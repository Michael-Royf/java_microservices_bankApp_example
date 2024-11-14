CREATE SEQUENCE account_id_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE customer_id_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;


CREATE TABLE accounts (
                          account_number BIGINT PRIMARY KEY DEFAULT nextval('account_id_sequence'),
                          customer_id BIGINT,
                          account_type VARCHAR(255),
                          branch_address VARCHAR(255),
                          created_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
                          updated_at TIMESTAMP,
                          created_by VARCHAR(255) NOT NULL,
                          updated_by VARCHAR(255)
);

CREATE TABLE customers (
                           customer_id BIGINT PRIMARY KEY DEFAULT nextval('customer_id_sequence'),
                           name VARCHAR(255),
                           email VARCHAR(255) UNIQUE NOT NULL,
                           mobile_number VARCHAR(20) UNIQUE NOT NULL,
                           created_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
                           updated_at TIMESTAMP,
                           created_by VARCHAR(255) NOT NULL,
                           updated_by VARCHAR(255)
);




