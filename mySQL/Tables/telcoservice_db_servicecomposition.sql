CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `servicecomposition`;
CREATE TABLE `servicecomposition` (
  `PACKAGEID` int NOT NULL,
  `SERVICEID` int NOT NULL,
  PRIMARY KEY (`PACKAGEID`,`SERVICEID`),
  KEY `SERVICEID` (`SERVICEID`),
  CONSTRAINT `servicecomposition_ibfk_1` FOREIGN KEY (`PACKAGEID`) 
  	REFERENCES `service_package` (`ID`) 
  		ON DELETE CASCADE 
  		ON UPDATE CASCADE,
  CONSTRAINT `servicecomposition_ibfk_2` FOREIGN KEY (`SERVICEID`) 
  	REFERENCES `service` (`ID`) 
  		ON DELETE CASCADE 
  		ON UPDATE CASCADE
);
