CREATE TABLE CUSTOMER (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    credit_limit DOUBLE,
    used_credit_limit DOUBLE
);
