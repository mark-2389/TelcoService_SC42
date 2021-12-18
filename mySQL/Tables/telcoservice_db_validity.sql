<<<<<<< HEAD
CREATE DATABASE  IF NOT EXISTS `telcoservice_db` ;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `validity`;
CREATE TABLE `validity` (
  `ID` int NOT NULL,
  `SERVICEID` int NOT NULL,
  `PERIOD` int DEFAULT NULL,
  `MONTHLY_FEE` decimal(6,2) DEFAULT '0.00',
  `EXPIRATION_DATE` date DEFAULT NULL, 
  PRIMARY KEY (`ID`,`SERVICEID`)
);
=======
CREATE DATABASE  IF NOT EXISTS `telcoservice_db` ;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `validity`;
CREATE TABLE `validity` (
  `ID` int NOT NULL,
  `SERVICEID` int NOT NULL,
  `PERIOD` int DEFAULT NULL,
  `MONTHLY_FEE` decimal(6,2) DEFAULT '0.00',
  `EXPIRATION_DATE` date DEFAULT NULL, 
  PRIMARY KEY (`ID`,`SERVICEID`)
);
>>>>>>> branch 'main' of https://github.com/niccolodidoni/TelcoService_SC42
