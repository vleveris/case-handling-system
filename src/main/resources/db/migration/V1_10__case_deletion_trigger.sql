create function reset_code()
    returns trigger as $$
begin
update casep
set code=null
where id = old.payment_id;
return old;
end;$$
language plpgsql;
create trigger reset_payment_code
    after delete
    on casep
    for each row
    execute procedure reset_code();
