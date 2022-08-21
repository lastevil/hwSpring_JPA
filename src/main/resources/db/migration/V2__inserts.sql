INSERT INTO products (title, price)
VALUES ('First', 2),
       ('Second', 4),
       ('Third', 6),
       ('Fourth', 8),
       ('Fifth', 10),
       ('Sixth', 12),
       ('Seventh', 3),
       ('Eighth', 5),
       ('Ninth', 90),
       ('Tenth', 110);
insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('user1', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user_1@gmail.com'),
       ('admin1', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin_1@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);

insert into order_statuses(order_status)
values ('Ожидание оплаты'),
       ('В обработке'),
       ('Готов к отправке'),
       ('Доставка'),
       ('Завершен');