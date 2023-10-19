create table if not exists flights
(
    id              serial primary key,
    name varchar(255) unique,
    passenger_capacity integer
);
alter table flights
    owner to "user";
