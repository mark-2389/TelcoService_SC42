create table purchase_per_package (
	PACKAGEID int,
    purchases int
);

delimiter //

-- every time a new service package is created it is inserted in the sale report with purchase equal to zero

create trigger new_package_available
after insert on service_package
for each row
begin
	insert into purchase_per_package value ( new.ID, 0 );
end; //

delimiter ;

delimiter //

-- at every new purchase (the order has been payed) we have to update the sales report

create trigger new_purchase
after update on telcoservice_db.order
for each row
begin
	if ( old.isvalid != ACCEPTED and new.isvalid = ACCEPTED) then
		update purchase_per_package PPP
			set PPP.purchase = PPP.purchase + 1
			where PPP.packageid = new.packageid;
	end if;
end; //

delimiter ;