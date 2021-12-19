CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `auditing`;
CREATE TABLE `auditing` (
  `USERNAME` varchar(255) NOT NULL,
  `MAIL` varchar(127) NOT NULL,
  `AMOUNT` decimal(6, 2) NOT NULL,
  `REJECTION_DATE` date,
  `REJECTION_TIME` time,
  `IS_ACTIVE` boolean,
  PRIMARY KEY (`USERNAME`, `REJECTION_DATE`, `REJECTION_TIME`),
  CONSTRAINT `auditing_ibfk_1` FOREIGN KEY (`USERNAME`)
   REFERENCES `client` (`USERNAME`)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
);
