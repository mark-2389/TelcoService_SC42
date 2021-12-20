CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
    `USERNAME` varchar(255) PRIMARY KEY,
    `PASSWORD` varchar(31) NOT NULL,
    `EMAIL` varchar(255) NOT NULL UNIQUE
);