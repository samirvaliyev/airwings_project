create table if not exists users
(
    id         serial primary key,
    first_name varchar(255),
    last_name  varchar(255),
    login  varchar(255) unique,
    password   varchar(255),
    role varchar(255),
    passport_img   bytea
);
alter table users
    owner to "user";

