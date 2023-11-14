create table user_table (
    id identity primary key,
    email varchar(25) unique,
    first_name varchar(25),
    last_name varchar(25),
    password varchar(100),
    role varchar(25)
);