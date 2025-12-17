CREATE TABLE billingaddresses (
    account_id BIGINT PRIMARY KEY,
    street VARCHAR(255),
    number INT,
    CONSTRAINT fk_billingaddresses_account FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);