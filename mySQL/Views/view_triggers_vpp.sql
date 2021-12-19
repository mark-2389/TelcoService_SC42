-- Total value of sales per package without the optional products

create table value_per_package_without(
	PACKAGEID int,
	TOTAL bigint
);

delimiter //

-- every time a new service package is created it is inserted in the sale report with value equal to zero

create trigger new_package_value
after insert on service_package
for each row
begin
	insert into value_per_package_without value ( new.ID, 0 );
end; //

delimiter ;

delimiter //

-- at every new purchase (the order has been payed) we have to update the sales report

create trigger new_purchase_value
after update on telcoservice_db.order
for each row
begin
	if ( old.isvalid != ACCEPTED and new.isvalid = ACCEPTED ) then
		update value_per_package_without VPP
			set VPP.TOTAL = VPP.TOTAL + ( SELECT V.MONTHLY_FEE * V.PERIOD 
											FROM telcoservice_db.order O join validity V on (O.validityid, O.packageid) = (V.id, V.packageid)
											WHERE O.id = new.id and o.packageid = new.packageid )
			where VPP.packageid = new.packageid;
	end if;
end; //

delimiter ;