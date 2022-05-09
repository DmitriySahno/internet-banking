insert into users(name)
values ('John Malkovich'),
       ('Paul McKinley'),
       ('Christopher Robertson'),
       ('Edvard Schwarts');

insert into balance(user_id, amount)
values (1, 120),
       (2, 2000),
       (4, 0);

insert into operations(user_id, type, amount, date)
values (1, 'D', 100, '2022-01-01 10:00:00'),
       (1, 'D', 20, '2022-01-01 12:00:00'),
       (2, 'D', 2000, '2022-01-01 12:01:00');