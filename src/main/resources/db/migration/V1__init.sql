CREATE TABLE IF NOT EXISTS products
(
    id    bigserial,
    title VARCHAR(255),
    price integer,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE IF NOT EXISTS roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS address
(
    id         bigserial,
    user_id    int,
    country    varchar(255) not null,
    city       varchar(255) not null,
    street     varchar(255) not null,
    house      int          not null,
    flat       int,
    post_index int          not null,
    primary key (id),
    foreign key (user_id) references users (id)
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
    user_id     integer,
    total_price integer,
    status_id   int,
    address_id int,
    phone varchar(255),
    created_at  timestamp,
    updated_at timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (status_id) references order_statuses (id),
    FOREIGN KEY (address_id) references address(id)

);

CREATE TABLE IF NOT EXISTS order_products
(
    id                bigserial,
    product_id        bigint not null,
    quantity          int    not null,
    order_id          bigint not null,
    price_per_product int    not null,
    price             int    not null,
    created_at        timestamp,
    updated_at        timestamp,
    primary key (id),
    foreign key (order_id) references orders (id),
    foreign key (product_id) references products (id)
);

