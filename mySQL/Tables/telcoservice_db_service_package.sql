CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `service_package`;

CREATE TABLE `service_package` (
  `ID` int AUTO_INCREMENT PRIMARY KEY,
  `NAME` varchar(31) CHARACTER SET 'utf8' COLLATE 'utf8_bin',
  `EXPIRATION_DATE` date DEFAULT NULL  -- NULL = it never expires
);
