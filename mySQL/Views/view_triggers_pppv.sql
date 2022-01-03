-- Number of total purchases per package and validity period

create table purchase_per_package_validity (
	PACKAGE_ID int,
    VALIDITY_ID int,
    PURCHASES int
);

delimiter //

create trigger new_package_validity
after insert on validity
for each row
begin
	insert into purchase_per_package_validity value (new.PACKAGE_ID, new.ID, 0);
end; //

delimiter ;

delimiter // 

create trigger new_purchase_validity
after update on telcoservice_db.order
for each row
begin
    if ( old.is_valid <> 'ACCEPTED' and new.is_valid = 'ACCEPTED' ) then
        update purchase_per_package_validity PPPV
			set PPPV.purchases = PPPV.purchases + 1
            where PPPV.PACKAGE_ID = new.PACKAGE_ID and PPPV.VALIDITY_ID = new.VALIDITY_ID;
    end if;
end; // 

delimiter ;