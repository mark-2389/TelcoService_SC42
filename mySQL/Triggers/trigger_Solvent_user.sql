delimiter //

create trigger solvent_user
after update on telcoservice_db.client
for each row
BEGIN
   -- if the user was insolvent but after an update the new number of
   -- rejections is 0 then the user should become solvent
   IF ( old.INSOLVENT = 'INSOLVENT' AND old.NUMBER_REJECTIONS > 0 AND new.NUMBER_REJECTIONS = 0 ) THEN
      UPDATE telcoservice_db.client
         SET INSOLVENT = 'SOLVENT'
         WHERE USERNAME = new.USERNAME;
   END IF;
END; //

delimiter ;
