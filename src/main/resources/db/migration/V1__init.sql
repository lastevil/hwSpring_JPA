CREATE TABLE IF NOT EXISTS products (
                                    id bigserial,
                                    title VARCHAR(255),
                                    coast integer, PRIMARY KEY (id)
                                    );

CREATE TABLE IF NOT EXISTS users (
                                  id         bigserial primary key,
                                  username   varchar(36) not null,
                                  password   varchar(80) not null,
                                  email      varchar(50) unique,
                                  created_at timestamp default current_timestamp,
                                  updated_at timestamp default current_timestamp
                                 );

CREATE TABLE IF NOT EXISTS roles (
                                  id         bigserial primary key,
                                  name       varchar(50) not null,
                                  created_at timestamp default current_timestamp,
                                  updated_at timestamp default current_timestamp
                                 );

CREATE TABLE IF NOT EXISTS users_roles (
                                        user_id bigint not null references users (id),
                                        role_id bigint not null references roles (id),
                                        primary key (user_id, role_id)
                                       );

CREATE TABLE IF NOT EXISTS orders (
                                    id bigserial, user_id integer,
                                    product_id integer,
                                    fix_coast integer,
                                    status VARCHAR(255),
                                    order_time TIME, PRIMARY KEY (id),
                                    FOREIGN KEY (user_id) REFERENCES users(id),
                                    FOREIGN KEY (product_id) REFERENCES products(id)
                                  );