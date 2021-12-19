CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `serviceschedule`;
CREATE TABLE `serviceschedule` (
  `USERNAME` varchar(255) NOT NULL,
  `SERVICEID` int NOT NULL,
  `ACTIVATIONDATE` date DEFAULT NULL,
  `DEACTIVATIONDATE` date DEFAULT NULL,
  PRIMARY KEY (`USERNAME`,`SERVICEID`),
  KEY `SERVICEID` (`SERVICEID`),
  CONSTRAINT `serviceschedule_ibfk_1` FOREIGN KEY (`USERNAME`) 
  	REFERENCES `client` (`USERNAME`) 
  		ON DELETE CASCADE 
  		ON UPDATE CASCADE,
  CONSTRAINT `serviceschedule_ibfk_2` FOREIGN KEY (`SERVICEID`) 
  	REFERENCES `service` (`ID`) 
  		ON DELETE CASCADE 
  		ON UPDATE CASCADE,
  CONSTRAINT `serviceschedule_chk_1` 
  	CHECK ((`deactivationDate` > `activationDate`))
);
