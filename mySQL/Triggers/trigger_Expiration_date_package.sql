delimiter //

create trigger Expiration_Date_Consistency_Package
-- after, So to check also the latest added service
after insert on servicecomposition
for each row
BEGIN
	DECLARE min_date date default ( SELECT min(S.EXPIRATION_DATE)
									FROM service S JOIN servicecomposition SC on S.ID = SC.SERVICEID
									WHERE  new.PACKAGEID = SC.PACKAGEID );
	if ( min_date  is not null  AND ( SELECT SP.EXPIRATION_DATE
									  FROM service_package SP
									  WHERE  SP.ID = new.PACKAGEID ) > min_date ) THEN
		update servicepackage SP
			SET SP.EXPIRATION_DATE = min_date
            WHERE ( new.PACKAGEID = SP.ID);
	end if;
END; //

delimiter ;
