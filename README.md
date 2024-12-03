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
    ```
2. Build the project:

Always show details
````bash
mvn clean install
````
3. Run the application:

Always show details
`````bash
mvn spring-boot:run
`````
4. Access the application:

    Swagger UI: http://localhost:8080/swagger-ui.html
    H2 Console: http://localhost:8080/h2-console

H2 Credentials:

Always show details

    JDBC URL: jdbc:h2:mem:loanapp
    Username: sa
    Password: 

## API Endpoints
-Public Endpoints

    None (all endpoints require authentication).

- ADMIN Endpoints
``````bash
HTTP Method	Endpoint	Description
GET	/api/admin/loans	Get all loans
POST	/api/admin/loans	Create a new loan
``````
- CUSTOMER Endpoints
```````bash
HTTP Method	Endpoint	Description
GET	/api/customer/loans	List loans for logged-in user
POST	/api/installments/pay	Pay loan installments
```````
Authentication and Roles

The application uses HTTP Basic Authentication for simplicity:
Username	Password	Role
admin	adminpass	ADMIN
customer1	custpass1	CUSTOMER
customer2	custpass2	CUSTOMER

    ADMIN users have access to all loans and customer data.
    CUSTOMER users can only view and manage their own loans.

Testing

    Run the tests:

Always show details

    mvn test

    Test data:
        Predefined test data (test-data.sql) is loaded for integration tests.

Known Issues and Improvements

    Add JWT-based authentication for better security.
    Integrate with a production database (e.g., PostgreSQL).
    Add support for pagination in loan listing APIs.

