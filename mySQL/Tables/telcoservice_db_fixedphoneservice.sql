<<<<<<< HEAD
CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `fixedphoneservice`;
CREATE TABLE `fixedphoneservice` (
  `ID` int NOT NULL,
  `MINUTES` int DEFAULT NULL,
  `SMS` int DEFAULT NULL,
  `MINUTESFEE` decimal(4,2) DEFAULT NULL,
  `SMSFEE` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `fixedphoneservice_ibfk_1` FOREIGN KEY (`ID`)
   REFERENCES `service` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);
=======
CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `fixedphoneservice`;
CREATE TABLE `fixedphoneservice` (
  `ID` int NOT NULL,
  `MINUTES` int DEFAULT NULL,
  `SMS` int DEFAULT NULL,
  `MINUTESFEE` decimal(4,2) DEFAULT NULL,
  `SMSFEE` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `fixedphoneservice_ibfk_1` FOREIGN KEY (`ID`)
   REFERENCES `service` (`ID`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);
>>>>>>> branch 'main' of https://github.com/niccolodidoni/TelcoService_SC42
