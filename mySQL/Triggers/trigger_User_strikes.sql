delimiter // 

create trigger user_strikes
after update on telcoservice_db.order
for each row
BEGIN

    -- we retrieve the costumer that made the order (distinct is to make the O.CLIENT as a single value)
	DECLARE costumer varchar(255) default ( SELECT distinct O.CLIENT
											FROM telcoservice_db.order O
											WHERE O.client = new.client );

    -- if the number of rejection changed (in the order it can only increment) we update the rejections on the client
	if ( old.number_rejections <> new.number_rejections ) then
		update telcoservice_db.client as C
			set C.NUMBER_REJECTIONS = C.NUMBER_REJECTIONS+1
            where C.username = costumer;
    end if;
					
END; //

delimiter ;