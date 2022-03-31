create table if not exists users
(
    id         uuid primary key,
    login      varchar(100),
    password   varchar(100),
    is_admin   boolean
);


create table if not exists products
(
    id         uuid primary key,
    price      decimal(10, 2),
    quantity   int,
    name       varchar(100)
);