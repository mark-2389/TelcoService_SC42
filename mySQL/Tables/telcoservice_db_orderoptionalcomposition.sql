<<<<<<< HEAD
CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `orderoptionalcomposition`;
CREATE TABLE `orderoptionalcomposition` (
  `ORDERID` int NOT NULL,
  `OPTIONALPRODUCTID` int NOT NULL,
  PRIMARY KEY (`ORDERID`,`OPTIONALPRODUCTID`),
  KEY `OPTIONALPRODUCTID` (`OPTIONALPRODUCTID`),
  CONSTRAINT `orderoptionalcomposition_ibfk_1` FOREIGN KEY (`ORDERID`)
   REFERENCES `order` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT `orderoptionalcomposition_ibfk_2` FOREIGN KEY (`OPTIONALPRODUCTID`)
   REFERENCES `optional_product` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);
=======
CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `orderoptionalcomposition`;
CREATE TABLE `orderoptionalcomposition` (
  `ORDERID` int NOT NULL,
  `OPTIONALPRODUCTID` int NOT NULL,
  PRIMARY KEY (`ORDERID`,`OPTIONALPRODUCTID`),
  KEY `OPTIONALPRODUCTID` (`OPTIONALPRODUCTID`),
  CONSTRAINT `orderoptionalcomposition_ibfk_1` FOREIGN KEY (`ORDERID`)
   REFERENCES `order` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT `orderoptionalcomposition_ibfk_2` FOREIGN KEY (`OPTIONALPRODUCTID`)
   REFERENCES `optional_product` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);
>>>>>>> branch 'main' of https://github.com/niccolodidoni/TelcoService_SC42
