package com.example.loanapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loanapi.dto.InstallmentResponse;
import com.example.loanapi.dto.LoanRequest;
import com.example.loanapi.dto.LoanResponse;
import com.example.loanapi.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    // Constructor injection for LoanService
    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // Create a loan
    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@RequestBody @Valid LoanRequest loanRequest) {
        LoanResponse loanResponse = loanService.createLoan(
                loanRequest.getCustomerId(),
                loanRequest.getLoanAmount(),
                loanRequest.getInterestRate(),
                loanRequest.getInstallments()
        );
        return ResponseEntity.ok(loanResponse);
    }

    // List loans for a specific customer
    @GetMapping("/{customerId}/loans")
    public ResponseEntity<List<LoanResponse>> listLoans(@PathVariable Long customerId) {
        List<LoanResponse> loans = loanService.listLoans(customerId);
        return ResponseEntity.ok(loans);
    }

    // List installments for a specific loan
    @GetMapping("/{loanId}/installments")
    public ResponseEntity<List<InstallmentResponse>> listInstallments(@PathVariable Long loanId) {
        List<InstallmentResponse> installments = loanService.listInstallments(loanId);
        return ResponseEntity.ok(installments);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<LoanResponse>> getAllLoans() {
        List<LoanResponse> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<LoanResponse>> getCustomerLoans(Authentication authentication) {
        String username = authentication.getName(); // Fetch the logged-in customer's username
        return ResponseEntity.ok(loanService.getLoansForCustomer(username));
    }

}
