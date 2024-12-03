package com.example.loanapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loanapi.model.Customer;
import com.example.loanapi.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    // Custom query method to find loans by customer
    List<Loan> findByCustomer(Customer customer);
}
