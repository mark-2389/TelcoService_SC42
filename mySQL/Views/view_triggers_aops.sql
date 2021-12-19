-- Average number of optional products sold together with each service package

create table avarage_OpProducts_per_ServPackage (
		PACKAGEID int,
        AVERAGEPRODUCTS int
);

delimiter //

create trigger new_package_opproduct
after insert on service_package
for each row
begin
	insert into avarage_OpProducts_per_ServPackage value (new.ID, 0);
end; //

delimiter ;

delimiter //

create trigger new_purchase_order_opproduct
after update on telcoservice_db.order
for each row
begin
	
	if ( old.isvalid != ACCEPTED and new.isvalid = ACCEPTED ) then
    
		create temporary table prod_per_order ( SELECT O.ID as ID, count(OPTIONALPRODUCTID) as NUM_PROD
												FROM telcoservice_db.order O  LEFT JOIN orderoptionalcomposition OpComp on O.ID = OpComp.ORDERID
												WHERE AOPS.PACKAGEID = new.PACKAGEID
												GROUP BY O.ID );
                                            
		update avarage_OpProducts_per_ServPackage AOPS
			set AVERAGEPRODUCTS = ( SELECT avg( NUM_PROD )
									FROM prod_per_order PPO )
			where AOPS.PACKAGEID = new.PACKAGEID; 
            
	end if;
end; //

delimiter ;