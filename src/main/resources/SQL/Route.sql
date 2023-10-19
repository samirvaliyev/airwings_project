create table if not exists routes
(
    id           serial primary key,
    departure     varchar(255) not null,
    destination  varchar(255) not null,
    transit_time integer
);
alter table routes
    owner to "user";
