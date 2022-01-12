delimiter //

-- to be checked for values where are zero and null
create trigger alert_auditing
after update on telcoservice_db.client
for each row
begin
    declare last_rejection_value DECIMAL(6, 2);
	if ( old.number_rejections = 2 AND new.number_rejections = 3 ) then
	    set last_rejection_value = (
	        SELECT O.TOTAL_COST
	        FROM telcoservice_db.`order` O
	        WHERE O.CLIENT = new.USERNAME
	        ORDER BY O.DATE_CREATION -- TODO: add last_rejection_date to order
	        LIMIT 1
	    );

		insert into auditing values ( new.username, ( SELECT C.email
								 	                   FROM telcoservice_db.client C
									                   WHERE C.username = new.username ), last_rejection_value, current_date, current_time, true );
    end if;

end; //

delimiter ;
