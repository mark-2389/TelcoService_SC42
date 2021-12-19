CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `order_optional_composition`;
CREATE TABLE `order_optional_composition` (

  `ORDER_ID` int,
  `OPTIONAL_PRODUCT_ID` int,

  PRIMARY KEY (`ORDER_ID`,`OPTIONAL_PRODUCT_ID`),

  CONSTRAINT `order_optional_composition_ibfk_1` FOREIGN KEY (`ORDER_ID`)
   REFERENCES `order` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,

  CONSTRAINT `order_optional_composition_ibfk_2` FOREIGN KEY (`OPTIONAL_PRODUCT_ID`)
   REFERENCES `optional_product` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);