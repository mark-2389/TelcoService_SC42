-- Number of total purchases per package and validity period

create table purchase_per_package_validity (
	PACKAGEID int,
    VALIDITYID int,
    PURCHASES int
);

delimiter //

create trigger new_package_validity
after insert on validity
for each row
begin
	insert into purchase_per_package_validity value (new.ID, new.PACKAGEID, 0);
end; //

delimiter ;

delimiter // 

create trigger new_purchase_validity
after update on telcoservice_db.order
for each row
begin
	if ( old.isvalid != ACCEPTED and new.isvalid = ACCEPTED ) then
		update purchase_per_package_valdity PPPV
			set PPPV.purchases = PPPV.purchases + 1
            where PPPV.PACKAGEID = new.PACKAGEID and PPPV.VALIDITYID = new.validityid;
    end if;
end; // 

delimiter ;