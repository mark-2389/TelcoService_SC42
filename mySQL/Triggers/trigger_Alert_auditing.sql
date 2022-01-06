delimiter //

-- to be checked for values where are zero and null
create trigger alert_auditing
after update on telcoservice_db.client
for each row
begin
    -- TODO: is it correct to have a value of 0 when inserting a tuple?
	if ( old.number_rejections = 2 AND new.number_rejections = 3 ) then
		insert into auditing values ( new.username, ( SELECT C.email
								 	                   FROM telcoservice_db.client C
									                   WHERE C.username = new.username ), 0, current_date, current_time, true );
    end if;

end; //

delimiter ;
