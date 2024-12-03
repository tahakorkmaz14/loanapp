package com.example.loanapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loanapi.dto.InstallmentResponse;
import com.example.loanapi.dto.PaymentRequest;
import com.example.loanapi.dto.PaymentResult;
import com.example.loanapi.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/installments")
public class InstallmentController {

    private final LoanService loanService;

    // Constructor injection
    public InstallmentController(LoanService loanService) {
        this.loanService = loanService;
    }

    // List installments for a specific loan
    @GetMapping("/{loanId}")
    public ResponseEntity<List<InstallmentResponse>> listInstallments(@PathVariable Long loanId) {
        List<InstallmentResponse> installments = loanService.listInstallments(loanId);
        return ResponseEntity.ok(installments);
    }

    // Pay loan installments
    @PostMapping("/{loanId}/pay")
    public ResponseEntity<PaymentResult> payLoan(
            @PathVariable Long loanId,
            @RequestBody @Valid PaymentRequest paymentRequest) {
        PaymentResult result = loanService.payLoan(loanId, paymentRequest.getPaymentAmount());
        return ResponseEntity.ok(result);
    }
}
