delimiter //

-- to be checked for values where are zero and null
create trigger alert_auditing
after update on telcoservice_db.client
for each row
begin

	if ( old.number_rejection = 2 AND new.number_rejection = 3 ) then
		insert into auditing values ( ( SELECT C.username, C.email
								 	    FROM telcoservice_db.client C
									    WHERE strcmp( C.username, new.username ) = 0 ), 0, null, null, true );
    end if;

end; //

delimiter ;
