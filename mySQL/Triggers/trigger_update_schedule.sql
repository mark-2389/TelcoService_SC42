DELIMITER //

CREATE TRIGGER update_service_schedule
AFTER UPDATE ON telcoservice_db.`order`
FOR EACH ROW
BEGIN
    DECLARE _period INT;

    -- if the order goes from not accepted to accepted
    IF ( old.IS_VALID <> 'ACCEPTED' AND new.IS_VALID = 'ACCEPTED' ) THEN
        -- add all the services in the order to the schedule

        -- get the validity period and save it in a variable
        SET _period = (
            SELECT V.PERIOD
            FROM telcoservice_db.validity V
            WHERE V.PACKAGE_ID = NEW.PACKAGE_ID AND V.ID = NEW.VALIDITY_ID
        );

        -- insert the service_ids decorated with the name of the user and the starting and ending date
        INSERT INTO telcoservice_db.service_schedule (
            SELECT NEW.CLIENT, C.SERVICE_ID, NEW.DATE_SUBSCRIPTION, DATE_ADD(NEW.DATE_SUBSCRIPTION, INTERVAL _period MONTH)
            FROM telcoservice_db.service_composition C
            WHERE C.PACKAGE_ID = NEW.PACKAGE_ID
        );
    END IF;
END; //

DELIMITER ;

DELIMITER //

CREATE TRIGGER update_optional_schedule
AFTER UPDATE ON telcoservice_db.`order`
FOR EACH ROW
BEGIN
    DECLARE _period INT;

    -- if the order goes from not accepted to accepted
    IF ( old.IS_VALID <> 'ACCEPTED' AND new.IS_VALID = 'ACCEPTED' ) THEN
        -- add all the optionals in the order to the schedule

        -- get the validity period and save it in a variable
        SET _period = (
            SELECT V.PERIOD
            FROM telcoservice_db.validity V
            WHERE V.PACKAGE_ID = NEW.PACKAGE_ID AND V.ID = NEW.VALIDITY_ID
        );

        -- insert the optional_ids decorated with the name of the user and the starting and ending date
        INSERT INTO telcoservice_db.optional_schedule (
            SELECT NEW.CLIENT, C.OPTIONAL_PRODUCT_ID, CURRENT_DATE, DATE_ADD(CURRENT_DATE, INTERVAL _period MONTH)
            FROM telcoservice_db.order_optional_composition C
            WHERE C.ORDER_ID = NEW.ID
        );
    END IF;
END; //

DELIMITER ;