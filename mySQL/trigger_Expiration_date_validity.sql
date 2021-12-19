delimiter //

create trigger Expiration_Date_Consistency_Validity
before insert on validity
for each row
BEGIN
	DECLARE date_to_set date default ( SELECT SP.EXPIRATION_DATE
										FROM service_package SP 
										WHERE new.PACKAGEID = SP.ID); 
	IF ( data_to_set is not null AND new.EXPIRATION_DATE > date_to_set ) THEN
		SET new.EXPIRATION_DATE = data_to_set;
	END IF;
END; //

delimiter ;