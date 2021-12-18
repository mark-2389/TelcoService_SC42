delimiter //

create trigger insolvent_user
after update on telcoservice_db.order
for each row
BEGIN
	DECLARE insolvent_client varchar(255) default ( SELECT distinct O.CLIENT
													FROM telcoservice_db.order O
													WHERE O.client = new.client );
	if ( old.isvalid = 'DEFAULT' and new.isvalid = 'REJECTED' ) then
		update telcoservice_db.client
			set insolvent = 'insolvent'
            where strcmp (username, insolvent_client) = 0;
	elseif ( old.isvalid = 'REJECTED'  and new.isvalid = 'ACCEPTED' ) then
		update telcoservice_db.client
			set number_rejection = number_rejection - ( SELECT O.number_rejection
														FROM telcoservice_db.order O
                                                        WHERE O.id = new.id );
	end if;
END; //

delimiter ;
