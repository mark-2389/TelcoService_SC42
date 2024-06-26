CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

-- This table keeps track of the alerts that have been created
-- An alert is created when a user causes at least three failed payments, eventually not on the same order
DROP TABLE IF EXISTS `auditing`;
CREATE TABLE `auditing` (

  `USERNAME` varchar(255) NOT NULL,
  `EMAIL` varchar(255) NOT NULL,
  `AMOUNT` decimal(6, 2) NOT NULL,
  `REJECTION_DATE` date DEFAULT ( current_date ),
  `REJECTION_TIME` time DEFAULT ( current_time ),
  `IS_ACTIVE` boolean DEFAULT true,  -- This attribute is kept for history

  PRIMARY KEY (`USERNAME`, `REJECTION_DATE`, `REJECTION_TIME`),

  -- Foreign keys for consistency on Client table
   -- CONSTRAINT `auditing_ibfk_1` FOREIGN KEY (`USERNAME`, `EMAIL`)
   -- REFERENCES `client` (`USERNAME`, `EMAIL`)
   --   ON DELETE NO ACTION
   --   ON UPDATE CASCADE
   
    -- Foreign keys for consistency on Client table
    CONSTRAINT `auditing_ibfk_1` FOREIGN KEY (`USERNAME`)
    REFERENCES `client` (`USERNAME`)
      ON DELETE NO ACTION
      ON UPDATE CASCADE,
      
	-- Foreign keys for consistency on Client table
 	CONSTRAINT `auditing_ibfk_2` FOREIGN KEY (`EMAIL`)
    REFERENCES `client` (`EMAIL`)
      ON DELETE NO ACTION
      ON UPDATE CASCADE
);
