CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (

  `USERNAME` varchar(255) NOT NULL,
  `PASSWORD` varchar(31)  NOT NULL,
  `EMAIL` varchar(255) NOT NULL UNIQUE,
  `NUMBER_REJECTIONS` int DEFAULT 0,  -- Keeps track of the number of rejections
  `INSOLVENT` enum('SOLVENT','INSOLVENT') DEFAULT 'SOLVENT',  -- A client is SOLVENT (INSOLVENT) if there are no REJECTED orders (there is at least one rejected order)

  PRIMARY KEY (`USERNAME`),

  CONSTRAINT `client_chk_1` CHECK ((`NUMBER_REJECTIONS` >= 0))
);