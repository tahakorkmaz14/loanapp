package com.example.loanapi.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loanapi.dto.InstallmentResponse;
import com.example.loanapi.dto.LoanResponse;
import com.example.loanapi.dto.PaymentResult;
import com.example.loanapi.model.Customer;
import com.example.loanapi.model.Loan;
import com.example.loanapi.model.LoanInstallment;
import com.example.loanapi.repository.CustomerRepository;
import com.example.loanapi.repository.LoanInstallmentRepository;
import com.example.loanapi.repository.LoanRepository;

@Service
public class LoanService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanInstallmentRepository installmentRepository;

    // Existing createLoan method here...
    // New listLoans method
    public List<LoanResponse> listLoans(Long customerId) {

        // Validate the customer exists
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Fetch all loans for the customer
        List<Loan> loans = loanRepository.findByCustomer(customer);

        // Map Loan entities to LoanResponse DTOs
        return loans.stream().map(this::mapToLoanResponse).collect(Collectors.toList());
    }

    // Create a loan
    public LoanResponse createLoan(Long customerId, Double amount, Double interestRate, Integer installments) {

        // Validate Interest Rate
        if (interestRate < 0.1 || interestRate > 0.5) {
            throw new IllegalArgumentException("Interest rate must be between 0.1 and 0.5");
        }

        // Validate Installments
        List<Integer> allowedInstallments = List.of(6, 9, 12, 24);
        if (!allowedInstallments.contains(installments)) {
            throw new IllegalArgumentException("Installments must be one of 6, 9, 12, or 24");
        }

        // Validate Customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Validate Credit Limit
        Double totalAmount = amount * (1 + interestRate);
        if (customer.getCreditLimit() - customer.getUsedCreditLimit() < totalAmount) {
            throw new RuntimeException("Insufficient credit limit");
        }

        // Create Loan
        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setLoanAmount(amount);
        loan.setNumberOfInstallments(installments);
        loan.setCreateDate(LocalDate.now());
        loan.setIsPaid(false);
        loanRepository.save(loan);

        // Installment Creation Logic
        for (int i = 1; i <= installments; i++) {
            LocalDate dueDate = LocalDate.now().plusMonths(i).withDayOfMonth(1);
            LoanInstallment installment = new LoanInstallment();
            installment.setLoan(loan);
            installment.setAmount(totalAmount / installments); // Divide total loan amount into equal installments
            installment.setPaidAmount(0.0);                   // Initially no amount is paid
            installment.setDueDate(dueDate);                  // Set due date for each installment
            installment.setIsPaid(false);                     // Initially, the installment is not paid
            installmentRepository.save(installment);
        }

        // Update Customer Credit Limit
        customer.setUsedCreditLimit(customer.getUsedCreditLimit() + totalAmount);
        customerRepository.save(customer);

        // Return LoanResponse
        return mapToLoanResponse(loan);
    }

    // Helper method to map Loan to LoanResponse
    private LoanResponse mapToLoanResponse(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getLoanAmount(),
                loan.getNumberOfInstallments(),
                loan.getIsPaid()
        );
    }

    public List<InstallmentResponse> listInstallments(Long loanId) {
        List<LoanInstallment> installments = installmentRepository.findByLoan_Id(loanId);

        if (installments.isEmpty()) {
            throw new RuntimeException("No installments found for loan ID: " + loanId);
        }

        return installments.stream().map(this::mapToInstallmentResponse).collect(Collectors.toList());
    }

// Helper method to map LoanInstallment to InstallmentResponse
    private InstallmentResponse mapToInstallmentResponse(LoanInstallment installment) {
        return new InstallmentResponse(
                installment.getId(),
                installment.getAmount(),
                installment.getPaidAmount(),
                installment.getDueDate(),
                installment.getIsPaid()
        );
    }

    public PaymentResult payLoan(Long loanId, Double amount) {
        // Fetch unpaid installments for the loan
        List<LoanInstallment> installments = installmentRepository.findByLoan_IdAndIsPaidFalse(loanId);

        if (installments.isEmpty()) {
            throw new IllegalArgumentException("No unpaid installments found for loan ID: " + loanId);
        }

        // Filter installments within 3 months from today
        LocalDate maxDueDate = LocalDate.now().plusMonths(3);
        installments = installments.stream()
                .filter(installment -> !installment.getDueDate().isAfter(maxDueDate))
                .sorted(Comparator.comparing(LoanInstallment::getDueDate)) // Sort by earliest due date
                .toList();

        if (installments.isEmpty()) {
            throw new IllegalArgumentException("No installments are payable within the next 3 months");
        }

        double remainingAmount = amount;
        int installmentsPaid = 0;
        double totalSpent = 0;

        LocalDate now = LocalDate.now();

        for (LoanInstallment installment : installments) {
            long daysBeforeDue = ChronoUnit.DAYS.between(now, installment.getDueDate());
            long daysAfterDue = ChronoUnit.DAYS.between(installment.getDueDate(), now);

            // Adjust the installment amount for discounts or penalties
            double adjustedAmount = installment.getAmount();

            if (daysBeforeDue > 0) {
                // Apply discount for early payment
                double discount = installment.getAmount() * 0.001 * daysBeforeDue;
                adjustedAmount -= discount;
            } else if (daysAfterDue > 0) {
                // Apply penalty for late payment
                double penalty = installment.getAmount() * 0.001 * daysAfterDue;
                adjustedAmount += penalty;
            }

            if (remainingAmount >= adjustedAmount) {
                // Pay the full adjusted installment
                installment.setPaidAmount(adjustedAmount);
                installment.setIsPaid(true);
                installment.setPaymentDate(now);
                installmentRepository.save(installment);

                remainingAmount -= adjustedAmount;
                totalSpent += adjustedAmount;
                installmentsPaid++;
            } else {
                break; // Stop if remaining amount is insufficient
            }
        }

        // Update loan status if fully paid
        boolean isLoanPaid = installments.stream().allMatch(LoanInstallment::getIsPaid);
        if (isLoanPaid) {
            Loan loan = loanRepository.findById(loanId)
                    .orElseThrow(() -> new RuntimeException("Loan not found"));
            loan.setIsPaid(true);
            loanRepository.save(loan);
        }

        isLoanPaid = installmentRepository.findByLoan_IdAndIsPaidFalse(loanId).isEmpty();

        // Update customer credit limit
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        Customer customer = loan.getCustomer();
        customer.setUsedCreditLimit(customer.getUsedCreditLimit() - totalSpent);
        customerRepository.save(customer);

        // Return result
        return new PaymentResult(installmentsPaid, totalSpent, isLoanPaid);
    }

    public List<LoanResponse> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream()
                .map(this::mapToLoanResponse)
                .toList();
    }

    public List<LoanResponse> getLoansForCustomer(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        List<Loan> loans = loanRepository.findByCustomer(customer);
        return loans.stream()
                .map(this::mapToLoanResponse)
                .toList();
    }

}
