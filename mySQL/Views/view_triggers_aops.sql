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
    
		create temporary table IF NOT EXISTS prod_per_order ( SELECT O.ID as ID, count(OPTIONAL_PRODUCT_ID) as NUM_PROD
												FROM telcoservice_db.order O  LEFT JOIN order_optional_composition OpComp on O.ID = OpComp.ORDER_ID
												WHERE O.PACKAGE_ID = new.PACKAGE_ID
												GROUP BY O.ID );
                                            
		update average_OpProducts_per_ServPackage AOPS
			set AVERAGE_PRODUCTS = ( SELECT avg( NUM_PROD )
									FROM prod_per_order PPO )
			where AOPS.PACKAGE_ID = new.PACKAGE_ID;
            
	end if;
end; //

delimiter ;