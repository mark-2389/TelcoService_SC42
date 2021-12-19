CREATE DATABASE  IF NOT EXISTS `telcoservice_db`;
USE `telcoservice_db`;

DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (

    `USERNAME` varchar(255) NOT NULL,
    `PASSWORD` varchar(31) NOT NULL,
    `EMAIL` varchar(255) NOT NULL UNIQUE,

    PRIMARY KEY (`USERNAME`)
);