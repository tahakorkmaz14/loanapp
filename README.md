# Loan Application

A Spring Boot REST API for managing loans and installments with role-based access control.  
This application supports `ADMIN` and `CUSTOMER` roles, allowing role-specific operations such as listing and paying loans.

---

## Features

- **Role-Based Access Control**:  
  - `ADMIN` can access and manage all loans and customers.
  - `CUSTOMER` can only access and manage their own data.
- **Loan Management**:
  - Create loans for customers with dynamic interest rates and installment plans.
- **Installment Management**:
  - Pay installments with support for penalties (late payments) and rewards (early payments).
- **Validation**:
  - Input validation for interest rates, number of installments, and payment amounts.
- **H2 Database**:
  - In-memory database for development and testing.

---

## Prerequisites

- **Java 23**
- **Maven**
- **Git**
- Any REST API Client (e.g., Postman, Curl)

---

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/tahakorkmaz14/loanapp.git
   cd loanapp
