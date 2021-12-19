CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `optional_product`;

CREATE TABLE `optional_product` (

  `ID` int AUTO_INCREMENT PRIMARY KEY,
  `NAME` varchar(255),
  `MONTHLY_FEE` decimal(6,2) DEFAULT 0.00,
  `EXPIRATION_DATE` date DEFAULT NULL,  -- NULL = it never expires

  CONSTRAINT `optional_product_chk_1` CHECK (`MONTHLY_FEE` >= 0)
);
