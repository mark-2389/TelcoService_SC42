delimiter //

-- to be checked for values where are zero and null
create trigger alert_auditing
after update on telcoservice_db.client
for each row
begin
    declare total_value DECIMAL(6, 2);
    declare in_auditing int default (
        select count(*) from telcoservice_db.auditing A
        where A.USERNAME = new.USERNAME and A.IS_ACTIVE = true
    );

	if ( old.number_rejections = 2 AND new.number_rejections = 3 ) then
	    set total_value = (
	        SELECT sum(O.TOTAL_COST)
	        FROM telcoservice_db.`order` O
	        WHERE O.CLIENT = new.USERNAME AND O.IS_VALID = 'REJECTED'
            group by O.client
	    );

		insert into auditing values ( new.username, new.EMAIL, total_value, current_date, current_time, true );
    elseif ( new.NUMBER_REJECTIONS <> old.NUMBER_REJECTIONS and in_auditing > 0 ) then
        set total_value = (
            SELECT sum(O.TOTAL_COST)
            FROM telcoservice_db.`order` O
            WHERE O.CLIENT = new.USERNAME AND O.IS_VALID = 'REJECTED'
            GROUP BY O.CLIENT
        );

        update telcoservice_db.auditing A
            set A.AMOUNT = total_value
            where A.USERNAME = new.USERNAME and A.IS_ACTIVE = true;

	end if;
end; //


delimiter ;
