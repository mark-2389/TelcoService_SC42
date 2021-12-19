CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `internetservice`;
CREATE TABLE `internetservice` (
  `ID` int NOT NULL,
  `ISMOBILE` bit(1) DEFAULT NULL,
  `GB` int DEFAULT NULL,
  `GBFEE` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `internetservice_ibfk_1` FOREIGN KEY (`ID`)
   REFERENCES `service` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);
