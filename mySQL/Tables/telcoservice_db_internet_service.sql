CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `internet_service`;
DROP TABLE IF EXISTS `mobile_internet_service`;
DROP TABLE IF EXISTS `fixed_internet_service`;

CREATE TABLE `mobile_internet_service` (

  `ID` int PRIMARY KEY,
  `GB` int,
  `GB_FEE` decimal(6,2),

  CONSTRAINT `mobile_internet_service_ibfk_1` FOREIGN KEY (`ID`)
   REFERENCES `service` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE `fixed_internet_service` (

    `ID` int PRIMARY KEY,
    `GB` int,
    `GB_FEE` decimal(6,2),

    CONSTRAINT `fixed_internet_service_ibfk_1` FOREIGN KEY (`ID`)
        REFERENCES `service` (`ID`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
