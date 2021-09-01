alter table payment
    add column code varchar(255) unique;
create table casep
(
    id         serial primary key,
    payment_id serial  not null,
    country    integer not null,
    foreign key (payment_id) references payment on update restrict on delete restrict
);

