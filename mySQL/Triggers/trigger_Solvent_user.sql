delimiter //

create trigger solvent_user
after update on telcoservice_db.client
for each row
BEGIN
   -- if the user was insolvent but after an update the new number of
   -- rejections is 0 then the user should become solvent
   IF ( old.insolvent = 'insolvent' AND old.number_rejection > 0 AND new.number_rejection = 0 ) THEN
      UPDATE telcoservice_db.client
         SET insolvent = 'solvent'
         WHERE id = new.id;
   END IF;
END; //

delimiter ;
