create table if not exists airplanes
(
    id          serial primary key,
    airplane_name varchar(400),
    flight_id     bigint not null references flights (id) unique,
    route_id    bigint not null references routes (id) unique,
    price       integer,
    start_date  date,
    end_date    date,
    deleted     bool default false,
    confirmed     bool default false
);
alter table airplanes
    owner to "user";


