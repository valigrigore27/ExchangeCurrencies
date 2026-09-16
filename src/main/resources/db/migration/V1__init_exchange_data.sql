CREATE TABLE exchange (
    id BIGSERIAL PRIMARY KEY,
    source_currency VARCHAR(5) NOT NULL,
    target_currency VARCHAR(5) NOT NULL,
    rate NUMERIC(12, 6) NOT NULL
);
