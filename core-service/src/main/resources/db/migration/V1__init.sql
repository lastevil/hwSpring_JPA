CREATE TABLE IF NOT EXISTS products
(
    id    bigserial,
    title VARCHAR(255),
    price integer,
    PRIMARY KEY (id)
);
