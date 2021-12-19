CREATE DATABASE  IF NOT EXISTS `telcoservice_db` ;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `validity`;

CREATE TABLE `validity` (

  `ID` int,
  `PACKAGE_ID` int,
  `PERIOD` int,
  `MONTHLY_FEE` decimal(6,2) DEFAULT 0.00,
  `EXPIRATION_DATE` date DEFAULT NULL,  -- NULL = it never expires
  -- TODO consider adding a VALID field
  -- TODO consider adding a TOTAL_COST field as MONTHLY_FEE * PERIOD to help triggers and order tuples

  PRIMARY KEY (`ID`,`PACKAGE_ID`)
);