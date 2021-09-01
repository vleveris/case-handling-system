alter table casep
    add column note varchar(255);
alter table casep
    add column created timestamp not null;
alter table casep
    add column resolved timestamp not null;

