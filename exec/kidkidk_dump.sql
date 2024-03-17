-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: kdkd
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `child`
--

DROP TABLE IF EXISTS `child`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `child` (
  `coin` int DEFAULT NULL,
  `fund_money` int DEFAULT NULL,
  `child_id` bigint NOT NULL,
  PRIMARY KEY (`child_id`),
  CONSTRAINT `FKan809c5ywbh5hj4eqpuhadrcy` FOREIGN KEY (`child_id`) REFERENCES `profile` (`profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `child`
--

LOCK TABLES `child` WRITE;
/*!40000 ALTER TABLE `child` DISABLE KEYS */;
/*!40000 ALTER TABLE `child` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deposit`
--

DROP TABLE IF EXISTS `deposit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deposit` (
  `amount` int DEFAULT NULL,
  `money` int DEFAULT NULL,
  `type` bit(1) DEFAULT NULL,
  `child_id` bigint DEFAULT NULL,
  `data_log` datetime(6) DEFAULT NULL,
  `deposit_id` bigint NOT NULL AUTO_INCREMENT,
  `detail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`deposit_id`),
  KEY `FKemq7ymh390w81ar5ih77odkvb` (`child_id`),
  CONSTRAINT `FKemq7ymh390w81ar5ih77odkvb` FOREIGN KEY (`child_id`) REFERENCES `child` (`child_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deposit`
--

LOCK TABLES `deposit` WRITE;
/*!40000 ALTER TABLE `deposit` DISABLE KEYS */;
/*!40000 ALTER TABLE `deposit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `education`
--

DROP TABLE IF EXISTS `education`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `education` (
  `education_id` bigint NOT NULL AUTO_INCREMENT,
  `category` enum('SAVING','FUND','PROPERTY','TAX','SUPPLYANDDEMAND','PRODUCTIONANDCONSUMPTION','EXCHANGERATE','COIN') DEFAULT NULL,
  `content` longtext,
  PRIMARY KEY (`education_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `education`
--

LOCK TABLES `education` WRITE;
/*!40000 ALTER TABLE `education` DISABLE KEYS */;
/*!40000 ALTER TABLE `education` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund`
--

DROP TABLE IF EXISTS `fund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund` (
  `yield` int DEFAULT NULL,
  `fund_id` bigint NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fund_id`),
  CONSTRAINT `FKn8vp2sun3s2u33fci2bc03lh5` FOREIGN KEY (`fund_id`) REFERENCES `child` (`child_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund`
--

LOCK TABLES `fund` WRITE;
/*!40000 ALTER TABLE `fund` DISABLE KEYS */;
/*!40000 ALTER TABLE `fund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_history`
--

DROP TABLE IF EXISTS `fund_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_history` (
  `pnl` int DEFAULT NULL,
  `seed_money` int DEFAULT NULL,
  `yield` int DEFAULT NULL,
  `child_id` bigint DEFAULT NULL,
  `data_log` datetime(6) DEFAULT NULL,
  `fund_history_id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`fund_history_id`),
  KEY `FK5qi3ggm3eoii2pwkwirs4flqx` (`child_id`),
  CONSTRAINT `FK5qi3ggm3eoii2pwkwirs4flqx` FOREIGN KEY (`child_id`) REFERENCES `child` (`child_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_history`
--

LOCK TABLES `fund_history` WRITE;
/*!40000 ALTER TABLE `fund_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `fund_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_news`
--

DROP TABLE IF EXISTS `fund_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_news` (
  `child_id` bigint DEFAULT NULL,
  `data_log` datetime(6) DEFAULT NULL,
  `fund_news_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fund_news_id`),
  KEY `FK3r8kharr0pgia4uoom3y0fkay` (`child_id`),
  CONSTRAINT `FK3r8kharr0pgia4uoom3y0fkay` FOREIGN KEY (`child_id`) REFERENCES `child` (`child_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_news`
--

LOCK TABLES `fund_news` WRITE;
/*!40000 ALTER TABLE `fund_news` DISABLE KEYS */;
/*!40000 ALTER TABLE `fund_news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_reservation`
--

DROP TABLE IF EXISTS `fund_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_reservation` (
  `state` bit(1) DEFAULT NULL,
  `yield` int DEFAULT NULL,
  `fund_reservation_id` bigint NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fund_reservation_id`),
  CONSTRAINT `FKm529p0574xjuixf5n3ls56f8k` FOREIGN KEY (`fund_reservation_id`) REFERENCES `child` (`child_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_reservation`
--

LOCK TABLES `fund_reservation` WRITE;
/*!40000 ALTER TABLE `fund_reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `fund_reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_status`
--

DROP TABLE IF EXISTS `fund_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_status` (
  `amount` int DEFAULT NULL,
  `answer` bit(1) DEFAULT NULL,
  `submit` bit(1) DEFAULT NULL,
  `fund_status_id` bigint NOT NULL,
  PRIMARY KEY (`fund_status_id`),
  CONSTRAINT `FKrutshwi1fx356arjdyl2mwbdc` FOREIGN KEY (`fund_status_id`) REFERENCES `fund` (`fund_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_status`
--

LOCK TABLES `fund_status` WRITE;
/*!40000 ALTER TABLE `fund_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `fund_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `done_count` int DEFAULT NULL,
  `task_amount` int DEFAULT NULL,
  `wage` int DEFAULT NULL,
  `job_id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `task` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  CONSTRAINT `FKfa5l8t6yojmhla9gexxjusp1w` FOREIGN KEY (`job_id`) REFERENCES `child` (`child_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_reservation`
--

DROP TABLE IF EXISTS `job_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_reservation` (
  `state` bit(1) DEFAULT NULL,
  `task_amount` int DEFAULT NULL,
  `wage` int DEFAULT NULL,
  `job_reservation_id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `task` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`job_reservation_id`),
  CONSTRAINT `FKir7y2gnpu0p4c8n9wifbtixn4` FOREIGN KEY (`job_reservation_id`) REFERENCES `child` (`child_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_reservation`
--

LOCK TABLES `job_reservation` WRITE;
/*!40000 ALTER TABLE `job_reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parent`
--

DROP TABLE IF EXISTS `parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parent` (
  `parent_id` bigint NOT NULL,
  PRIMARY KEY (`parent_id`),
  CONSTRAINT `FK3inlojyp6ixcimtn040bun4gn` FOREIGN KEY (`parent_id`) REFERENCES `profile` (`profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parent`
--

LOCK TABLES `parent` WRITE;
/*!40000 ALTER TABLE `parent` DISABLE KEYS */;
/*!40000 ALTER TABLE `parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile` (
  `pin` int DEFAULT NULL,
  `type` bit(1) DEFAULT NULL,
  `profile_id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `profile_image` tinytext,
  PRIMARY KEY (`profile_id`),
  KEY `FKawh070wpue34wqvytjqr4hj5e` (`user_id`),
  CONSTRAINT `FKawh070wpue34wqvytjqr4hj5e` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_seq`
--

DROP TABLE IF EXISTS `profile_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_seq`
--

LOCK TABLES `profile_seq` WRITE;
/*!40000 ALTER TABLE `profile_seq` DISABLE KEYS */;
INSERT INTO `profile_seq` VALUES (1);
/*!40000 ALTER TABLE `profile_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz` (
  `answer` bit(1) DEFAULT NULL,
  `quiz_id` bigint NOT NULL AUTO_INCREMENT,
  `category` enum('SAVING','FUND','PROPERTY','TAX','SUPPLYANDDEMAND','PRODUCTIONANDCONSUMPTION','EXCHANGERATE','COIN') DEFAULT NULL,
  `question` tinytext,
  PRIMARY KEY (`quiz_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roi`
--

DROP TABLE IF EXISTS `roi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roi` (
  `count` int DEFAULT NULL,
  `success` int DEFAULT NULL,
  `roi_id` bigint NOT NULL,
  PRIMARY KEY (`roi_id`),
  CONSTRAINT `FKkgclbvw07g3574rcc7vggwwef` FOREIGN KEY (`roi_id`) REFERENCES `child` (`child_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roi`
--

LOCK TABLES `roi` WRITE;
/*!40000 ALTER TABLE `roi` DISABLE KEYS */;
/*!40000 ALTER TABLE `roi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saving`
--

DROP TABLE IF EXISTS `saving`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saving` (
  `count` int DEFAULT NULL,
  `payment` int DEFAULT NULL,
  `rate` int DEFAULT NULL,
  `saving_id` bigint NOT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`saving_id`),
  CONSTRAINT `FK5t5ny7xeq6uwy9lqams9uqie4` FOREIGN KEY (`saving_id`) REFERENCES `child` (`child_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saving`
--

LOCK TABLES `saving` WRITE;
/*!40000 ALTER TABLE `saving` DISABLE KEYS */;
/*!40000 ALTER TABLE `saving` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saving_history`
--

DROP TABLE IF EXISTS `saving_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saving_history` (
  `amount` int DEFAULT NULL,
  `type` bit(1) DEFAULT NULL,
  `child_id` bigint DEFAULT NULL,
  `data_log` datetime(6) DEFAULT NULL,
  `saving_history_id` bigint NOT NULL AUTO_INCREMENT,
  `detail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`saving_history_id`),
  KEY `FK3horse8h431je6bvjgj10177m` (`child_id`),
  CONSTRAINT `FK3horse8h431je6bvjgj10177m` FOREIGN KEY (`child_id`) REFERENCES `child` (`child_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saving_history`
--

LOCK TABLES `saving_history` WRITE;
/*!40000 ALTER TABLE `saving_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `saving_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `access_token` varchar(2000) DEFAULT NULL,
  `email` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-16  3:53:16
