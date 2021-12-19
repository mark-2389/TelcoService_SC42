CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `internet_service`;

CREATE TABLE `internet_service` (

  `ID` int PRIMARY KEY,
  `IS_MOBILE` bit(1),
  `GB` int,
  `GB_FEE` decimal(6,2),

  CONSTRAINT `internet_service_ibfk_1` FOREIGN KEY (`ID`)
   REFERENCES `service` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);
