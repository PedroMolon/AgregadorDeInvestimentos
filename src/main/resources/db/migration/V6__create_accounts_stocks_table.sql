CREATE TABLE accounts_stocks (
    account_id BIGINT NOT NULL,
    stock_id VARCHAR(32) NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (account_id, stock_id),
    CONSTRAINT fk_as_account FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    CONSTRAINT fk_as_stock FOREIGN KEY (stock_id) REFERENCES stocks(stock_id) ON DELETE CASCADE
);