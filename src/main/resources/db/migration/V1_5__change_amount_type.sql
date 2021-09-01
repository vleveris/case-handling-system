alter table payment alter column currency type integer;
alter table payment drop column amount;
alter table payment
    add column amount float not null;