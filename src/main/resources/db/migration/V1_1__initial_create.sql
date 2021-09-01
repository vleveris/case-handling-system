create table payment
(
    id       serial primary key,
    amount   money   not null,
    currency integer not null
);
