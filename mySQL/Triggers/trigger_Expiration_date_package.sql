delimiter //

create trigger Expiration_Date_Consistency_Package
-- after, So to check also the latest added service
after insert on service_composition
for each row
BEGIN
	DECLARE min_date date default ( SELECT min(S.EXPIRATION_DATE)
									FROM service S JOIN service_composition SC on S.ID = SC.SERVICE_ID
									WHERE  new.PACKAGE_ID = SC.PACKAGE_ID );
	if ( min_date  is not null  AND ( SELECT SP.EXPIRATION_DATE
									  FROM service_package SP
									  WHERE  SP.ID = new.PACKAGE_ID ) > min_date ) THEN
		update service_package SP
			SET SP.EXPIRATION_DATE = min_date
            WHERE ( new.PACKAGE_ID = SP.ID);
	end if;
END; //

delimiter ;
