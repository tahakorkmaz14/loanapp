package com.example.loanapi.dto;

import java.time.LocalDate;

public class InstallmentResponse {
    private Long installmentId;
    private Double amount;
    private Double paidAmount;
    private LocalDate dueDate;
    private Boolean isPaid;

    public InstallmentResponse(Long installmentId, Double amount, Double paidAmount, LocalDate dueDate, Boolean isPaid) {
        this.installmentId = installmentId;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.dueDate = dueDate;
        this.isPaid = isPaid;
    }

    public Long getInstallmentId() {
        return this.installmentId;
    }

    public void setInstallmentId(Long installmentId) {
        this.installmentId = installmentId;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPaidAmount() {
        return this.paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getIsPaid() {
        return this.isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

}
