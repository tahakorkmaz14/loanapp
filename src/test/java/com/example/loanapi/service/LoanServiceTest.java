package com.example.loanapi.service;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.loanapi.dto.LoanResponse;
import com.example.loanapi.model.Customer;
import com.example.loanapi.model.Loan;
import com.example.loanapi.model.LoanInstallment;
import com.example.loanapi.repository.CustomerRepository;
import com.example.loanapi.repository.LoanInstallmentRepository;
import com.example.loanapi.repository.LoanRepository;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanInstallmentRepository installmentRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    void shouldCreateLoanSuccessfully() {
        // Given
        Long customerId = 1L;
        Double loanAmount = 5000.0;
        Double interestRate = 0.3;
        Integer installments = 12;

        Customer customer = new Customer(1L, "customer1", "custpass1","CUSTOMER", 10000.0, 0.0);

        Mockito.when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        // When
        LoanResponse response = loanService.createLoan(customerId, loanAmount, interestRate, installments);

        // Then
        Assertions.assertNotNull(response);
        Mockito.verify(loanRepository, Mockito.times(1)).save(Mockito.any(Loan.class));
        Mockito.verify(installmentRepository, Mockito.times(12)).save(Mockito.any(LoanInstallment.class));
    }
}
