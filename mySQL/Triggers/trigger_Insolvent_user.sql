delimiter //

create trigger insolvent_user
after update on telcoservice_db.order
for each row
BEGIN
	DECLARE insolvent_client varchar(255) default ( SELECT distinct O.CLIENT
													FROM telcoservice_db.order O
													WHERE O.client = new.client );

	DECLARE new_client_rejections int;
	DECLARE old_client_rejections int;

	if ( old.IS_VALID = 'DEFAULT' and new.IS_VALID = 'REJECTED' ) then
        -- If the first payment is rejected
		update telcoservice_db.client
			set insolvent = 'insolvent'
            where strcmp (username, insolvent_client) = 0;

	elseif ( old.IS_VALID = 'REJECTED' and new.IS_VALID = 'ACCEPTED' ) then
	    -- When the order is finally payed
	    SET old_client_rejections = (
            SELECT C.NUMBER_REJECTIONS
            FROM client C
            WHERE NEW.CLIENT = C.USERNAME
        );

	    SET new_client_rejections = (
            SELECT C.NUMBER_REJECTIONS - O.NUMBER_REJECTIONS
            FROM telcoservice_db.order O, client C
            WHERE O.id = new.id AND NEW.CLIENT = C.USERNAME
	    );

		update telcoservice_db.client C
			set C.NUMBER_REJECTIONS = new_client_rejections
		    where NEW.CLIENT = C.USERNAME;

	    if ( old_client_rejections > 0 and new_client_rejections = 0 ) then
            update telcoservice_db.client C
            set C.INSOLVENT = 'SOLVENT'
            where NEW.CLIENT = C.USERNAME AND C.INSOLVENT = 'INSOLVENT';
        end if;
	end if;
END; //

delimiter ;
