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
after insert on telcoservice_db.order
for each row
begin
	update avarage_OpProducts_per_ServPackage AOPS
		set AVERAGEPRODUCTS = SELECT count(OPTIONALPRODUCTID)/count(PACKAGEID) as AVG_OPPROD
							  FROM telcoservice_db.order O  left JOIN orderoptionalcomposition OpComp on O.ID = OpComp.ORDERID
							  WHERE AOPS.PACKAGEID = new.PACKAGEID
							  GROUP BY O.PACKAGEID;
end;

delimiter ;