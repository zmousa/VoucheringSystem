-- MySQL dump 10.13  Distrib 5.6.19, for Win64 (x86_64)
--
-- Host: localhost   
-- ------------------------------------------------------
-- Server version	5.6.23-enterprise-commercial-advanced-log

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
-- Table structure for table `beneficiary`
--

DROP TABLE IF EXISTS `beneficiary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `beneficiary` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beneficiary`
--

LOCK TABLES `beneficiary` WRITE;
/*!40000 ALTER TABLE `beneficiary` DISABLE KEYS */;
INSERT INTO `beneficiary` VALUES ('VF001','John Smith','M',20),('VF002','Ben Foster','M',40),('VF003','Julia Rey','F',18);
/*!40000 ALTER TABLE `beneficiary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` varchar(1) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('A','Voucher for Seed'),('B','Voucher for Work'),('C','Voucher for Foods');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `distribution`
--

DROP TABLE IF EXISTS `distribution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `distribution` (
  `id` varchar(50) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `distribution`
--

LOCK TABLES `distribution` WRITE;
/*!40000 ALTER TABLE `distribution` DISABLE KEYS */;
INSERT INTO `distribution` VALUES ('03ADD49595294BE79F667EB2A5328C0C','2016-01-28 19:48:22'),('5B40E61C3FD940A2BFBDE0DD77ED18F9','2016-01-28 19:48:21'),('EC835128FE0B42F69D2106255D9B9131','2016-01-28 19:48:23');
/*!40000 ALTER TABLE `distribution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `distribution_detail`
--

DROP TABLE IF EXISTS `distribution_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `distribution_detail` (
  `id` varchar(50) NOT NULL,
  `distribution_id` varchar(50) NOT NULL,
  `category` varchar(1) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `distribution_detail`
--

LOCK TABLES `distribution_detail` WRITE;
/*!40000 ALTER TABLE `distribution_detail` DISABLE KEYS */;
INSERT INTO `distribution_detail` VALUES ('4208B840383B44BBA167110CACDDAA2C','5B40E61C3FD940A2BFBDE0DD77ED18F9','B',2000),('5F3E3C2781494244ADE711692CE254E2','5B40E61C3FD940A2BFBDE0DD77ED18F9','A',3000),('9097641C50CC49068901213BB7FF078A','03ADD49595294BE79F667EB2A5328C0C','C',5000),('9A5D9CDC82B54137BEDDD529DBDE7AA9','EC835128FE0B42F69D2106255D9B9131','B',4000),('D8C78FC6EE414832BA5F7C42F7D2124F','03ADD49595294BE79F667EB2A5328C0C','A',1000);
/*!40000 ALTER TABLE `distribution_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jee_user`
--

DROP TABLE IF EXISTS `jee_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jee_user` (
  `id` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  `first_name` varchar(200) DEFAULT NULL,
  `last_name` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `middle_name` varchar(200) DEFAULT NULL,
  `department` varchar(200) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jee_user`
--

LOCK TABLES `jee_user` WRITE;
/*!40000 ALTER TABLE `jee_user` DISABLE KEYS */;
INSERT INTO `jee_user` VALUES ('1000','admin','admin',1,NULL,NULL,NULL,NULL,NULL,NULL),('1001','viewer','viewer',1,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `jee_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participation`
--

DROP TABLE IF EXISTS `participation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `participation` (
  `id` varchar(50) NOT NULL,
  `distribution_id` varchar(50) NOT NULL,
  `beneficiary_id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participation`
--

LOCK TABLES `participation` WRITE;
/*!40000 ALTER TABLE `participation` DISABLE KEYS */;
INSERT INTO `participation` VALUES ('1060E00CE6DA4B3C838B58AFFC985233','03ADD49595294BE79F667EB2A5328C0C','VF002'),('65D8F98A35F2463AB82BE3C7F04EA33E','EC835128FE0B42F69D2106255D9B9131','VF003'),('F7EA0449BBB246808A29A237DE001A25','5B40E61C3FD940A2BFBDE0DD77ED18F9','VF001');
/*!40000 ALTER TABLE `participation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher`
--

DROP TABLE IF EXISTS `voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voucher` (
  `code` varchar(50) NOT NULL,
  `category` varchar(1) NOT NULL,
  `value` int(11) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher`
--

LOCK TABLES `voucher` WRITE;
/*!40000 ALTER TABLE `voucher` DISABLE KEYS */;
INSERT INTO `voucher` VALUES ('A001000','A',1000),('A001001','A',1000),('A001002','A',1000),('B000000','B',2000),('B000001','B',2000),('C000500','C',500),('C000501','C',500),('C000502','C',500),('C000503','C',500),('C000504','C',500);
/*!40000 ALTER TABLE `voucher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-28 22:39:18
