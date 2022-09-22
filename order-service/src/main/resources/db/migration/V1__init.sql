CREATE TABLE IF NOT EXISTS address
(
    id         bigserial,
    username   varchar(255) not null,
    country    varchar(255) not null,
    city       varchar(255) not null,
    street     varchar(255) not null,
    house      int          not null,
    flat       int,
    post_index int          not null,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS order_statuses
(
    id           bigserial,
    order_status varchar(255),
    primary key (id)
);

CREATE TABLE IF NOT EXISTS orders
(
    id          bigserial,
    username    varchar(255) not null,
    total_price integer,
    status_id   int,
    address_id int,
    phone varchar(255),
    created_at  timestamp,
    updated_at timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (status_id) references order_statuses (id),
    FOREIGN KEY (address_id) references address(id)

);

CREATE TABLE IF NOT EXISTS order_products
(
    id                bigserial,
    product_title     varchar not null,
    quantity          int    not null,
    order_id          bigint not null,
    price_per_product int    not null,
    price             int    not null,
    created_at        timestamp,
    updated_at        timestamp,
    primary key (id),
    foreign key (order_id) references orders (id)
);

