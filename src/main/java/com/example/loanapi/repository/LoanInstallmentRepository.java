package com.example.loanapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.loanapi.model.LoanInstallment;

public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {

    public List<LoanInstallment> findByLoan_IdAndIsPaidFalse(Long loanId);

    @Query("SELECT i FROM LoanInstallment i WHERE i.loan.id = :loanId AND i.isPaid = false")
    List<LoanInstallment> findUnpaidInstallmentsByLoanId(@Param("loanId") Long loanId);

    List<LoanInstallment> findByLoan_Id(Long loanId);
}
