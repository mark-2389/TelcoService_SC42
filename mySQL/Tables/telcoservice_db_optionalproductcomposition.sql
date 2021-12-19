<<<<<<< HEAD
CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `optionalproductcomposition`;
CREATE TABLE `optionalproductcomposition` (
  `PACKAGEID` int NOT NULL,
  `OPTIONALPRODUCTID` int NOT NULL,
  PRIMARY KEY (`PACKAGEID`,`OPTIONALPRODUCTID`),
  KEY `OPTIONALPRODUCTID` (`OPTIONALPRODUCTID`),
  CONSTRAINT `optionalproductcomposition_ibfk_1` FOREIGN KEY (`PACKAGEID`)
   REFERENCES `service_package` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT `optionalproductcomposition_ibfk_2` FOREIGN KEY (`OPTIONALPRODUCTID`)
   REFERENCES `optional_product` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);
=======
CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `optionalproductcomposition`;
CREATE TABLE `optionalproductcomposition` (
  `PACKAGEID` int NOT NULL,
  `OPTIONALPRODUCTID` int NOT NULL,
  PRIMARY KEY (`PACKAGEID`,`OPTIONALPRODUCTID`),
  KEY `OPTIONALPRODUCTID` (`OPTIONALPRODUCTID`),
  CONSTRAINT `optionalproductcomposition_ibfk_1` FOREIGN KEY (`PACKAGEID`)
   REFERENCES `service_package` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT `optionalproductcomposition_ibfk_2` FOREIGN KEY (`OPTIONALPRODUCTID`)
   REFERENCES `optional_product` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);
>>>>>>> branch 'main' of https://github.com/niccolodidoni/TelcoService_SC42
