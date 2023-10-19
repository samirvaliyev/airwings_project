create table if not exists orders
(
    id              serial primary key,
    airplane_id       bigint not null references airplanes (id) on delete cascade,
    user_id         bigint not null references users (id) on delete cascade,
    status          varchar(255)
);
alter table orders
    owner to "user";