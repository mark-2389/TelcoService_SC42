<<<<<<< HEAD
CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `optional_product`;
CREATE TABLE `optional_product` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  `MONTHLY_FEE` decimal(4,2) DEFAULT NULL,
  `EXPIRATION_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `optional_product_chk_1` CHECK ((`MONTHLY_FEE` >= 0))
);
=======
CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `optional_product`;
CREATE TABLE `optional_product` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  `MONTHLY_FEE` decimal(4,2) DEFAULT NULL,
  `EXPIRATION_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `optional_product_chk_1` CHECK ((`MONTHLY_FEE` >= 0))
);
>>>>>>> branch 'main' of https://github.com/niccolodidoni/TelcoService_SC42
