-- Migration V3: Create Account Table

CREATE TABLE accounts (
    account_id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    user_id BIGINT,
    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);