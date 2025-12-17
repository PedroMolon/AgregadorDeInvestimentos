CREATE TABLE accounts (
    account_id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_accounts_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);