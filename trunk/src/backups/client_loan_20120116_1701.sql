-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.58-1ubuntu1


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema client_loan
--

CREATE DATABASE IF NOT EXISTS client_loan;
USE client_loan;

--
-- Definition of table `client_loan`.`ClientLoan`
--

DROP TABLE IF EXISTS `client_loan`.`ClientLoan`;
CREATE TABLE  `client_loan`.`ClientLoan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `email` varchar(64) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `employmentStatus` varchar(32) DEFAULT NULL,
  `propertyName` varchar(255) DEFAULT NULL,
  `loanAmount` float NOT NULL,
  `loanProgram` varchar(32) NOT NULL,
  `rate` float NOT NULL,
  `lastClosingDate` date NOT NULL,
  `refiInterval` int(11) DEFAULT '4',
  `status` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client_loan`.`ClientLoan`
--

/*!40000 ALTER TABLE `ClientLoan` DISABLE KEYS */;
LOCK TABLES `ClientLoan` WRITE;
INSERT INTO `client_loan`.`ClientLoan` VALUES  (1,'jumbo','jumbo@ggg','2222222','dkdkdkdk','kaka','laldkfj',3333.33,'ooqoqoq',3.8,'2011-01-28',4,'terminated'),
 (2,'chow','chow@lalll','1111111111','qqiqiqiqi','employed','cmcmcmcmcm',1e+07,';;;',4,'2011-01-01',4,'terminated'),
 (3,'jumbo chow','jumboalex@hotmail.com','2062062062','yadayadayada','google','',240000,'30 year fix',4.125,'2010-01-10',4,'terminated'),
 (4,'Zhang Fenghui','','','','employed','',315000,'7/1',3.5,'2010-01-30',4,'active'),
 (5,'test','','','','employed','',3.93939e+06,'alalala',4,'2012-01-01',4,'terminated'),
 (6,'test name','','','','employed','',66666,'ada',3.5,'2012-01-01',4,'terminated');
UNLOCK TABLES;
/*!40000 ALTER TABLE `ClientLoan` ENABLE KEYS */;


--
-- Definition of table `client_loan`.`clients`
--

DROP TABLE IF EXISTS `client_loan`.`clients`;
CREATE TABLE  `client_loan`.`clients` (
  `client_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `email` varchar(64) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `employment` varchar(32) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client_loan`.`clients`
--

/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
LOCK TABLES `clients` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;


--
-- Definition of table `client_loan`.`loans`
--

DROP TABLE IF EXISTS `client_loan`.`loans`;
CREATE TABLE  `client_loan`.`loans` (
  `loan_id` int(11) NOT NULL AUTO_INCREMENT,
  `loan_amount` float NOT NULL,
  `loan_program` varchar(64) NOT NULL,
  `rate` float NOT NULL,
  `closing_date` date NOT NULL,
  `refi_interval` int(11) DEFAULT '4' COMMENT 'unit is month',
  `property_name` varchar(128) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1',
  `client_id` int(11) NOT NULL,
  `loan_officer_id` int(11) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `notes` text,
  PRIMARY KEY (`loan_id`),
  KEY `loan_client_constraint` (`client_id`),
  KEY `loan_officer_constraint` (`loan_officer_id`),
  KEY `loan_status_constraint` (`status`),
  CONSTRAINT `loan_status_constraint` FOREIGN KEY (`status`) REFERENCES `status` (`status_id`),
  CONSTRAINT `loan_client_constraint` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`),
  CONSTRAINT `loan_officer_constraint` FOREIGN KEY (`loan_officer_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client_loan`.`loans`
--

/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
LOCK TABLES `loans` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `loans` ENABLE KEYS */;


--
-- Definition of table `client_loan`.`status`
--

DROP TABLE IF EXISTS `client_loan`.`status`;
CREATE TABLE  `client_loan`.`status` (
  `status_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `description` text,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client_loan`.`status`
--

/*!40000 ALTER TABLE `status` DISABLE KEYS */;
LOCK TABLES `status` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `status` ENABLE KEYS */;


--
-- Definition of table `client_loan`.`user_logins`
--

DROP TABLE IF EXISTS `client_loan`.`user_logins`;
CREATE TABLE  `client_loan`.`user_logins` (
  `user_login_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `password` varchar(256) NOT NULL,
  PRIMARY KEY (`user_login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client_loan`.`user_logins`
--

/*!40000 ALTER TABLE `user_logins` DISABLE KEYS */;
LOCK TABLES `user_logins` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `user_logins` ENABLE KEYS */;


--
-- Definition of table `client_loan`.`user_types`
--

DROP TABLE IF EXISTS `client_loan`.`user_types`;
CREATE TABLE  `client_loan`.`user_types` (
  `user_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(32) NOT NULL,
  `description` text,
  PRIMARY KEY (`user_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client_loan`.`user_types`
--

/*!40000 ALTER TABLE `user_types` DISABLE KEYS */;
LOCK TABLES `user_types` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `user_types` ENABLE KEYS */;


--
-- Definition of table `client_loan`.`users`
--

DROP TABLE IF EXISTS `client_loan`.`users`;
CREATE TABLE  `client_loan`.`users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1',
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `user_type_constraint` (`type`),
  KEY `user_status_constraint` (`status`),
  CONSTRAINT `user_status_constraint` FOREIGN KEY (`status`) REFERENCES `status` (`status_id`),
  CONSTRAINT `user_type_constraint` FOREIGN KEY (`type`) REFERENCES `user_types` (`user_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client_loan`.`users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
LOCK TABLES `users` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
