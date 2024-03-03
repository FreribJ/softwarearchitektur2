create table metadata
(
    name varchar(255) not null,
    value varchar(255) not null,
    type varchar(255) not null
);

create table tour
(
    name varchar(255) not null,
    description varchar(255) not null,
    price decimal(10, 2) not null
);

create table Test
(
    name varchar(255) not null,
    zahl int not null
)