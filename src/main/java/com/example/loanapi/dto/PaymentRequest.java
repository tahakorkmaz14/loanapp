package com.example.loanapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequest {

    @NotNull(message = "Loan ID is required")
    private Long loanId;

    @NotNull(message = "Payment amount is required")
    @Positive(message = "Payment amount must be positive")
    private Double paymentAmount;

    public Long getLoanId() {
        return this.loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Double getPaymentAmount() {
        return this.paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

}
