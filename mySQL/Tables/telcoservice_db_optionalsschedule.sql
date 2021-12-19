CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `optionalsschedule`;
CREATE TABLE `optionalsschedule` (
  `USERNAME` varchar(255) NOT NULL,
  `OPTIONALPRODUCTID` int NOT NULL,
  `ACTIVATIONDATE` date DEFAULT NULL,
  `DEACTIVATIONDATE` date DEFAULT NULL,
  PRIMARY KEY (`USERNAME`,`OPTIONALPRODUCTID`),
  KEY `OPTIONALPRODUCTID` (`OPTIONALPRODUCTID`),
  CONSTRAINT `optionalsschedule_ibfk_1` FOREIGN KEY (`USERNAME`)
   REFERENCES `client` (`USERNAME`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT `optionalsschedule_ibfk_2` FOREIGN KEY (`OPTIONALPRODUCTID`)
   REFERENCES `optional_product` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);