package com.example.loanapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {

    // Default constructor (required for JPA)
    public Customer() {
    }

    // Custom constructor
    public Customer(Long id, String username, String password, String role, Double creditLimit, Double usedCreditLimit) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.creditLimit = creditLimit;
        this.usedCreditLimit = usedCreditLimit;

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role; // Role: ADMIN or CUSTOMER

    private Double creditLimit;
    private Double usedCreditLimit;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getUsedCreditLimit() {
        return usedCreditLimit;
    }

    public void setUsedCreditLimit(Double usedCreditLimit) {
        this.usedCreditLimit = usedCreditLimit;
    }
}
