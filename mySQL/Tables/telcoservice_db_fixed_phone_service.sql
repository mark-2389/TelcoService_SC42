CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `fixed_phone_service`;

CREATE TABLE `fixed_phone_service` (

  `ID` int PRIMARY KEY,
  `MINUTES` int,
  `SMS` int,
  `MINUTES_FEE` decimal(6,2),
  `SMS_FEE` decimal(6,2),

  CONSTRAINT `fixed_phone_service_ibfk_1` FOREIGN KEY (`ID`)
   REFERENCES `service` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);
