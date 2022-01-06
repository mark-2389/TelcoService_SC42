-- Total value of sales per package with optional products

create table IF NOT EXISTS value_per_package_op (
	PACKAGE_ID int,
	TOTAL bigint
);

delimiter //

-- every time a new service package is created it is inserted in the sale report with value equal to zero

create trigger new_package_value_op
after insert on service_package
for each row
begin
	insert into value_per_package_op value ( new.ID, 0 );
end; //

delimiter ;

delimiter //

-- at every new purchase (the order has been payed) we have to update the sales report

create trigger new_purchase_value_op
after update on telcoservice_db.order
for each row
begin
    if ( old.is_valid <> 'ACCEPTED' and new.is_valid = 'ACCEPTED' ) then
		update value_per_package_op VPPO
			set VPPO.TOTAL = VPPO.TOTAL + ( SELECT O.TOTAL_COST
											FROM telcoservice_db.order O
											WHERE O.id = new.id )
			where VPPO.PACKAGE_ID = new.PACKAGE_ID;
	end if;
end; //

delimiter ;