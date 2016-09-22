CREATE DATABASE  IF NOT EXISTS `charlie_speedsell` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `charlie_speedsell`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: bootcamp.edvantis.com    Database: charlie_speedsell
-- ------------------------------------------------------
-- Server version	5.7.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `feedbacks`
--

DROP TABLE IF EXISTS `feedbacks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedbacks` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `senderId` int(11) NOT NULL,
  `recipientId` int(11) NOT NULL,
  `lotId` int(11) NOT NULL,
  `markId` int(11) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  `submitDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  KEY `lotId` (`lotId`),
  KEY `markId` (`markId`),
  KEY `feedbacks_ibfk_1` (`senderId`),
  KEY `feedbacks_ibfk_4` (`recipientId`),
  CONSTRAINT `feedbacks_ibfk_1` FOREIGN KEY (`senderId`) REFERENCES `persons` (`Id`),
  CONSTRAINT `feedbacks_ibfk_2` FOREIGN KEY (`lotId`) REFERENCES `lots` (`Id`),
  CONSTRAINT `feedbacks_ibfk_3` FOREIGN KEY (`markId`) REFERENCES `marktypes` (`Id`),
  CONSTRAINT `feedbacks_ibfk_4` FOREIGN KEY (`recipientId`) REFERENCES `persons` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedbacks`
--

LOCK TABLES `feedbacks` WRITE;
/*!40000 ALTER TABLE `feedbacks` DISABLE KEYS */;
INSERT INTO `feedbacks` VALUES (1,0,4,0,0,'Kripnonit','Nice shtyla for kill superman','2016-08-18 11:08:46'),(2,4,0,10,2,'Good','Good buyer.','2016-08-18 11:08:46'),(4,0,4,10,1,'Good Tv','Nice lot, good seller, fast responce','2016-08-25 11:37:26'),(7,42,0,18,2,'Great ball','but it is stolen','2016-08-31 11:06:26'),(8,0,42,18,1,'Good customer','fast responce','2016-08-31 13:40:03'),(10,0,42,22,1,'Nice client','Recommend for cooperation','2016-09-14 10:08:33');
/*!40000 ALTER TABLE `feedbacks` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-22 20:25:00
