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
-- Table structure for table `lots`
--

DROP TABLE IF EXISTS `lots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lots` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `info` varchar(1024) DEFAULT NULL,
  `categoryId` int(11) NOT NULL,
  `stateId` int(11) NOT NULL DEFAULT '1',
  `buyPrice` int(11) DEFAULT NULL,
  `commission` tinyint(1) DEFAULT '0',
  `startPrice` int(11) NOT NULL DEFAULT '1',
  `startDate` datetime DEFAULT NULL,
  `duration` int(11) NOT NULL DEFAULT '86400000',
  `redemption` int(11) DEFAULT NULL,
  `ownerId` int(11) NOT NULL,
  `bidId` int(11) DEFAULT NULL,
  `img` varchar(1024) DEFAULT NULL,
  `premium` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `bidId` (`bidId`),
  KEY `categoryId` (`categoryId`),
  KEY `ownerId` (`ownerId`),
  KEY `lots_ibfk_3` (`stateId`),
  CONSTRAINT `lots_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `categories` (`Id`),
  CONSTRAINT `lots_ibfk_2` FOREIGN KEY (`ownerId`) REFERENCES `persons` (`Id`),
  CONSTRAINT `lots_ibfk_3` FOREIGN KEY (`stateId`) REFERENCES `lotstates` (`Id`),
  CONSTRAINT `lots_ibfk_4` FOREIGN KEY (`bidId`) REFERENCES `bids` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lots`
--

LOCK TABLES `lots` WRITE;
/*!40000 ALTER TABLE `lots` DISABLE KEYS */;
INSERT INTO `lots` VALUES (0,'Lenovo laptop','15\' LCD display, 8Gb RAM, 1Tb HDD',6,6,NULL,1,1,'2016-09-20 17:45:31',259200000,NULL,0,359,'laptop1.jpg',NULL),(1,'Iphone 6s','Apple iPhone 6s smartphone was launched in September 2015. The phone comes with a 4.70-inch touchscreen display with a resolution of 750 pixels by 1334 pixels at a PPI of 326 pixels per inch. Apple iPhone 6s price in India starts from Rs. 41700. ',5,3,NULL,1,1,'2016-09-22 15:25:33',86400000,2300,0,362,'apple6s.jpg',1),(10,'Samsung TV','42\' 3D, smart TV',7,3,NULL,1,100,'2016-09-22 15:25:34',234000000,NULL,4,NULL,'samsungtv.jpg',NULL),(11,'Kitty 1 month','Kitty from gold mother',30,4,NULL,1,1,'2016-09-21 17:45:51',93600000,NULL,4,NULL,'kitty.jpg',NULL),(12,'Hasky puppy','Best husky in world woth best price',22,3,NULL,1,100,'2016-09-22 15:25:34',90000000,NULL,4,NULL,'haskypuppy.jpg',NULL),(13,'GoPro','Sold cause need money',136,4,NULL,1,5,'2016-09-15 13:56:56',97200000,2300,4,NULL,'goprocamera.jpg',1),(14,'Parrot very young','Try to sold it',138,6,100,1,5,'2016-09-22 15:25:34',86400000,100,0,363,'parrot.jpg',1),(15,'Small goat','If you wanna more fun, buy it',12,3,NULL,1,1,'2016-09-22 15:25:34',86400000,100,0,NULL,'goat.jpg',NULL),(16,'Cheap modern bycycle','All details are SHIMANO Deore XT',13,4,NULL,1,5,'2016-09-15 13:56:56',87300000,2300,4,NULL,'bicycle.jpg',NULL),(17,'Cheap snowboard','snowboard for guys with 175-180 cm height',14,4,NULL,1,5,'2016-09-16 09:58:55',86400000,2300,4,NULL,'snowboard.jpg',NULL),(18,'Ball from EURO 2012','i stole this ball in euro stadium in ukraine in Donectk',15,4,NULL,1,1000,'2016-09-21 12:56:11',86400000,10000,0,NULL,'ball.jpg',NULL),(19,'Tourist bag','best chose for active people',16,4,NULL,0,5,'2016-09-21 12:56:11',86400000,NULL,25,NULL,'bag.jpg',NULL),(20,'Electric car','Tesla 50 Economic',17,4,NULL,1,5,'2016-09-21 19:15:51',86400000,2300,4,NULL,'car.jpg',NULL),(21,'YAVA the best ever','If u is MyWuk buy this',18,3,NULL,1,5,'2016-09-22 15:25:34',252000000,NULL,0,NULL,'motorcycle.jpg',1),(22,'Bus for sale!','Used Man 80 sits',19,3,NULL,1,5,'2016-09-22 15:25:34',259200000,2300,0,NULL,'bus.jpg',0),(23,'Car engine','An engine or motor is a machine designed to convert one form of energy into mechanical energy. \nHeat engines, including internal combustion engines and external combustion engines (such as steam engines), burn a fuel to create heat, which then creates a force. \nElectric motors convert electrical energy into mechanical motion; pneumatic motors use compressed air and others—such as clockwork motors in wind-up toys—use elastic energy. In biological systems, molecular motors, like myosins in muscles, use chemical energy to create forces and eventually motion.',20,4,NULL,1,5,'2016-09-21 19:11:31',86400000,NULL,25,NULL,'carengine.jpg',NULL),(87,'qwme Phone1','Some info',5,4,NULL,1,12,'2016-09-21 13:59:16',87300000,2300,0,NULL,'apple6s.jpg',NULL),(88,'qwme Phone2','Some info',5,4,NULL,1,12,'2016-09-21 13:59:16',86400000,2300,0,NULL,'apple6s.jpg',NULL),(89,'qwme Phone3','Awesome phone',5,1,NULL,0,700,'2016-09-20 13:59:16',194400000,1000,0,NULL,'apple6s.jpg',NULL),(90,'qwPhone4','Some info',5,4,NULL,1,12,'2016-09-22 13:59:16',89100000,2300,0,NULL,'apple6s.jpg',NULL),(91,'qwme Phone5','Apple iPhone 6s detailed specifications\n GENERAL\n Release date	September 2015\n Form factor	Touchscreen\n Dimensions (mm)	138.30 x 67.10 x 7.10\n Weight (g)	143.00\n Battery capacity (mAh)	1715\n Removable battery	No \\n\n Colours	Silver, Gold, Space Grey, Rose Gold\n SAR value	NA\n DISPLAY\n Screen size (inches)	4.70\n Touchscreen	Yes\n Resolution	750x1334 pixels\n Pixels per inch (PPI)	326\n HARDWARE\n Processor make	A9\n RAM	2GB\n Internal storage	16GB\n CAMERA\n Rear camera	12-megapixel\n Flash	Yes\n Front camera	5-megapixel\n SOFTWARE\n Operating System	iOS 9\n CONNECTIVITY\n Wi-Fi	Yes\n Wi-Fi standards supported	802.11 a/ b/ g/ n/ ac\n GPS	Yes\n Bluetooth	Yes, v 4.20\n NFC	Yes\n Infrared	No\n USB OTG	No\n Headphones	3.5mm\n FM	No\n SIM Type	Nano-SIM\n GSM/ CDMA<br/>	GSM\n 3G	Yes\n 4G/ LTE	Yes\n Supports 4G in India (Band 40)	Yes\n SENSORS\n Compass/ Magnetometer	Yes\n Proximity sensor	Yes\n Accelerometer	Yes\n Ambient light sensor	Yes\n Gyroscope	Yes\n Barometer	Yes\n Temperature sensor	No',5,0,NULL,1,12,'2016-09-22 13:59:16',86400000,2300,0,NULL,'apple6s.jpg',NULL),(92,'qwme Phone6','Some info',5,3,NULL,1,12,'2016-09-22 13:59:16',87300000,2300,0,NULL,'apple6s.jpg',NULL),(93,'qwme Phone7','Some info',5,3,NULL,1,12,'2016-09-22 13:59:16',187200000,2300,0,NULL,'apple6s.jpg',NULL),(94,'qwme Phone8','Some info',5,3,NULL,1,12,'2016-09-22 13:59:16',87300000,2300,0,NULL,'apple6s.jpg',NULL),(95,'qwme Phone9','Some info',5,3,NULL,1,12,'2016-09-22 13:59:16',87300000,2300,0,NULL,'apple6s.jpg',NULL),(96,'qwme Phone10','Some info',5,3,NULL,1,12,'2016-09-22 13:59:16',87300000,2300,0,NULL,'apple6s.jpg',NULL),(97,'qwme Phone11','Some info',5,3,NULL,1,12,'2016-09-22 13:59:16',87300000,2300,0,NULL,'apple6s.jpg',NULL),(98,'qwme Phone12','Some info',5,3,NULL,1,12,'2016-09-22 13:59:16',87300000,NULL,0,NULL,'apple6s.jpg',NULL),(99,'qwome Phone13','Some info',5,3,NULL,1,12,'2016-09-22 13:59:16',89100000,NULL,0,NULL,'apple6s.jpg',NULL),(115,'Laptop','Almost new laptop',6,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,42,NULL,'laptop1.jpg',NULL),(121,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(122,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(123,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(124,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(125,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(126,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(127,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(128,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(129,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(130,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(131,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(132,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(133,'Laptop','Some info',6,3,NULL,1,500,'2016-09-22 13:59:16',87300000,700,4,NULL,'laptop1.jpg',NULL),(134,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(135,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(136,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(137,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(138,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(139,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(140,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(141,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(142,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(143,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(144,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(145,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(146,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(147,'Phone','Some ingo',5,3,NULL,1,300,'2016-09-22 13:59:16',87300000,500,4,364,'apple6s.jpg',NULL),(148,'Phone','Some ingo',5,6,NULL,1,300,'2016-09-19 17:25:16',89100000,500,4,357,'apple6s.jpg',NULL),(149,'Phone','Some ingo',5,4,NULL,1,300,'2016-09-20 13:59:16',87300000,500,4,NULL,'apple6s.jpg',NULL),(153,'etst4',NULL,30,1,NULL,0,24,NULL,86400000,2424,25,NULL,NULL,1);
/*!40000 ALTER TABLE `lots` ENABLE KEYS */;
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
