CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `ID` int AUTO_INCREMENT PRIMARY KEY ,
  `CLIENT` varchar(255),
  `HOUR_CREATION` time DEFAULT current_time,
  `DATE_CREATION` date DEFAULT current_date,
  `VALIDITY_ID` int,
  `PACKAGE_ID` int,  -- The service_package we are referring to
  `DATE_SUBSCRIPTION` date, -- The date of activation of the services
  `TOTAL_COST` decimal(6, 2),
  `IS_VALID` enum('DEFAULT', 'REJECTED', 'ACCEPTED'),  -- Once the order is created it has to be payed (DEFAULT status), then REJECTED is the order's payment is rejected, ACCEPTED otherwise
  `NUMBER_REJECTIONS` int DEFAULT 0,

  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`CLIENT`)
   REFERENCES `client` (`USERNAME`)
      ON DELETE NO ACTION  -- For history purposes no delete cascade
      ON UPDATE CASCADE,

  CONSTRAINT `order_ibfk_2` FOREIGN KEY (`VALIDITY_ID`)
   REFERENCES `validity` (`ID`)
      ON DELETE RESTRICT  -- Restrict is to deny the deletion of the referenced validity period
      ON UPDATE RESTRICT,

   CONSTRAINT `order_chk_1` CHECK (`NUMBER_REJECTIONS` >= 0)
);