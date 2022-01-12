delimiter //

-- to be checked for values where are zero and null
create trigger alert_auditing
after update on telcoservice_db.client
for each row
begin
    declare total_value DECIMAL(6, 2);
	if ( old.number_rejections = 2 AND new.number_rejections = 3 ) then
	    set total_value = (
	        SELECT sum(O.TOTAL_COST)
	        FROM telcoservice_db.`order` O
	        WHERE O.CLIENT = new.USERNAME AND O.IS_VALID = 'REJECTED'
	    );

		insert into auditing values ( new.username, ( SELECT C.email
								 	                   FROM telcoservice_db.client C
									                   WHERE C.username = new.username ), total_value, current_date, current_time, true );
    elseif ( new.NUMBER_REJECTIONS <> old.NUMBER_REJECTIONS
                 and ( select * from telcoservice_db.auditing A where A.USERNAME = new.USERNAME and A.IS_ACTIVE ) is not null ) then
        set total_value = (
            SELECT sum(O.TOTAL_COST)
            FROM telcoservice_db.`order` O
            WHERE O.CLIENT = new.USERNAME AND O.IS_VALID = 'REJECTED'
        );

        update telcoservice_db.auditing A
            set A.AMOUNT = total_value
            where A.USERNAME = new.USERNAME;

	end if;

end; //

delimiter ;
