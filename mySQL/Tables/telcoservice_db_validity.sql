CREATE DATABASE  IF NOT EXISTS `telcoservice_db` ;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `validity`;

CREATE TABLE `validity` (

    `ID` int AUTO_INCREMENT,
    `PACKAGE_ID` int,
    `PERIOD` int,
    `MONTHLY_FEE` decimal(6,2) DEFAULT 0.00,
    `TOTAL_COST` decimal(6,2),
    `EXPIRATION_DATE` date DEFAULT NULL,  -- NULL = it never expires

    PRIMARY KEY (`ID`,`PACKAGE_ID`)
);