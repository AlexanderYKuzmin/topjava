
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2021-01-30 10:00', 'Завтрак', 500, 100001),
       ('2021-01-30 13:00', 'Обед', 1000, 100001),
       ('2021-01-30 18:00', 'Ужин', 400, 100001),
       ('2021-01-30 8:00', 'Завтрак', 800, 100000),
       ('2021-01-30 12:00', 'Обед', 1000, 100000),
       ('2021-01-30 20:00', 'Ужин', 1100, 100000),
       ('2021-01-31 9:00', 'Завтрак', 300, 100001),
       ('2021-01-31 14:00', 'Обед', 1500, 100001),
       ('2021-01-31 19:00', 'Ужин', 400, 100001),
       ('2021-01-31 8:00', 'Завтрак', 800, 100000),
       ('2021-01-31 14:00', 'Обед', 900, 100000),
       ('2021-01-31 21:00', 'Ужин', 700, 100000);