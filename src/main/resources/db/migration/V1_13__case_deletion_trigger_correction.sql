create
or replace function reset_code()
    returns trigger as $$
begin
update payment
set code=null
where id = old.payment_id;
return old;
end;$$
language plpgsql;
