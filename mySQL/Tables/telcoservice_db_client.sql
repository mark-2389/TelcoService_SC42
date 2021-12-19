<<<<<<< HEAD
CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `USERNAME` varchar(255) NOT NULL,
  `PASSWORD` varchar(31) NOT NULL,
  `MAIL` varchar(127) NOT NULL,
  `NUMBER_REJECTION` int DEFAULT '0',
  `INSOLVENT` enum('solvent','insolvent') DEFAULT 'solvent',
  PRIMARY KEY (`USERNAME`),
  CONSTRAINT `client_chk_1` CHECK ((`NUMBER_REJECTION` >= 0))
=======
CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `USERNAME` varchar(255) NOT NULL,
  `PASSWORD` varchar(31) NOT NULL,
  `MAIL` varchar(127) NOT NULL,
  `NUMBER_REJECTION` int DEFAULT '0',
  `INSOLVENT` enum('solvent','insolvent') DEFAULT 'solvent',
  PRIMARY KEY (`USERNAME`),
  CONSTRAINT `client_chk_1` CHECK ((`NUMBER_REJECTION` >= 0))
>>>>>>> branch 'main' of https://github.com/niccolodidoni/TelcoService_SC42
);