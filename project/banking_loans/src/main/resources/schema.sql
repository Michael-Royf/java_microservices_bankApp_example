CREATE SEQUENCE loan_id_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE loans (
                       loan_id BIGINT PRIMARY KEY DEFAULT nextval('loan_id_sequence'),
                       mobile_number VARCHAR(20),
                       loan_number VARCHAR(20),
                       loan_type VARCHAR(50),
                       total_loan INT,
                       amount_paid INT,
                       outstanding_amount INT,
                       created_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
                       updated_at TIMESTAMP,
                       created_by VARCHAR(255) NOT NULL,
                       updated_by VARCHAR(255)
);

