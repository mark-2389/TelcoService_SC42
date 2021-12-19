CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `service_composition`;
CREATE TABLE `service_composition` (
  `PACKAGE_ID` int,
  `SERVICE_ID` int,

  PRIMARY KEY (`PACKAGE_ID`,`SERVICE_ID`),

  CONSTRAINT `service_composition_ibfk_1` FOREIGN KEY (`PACKAGE_ID`)
  	REFERENCES `service_package` (`ID`) 
  		ON DELETE CASCADE 
  		ON UPDATE CASCADE,

  CONSTRAINT `service_composition_ibfk_2` FOREIGN KEY (`SERVICE_ID`)
  	REFERENCES `service` (`ID`) 
  		ON DELETE CASCADE 
  		ON UPDATE CASCADE
);
