create table if not exists staff
(
    id         serial primary key,
    first_name varchar(255),
    last_name  varchar(255),
    flight_id    bigint references flights (id) on delete cascade
);
alter table staff
    owner to "user";