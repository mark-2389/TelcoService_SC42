CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `service_schedule`;
CREATE TABLE `service_schedule` (
  `ORDER_ID` int,
  `USERNAME` varchar(255),
  `SERVICE_ID` int,
  `ACTIVATION_DATE` date NOT NULL,  -- The date of activation of the service
  `DEACTIVATION_DATE` date NOT NULL,  -- The date of deactivation of the service

  PRIMARY KEY (`USERNAME`,`SERVICE_ID`, `ORDER_ID`),

  CONSTRAINT `service_schedule_ibfk_1` FOREIGN KEY (`USERNAME`)
  	REFERENCES `client` (`USERNAME`) 
  		ON DELETE CASCADE 
  		ON UPDATE CASCADE,

  CONSTRAINT `service_schedule_ibfk_2` FOREIGN KEY (`SERVICE_ID`)
  	REFERENCES `service` (`ID`) 
  		ON DELETE CASCADE 
  		ON UPDATE CASCADE,

  CONSTRAINT `service_schedule_ibfk_3` FOREIGN KEY (`ORDER_ID`)
      REFERENCES `order` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,

  CONSTRAINT `service_schedule_chk_1` CHECK (`DEACTIVATION_DATE` > `ACTIVATION_DATE`)
);
