-- auto-generated definition
create table departments
(
    id   integer not null
        constraint departments_pk
            primary key,
    name text    not null
);

alter table departments
    owner to postgres;

-- auto-generated definition
create sequence departments_sequence
    as integer;

alter sequence departments_sequence owner to postgres;

-- auto-generated definition
create table workers
(
    id            integer not null
        constraint workers_pk
            primary key,
    department_id integer not null,
    name          text    not null,
    salary        real
);

alter table workers
    owner to postgres;

-- auto-generated definition
create sequence workers_sequence
    as integer;

alter sequence workers_sequence owner to postgres;

-- auto-generated definition
create table payments
(
    id        integer not null
        constraint payments_pk
            primary key,
    worker_id integer not null,
    date      date    not null,
    sum       real    not null
);

alter table payments
    owner to postgres;

-- auto-generated definition
create sequence payments_sequence
    as integer;

alter sequence payments_sequence owner to postgres;

