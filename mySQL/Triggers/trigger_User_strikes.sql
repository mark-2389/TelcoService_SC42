delimiter // 

create trigger user_strikes
after update on telcoservice_db.order
for each row
BEGIN

	DECLARE costumer varchar(255) default ( SELECT distinct O.CLIENT
											FROM telcoservice_db.order O
											WHERE O.client = new.client );
                                            
	if ( old.number_rejection <> new.number_rejection ) then
		update telcoservice_db.client as C
			set C.NUMBER_REJECTION = C.NUMBER_REJECTION+1
            where strcmp( C.username, costumer ) = 0;
    end if;
					
END; //

delimiter ;