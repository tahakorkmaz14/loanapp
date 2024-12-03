package com.example.loanapi.dto;

public class PaymentResult {

    private int installmentsPaid;
    private double totalSpent;
    private boolean isLoanPaid;

    public PaymentResult(int installmentsPaid, double totalSpent, boolean isLoanPaid) {
        this.installmentsPaid = installmentsPaid;
        this.totalSpent = totalSpent;
        this.isLoanPaid = isLoanPaid;
    }

    public int getInstallmentsPaid() {
        return this.installmentsPaid;
    }

    public void setInstallmentsPaid(int installmentsPaid) {
        this.installmentsPaid = installmentsPaid;
    }

    public double getTotalSpent() {
        return this.totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public boolean isIsLoanPaid() {
        return this.isLoanPaid;
    }

    public void setIsLoanPaid(boolean isLoanPaid) {
        this.isLoanPaid = isLoanPaid;
    }

}
