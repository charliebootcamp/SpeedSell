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
-- Table structure for table `persons`
--

DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persons` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `passwordHash` varchar(255) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `creditHash` varchar(255) DEFAULT NULL,
  `isActive` tinyint(4) NOT NULL,
  `homeAddress` varchar(512) DEFAULT NULL,
  `fullName` varchar(128) NOT NULL,
  `typeId` int(11) NOT NULL,
  `verified` tinyint(1) NOT NULL DEFAULT '0',
  `verificationCode` varchar(255) DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastLogin` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `passwordChangeCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UQ__Person__870C3C8BCBC9E0CF` (`phone`),
  UNIQUE KEY `UQ__Person__AB6E6164142841EC` (`email`),
  UNIQUE KEY `UQ__Person__F3DBC572F145E5B5` (`username`),
  KEY `typeId` (`typeId`),
  CONSTRAINT `persons_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `usertypes` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons`
--

LOCK TABLES `persons` WRITE;
/*!40000 ALTER TABLE `persons` DISABLE KEYS */;
INSERT INTO `persons` VALUES (0,'batman','011c945f30ce2cbafc452f39840f025693339c42','batman@gmail.com','0654924',NULL,1,'Gothem','Bruce Wayne',0,1,NULL,'2016-08-16 09:46:55','2016-09-19 10:40:35','2016-09-22 17:15:34',NULL),(4,'superman','011c945f30ce2cbafc452f39840f025693339c42','superman@gmail.com','0532443',NULL,1,'Budka','Kurwa Pes',0,1,NULL,'2016-08-16 09:46:55','2016-08-16 09:46:55','2016-09-22 16:01:21',NULL),(25,'nik','011c945f30ce2cbafc452f39840f025693339c42','mykola.dverii@edvantis.com','56',NULL,1,'Stryi','Mykola Dverii',1,1,NULL,'2016-08-17 09:25:52','2016-09-01 14:35:59','2016-09-22 17:15:13','646941003765775203'),(26,'flash','011c945f30ce2cbafc452f39840f025693339c42','flash@yopmail.com','123123123',NULL,1,NULL,'flash',0,1,NULL,'2016-08-18 13:15:15','2016-08-18 13:15:15','2016-08-18 13:15:15',NULL),(27,'mi','f56d6351aa71cff0debea014d13525e42036187a','nick.dveriy95@gmail.com','12314156',NULL,1,NULL,'mimi',0,1,NULL,'2016-08-18 13:17:22','2016-08-18 13:17:22','2016-08-31 08:17:42','9040745761947668444'),(42,'bohdanbodnar','a554d1f207499937754913b58c1b13a78f2e85bb','bodnarbog93@gmail.com','0638541749',NULL,1,'Ryasne','Bohdan Bodnar',0,1,NULL,'2016-08-23 09:25:09','2016-09-15 14:38:03','2016-09-22 16:56:45',NULL),(43,'btrew','b14ccd66e6cd0c956a53fedb8e4c3d10890cb2d2','sfhny5@yopmail.com','1e4hg14',NULL,1,NULL,'berbn',0,1,NULL,'2016-08-23 10:19:03','2016-08-23 10:19:03','2016-08-23 10:19:03',NULL),(44,'doba228','9ac20922b054316be23842a5bca7d69f29f69d77','mykhailo.dobosh@edvantis.com','0631903290',NULL,1,NULL,'Mykhailo',0,1,NULL,'2016-08-23 15:11:18','2016-08-23 15:11:18','2016-08-23 17:12:52',NULL),(45,'juyryj','011c945f30ce2cbafc452f39840f025693339c42','wwqrt@rtew','wsergwerg',NULL,1,NULL,'rejryy',0,0,'1780983829731909008','2016-08-25 10:10:02','2016-08-25 10:10:02','2016-08-25 10:10:02',NULL),(46,'f4rew','da39a3ee5e6b4b0d3255bfef95601890afd80709','fg43q@fvsr','2',NULL,1,NULL,'f43',0,0,'1759103474388582716','2016-08-25 13:39:38','2016-08-25 13:39:38','2016-08-25 13:39:38',NULL),(48,'qweqwer','39dfa55283318d31afe5a3ff4a0e3253e2045e43','gjnfg@yopmail.com','13456245735',NULL,1,NULL,'dwqdq',0,1,NULL,'2016-08-30 17:03:51','2016-08-30 17:03:51','2016-08-30 17:03:51',NULL),(49,'Bohdan','e6f37ce07731a942596d63cde99f1e23ac5183a1','bohdan.bodnsssar@edvantis.com','0638541746',NULL,1,'Lviv','Bohdan Bodnar',0,1,NULL,'2016-09-07 13:23:16','2016-09-07 13:28:02','2016-09-14 09:15:51',NULL),(51,'admin','011c945f30ce2cbafc452f39840f025693339c42','admin@example.com','123123125',NULL,1,NULL,'Admin Admin',1,1,NULL,'2016-09-07 13:23:16','2016-09-07 13:28:02','2016-09-21 10:25:21',NULL),(52,'viktor','1a9b9508b6003b68ddfe03a9c8cbc4bd4388339b','viktor.sadovyi@edvantis.com','1234567890',NULL,1,NULL,'viktor',0,0,'9014731013108201314','2016-09-15 11:16:48','2016-09-15 11:16:49','2016-09-15 11:16:49',NULL),(53,'qwe','fea7f657f56a2a448da7d4b535ee5e279caf3d9a','qwe@qweq','1234567880',NULL,1,NULL,'qwe',0,0,'7676875036991349531','2016-09-15 11:17:39','2016-09-15 11:17:39','2016-09-15 11:17:39',NULL),(54,'ivan','7c4a8d09ca3762af61e59520943dc26494f8941b','ivan@gmail.com','0123456789',NULL,1,NULL,'ivan',0,0,'3798362210490500027','2016-09-16 06:55:06','2016-09-16 06:55:04','2016-09-16 06:55:04',NULL),(55,'test','3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d','vidus@polyfaust.com','0991235555',NULL,1,NULL,'teset',0,1,NULL,'2016-09-16 07:08:08','2016-09-16 07:08:06','2016-09-16 07:08:06',NULL),(62,'ni','a642a77abd7d4f51bf9226ceaf891fcbb5b299b8','mykola.dverii@edvantis.ru','1231422256',NULL,1,NULL,'tyumj',0,0,'8890613097990535756','2016-09-19 13:37:04','2016-09-19 13:37:03','2016-09-19 13:37:03',NULL),(63,'nyt','a642a77abd7d4f51bf9226ceaf891fcbb5b299b8','mykola.dverii@edvantis.cyr','1231422253',NULL,1,NULL,'aedrhaqe',0,0,'5454352059129698597','2016-09-19 13:48:37','2016-09-19 13:48:37','2016-09-19 13:48:37',NULL),(64,'nijio','2ea6201a068c5fa0eea5d81a3863321a87f8d533','mykola.dverii@edvantis.cop','1231422259',NULL,1,NULL,'uhi0o',0,0,'8970250142301947057','2016-09-19 14:08:22','2016-09-19 14:08:22','2016-09-19 14:08:22',NULL);
/*!40000 ALTER TABLE `persons` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-22 20:24:59
