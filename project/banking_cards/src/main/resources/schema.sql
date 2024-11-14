CREATE SEQUENCE card_id_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE cards (
                       card_id BIGINT PRIMARY KEY DEFAULT nextval('card_id_sequence'),
                       mobile_number VARCHAR(20),
                       card_number VARCHAR(20),
                       card_type VARCHAR(50),
                       total_limit INT,
                       amount_used INT,
                       available_amount INT,
                       created_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
                       updated_at TIMESTAMP,
                       created_by VARCHAR(255) NOT NULL,
                       updated_by VARCHAR(255)
);


