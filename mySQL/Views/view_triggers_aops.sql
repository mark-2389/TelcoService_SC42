-- Average number of optional products sold together with each service package

create table IF NOT EXISTS average_OpProducts_per_ServPackage (
		PACKAGE_ID int,
        AVERAGE_PRODUCTS float
);

delimiter //

create trigger new_package_opProduct
after insert on service_package
for each row
begin
	insert into average_OpProducts_per_ServPackage value (new.ID, 0);
end; //

delimiter ;

delimiter //

create trigger new_purchase_order_opProduct
after update on telcoservice_db.order
for each row
begin

	if ( old.is_valid <> 'ACCEPTED' and new.is_valid = 'ACCEPTED' ) then

		update average_OpProducts_per_ServPackage AOPS
			set AVERAGE_PRODUCTS = ( SELECT count(OPTIONAL_PRODUCT_ID) / count( distinct O.ID )
                                     FROM telcoservice_db.order O  LEFT JOIN order_optional_composition OpComp on O.ID = OpComp.ORDER_ID
                                     WHERE O.PACKAGE_ID = new.PACKAGE_ID )
			where AOPS.PACKAGE_ID = new.PACKAGE_ID;
            
	end if;
end; //

delimiter ;