package com.example.loanapi.dto;

public class LoanResponse {
    private Long loanId;
    private Double loanAmount;
    private Integer numberOfInstallments;
    private Boolean isPaid;

    // Constructor
    public LoanResponse(Long loanId, Double loanAmount, Integer numberOfInstallments, Boolean isPaid) {
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.numberOfInstallments = numberOfInstallments;
        this.isPaid = isPaid;
    }

    // Getters and setters
    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
