CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `optional_product_composition`;
CREATE TABLE `optional_product_composition` (

  `PACKAGE_ID` int,
  `OPTIONAL_PRODUCT_ID` int,

  PRIMARY KEY (`PACKAGE_ID`,`OPTIONAL_PRODUCT_ID`),

  CONSTRAINT `optional_product_composition_ibfk_1` FOREIGN KEY (`PACKAGE_ID`)
   REFERENCES `service_package` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,

  CONSTRAINT `optional_product_composition_ibfk_2` FOREIGN KEY (`OPTIONAL_PRODUCT_ID`)
   REFERENCES `optional_product` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);