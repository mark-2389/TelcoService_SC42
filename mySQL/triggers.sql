drop trigger Expiration_Date_Consistency_Validity;
drop trigger Expiration_Date_Consistency_Package;

delimiter |

create trigger Expiration_Date_Consistency_Validity
before insert on validity
for each row
BEGIN
	IF ( (SELECT SP.EXPIRATION_DATE
		  FROM service_package SP 
		  WHERE new.PACKAGEID = SP.ID) is not null AND new.EXPIRATION_DATE > ( SELECT SP.EXPIRATION_DATE
																			   FROM service_package SP
																			   WHERE  new.PACKAGEID = SP.ID ) ) THEN
		SET new.EXPIRATION_DATE = SP.EXPIRATION_DATE
		WHERE new.PACKAGEID IN ( select SP1.ID
								from service_package  SP1
								where SP1.ID = new.PACKAGEID );
	END IF;
END;

delimiter ;

delimiter |

create trigger Expiration_Date_Consistency_Package
#after?? So to check also the latest added service
before insert on servicecomposition
for each row
BEGIN
	if ( ( ( SELECT min(S.EXPIRATION_DATE) AS min_date
		     FROM service S JOIN servicecomposition SC on S.ID = SC.SERVICEID
		     WHERE   new.PACKAGEID = SC.PACKAGEID ) is not null  ) AND ( SELECT SP.EXPIRATION_DATE
																		 FROM ServicePackage SP
																		 WHERE  SP.ID = new.PACKAGEID ) > min_date )
		update servicepackage SP1 
			SET SP.EXPIRATION_DATE = min_date
			WHERE new.PACAKGEID = SP.ID
	end if;
END;

delimiter ;
