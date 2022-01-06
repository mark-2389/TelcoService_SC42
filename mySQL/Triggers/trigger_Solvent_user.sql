delimiter //

create trigger solvent_user
after update on telcoservice_db.client
for each row
BEGIN
   -- if the user was insolvent but after an update the new number of
   -- rejections is 0 then the user should become solvent
   IF ( old.INSOLVENT = 'INSOLVENT' AND new.INSOLVENT = 'SOLVENT' ) THEN
      UPDATE telcoservice_db.auditing
         SET IS_ACTIVE = 0
         WHERE auditing.USERNAME = NEW.USERNAME;
   END IF;
END; //

delimiter ;
