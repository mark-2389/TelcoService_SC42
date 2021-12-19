delimiter //

create trigger insolvent_user
after update on telcoservice_db.order
for each row
BEGIN
	DECLARE insolvent_client varchar(255) default ( SELECT distinct O.CLIENT
													FROM telcoservice_db.order O
													WHERE O.client = new.client );

	if ( old.IS_VALID = 'DEFAULT' and new.IS_VALID = 'REJECTED' ) then
        -- If the first payment is rejected
		update telcoservice_db.client
			set insolvent = 'insolvent'
            where strcmp (username, insolvent_client) = 0;

	elseif ( old.IS_VALID = 'REJECTED'  and new.IS_VALID = 'ACCEPTED' ) then
	    -- When the order is finally payed
		update telcoservice_db.client
			set NUMBER_REJECTIONS = NUMBER_REJECTIONS - ( SELECT O.NUMBER_REJECTIONS
														FROM telcoservice_db.order O
                                                        WHERE O.id = new.id );
	end if;
END; //

delimiter ;
