-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: verodatabase
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `follows`
--

DROP TABLE IF EXISTS `follows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `follows` (
  `followsID` int(11) NOT NULL AUTO_INCREMENT,
  `userFollowed` varchar(20) NOT NULL,
  `userFollows` varchar(20) NOT NULL,
  PRIMARY KEY (`followsID`),
  KEY `_idx` (`userFollowed`),
  KEY `userFollowing_idx` (`userFollows`),
  CONSTRAINT `userFollowed` FOREIGN KEY (`userFollowed`) REFERENCES `user` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userFollows` FOREIGN KEY (`userFollows`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follows`
--

LOCK TABLES `follows` WRITE;
/*!40000 ALTER TABLE `follows` DISABLE KEYS */;
INSERT INTO `follows` VALUES (1,'admin@admin.com','george@george.com'),(2,'admin@admin.com','morad@morad.com'),(3,'george@george.com','admin@admin.com'),(4,'morad@morad.com','admin@admin.com'),(5,'morad@morad.com','george@george.com'),(6,'george@george.com','morad@morad.com');
/*!40000 ALTER TABLE `follows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friendrequests`
--

DROP TABLE IF EXISTS `friendrequests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `friendrequests` (
  `requestID` int(11) NOT NULL AUTO_INCREMENT,
  `userRequests` varchar(20) NOT NULL,
  `userrequested` varchar(20) NOT NULL,
  `statusRequest` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`requestID`),
  KEY `userRequests_idx` (`userRequests`),
  KEY `userrequested_idx` (`userrequested`),
  CONSTRAINT `userRequests` FOREIGN KEY (`userRequests`) REFERENCES `user` (`email`),
  CONSTRAINT `userrequested` FOREIGN KEY (`userrequested`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friendrequests`
--

LOCK TABLES `friendrequests` WRITE;
/*!40000 ALTER TABLE `friendrequests` DISABLE KEYS */;
/*!40000 ALTER TABLE `friendrequests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `friends` (
  `idfriends` int(11) NOT NULL AUTO_INCREMENT,
  `firstFriend` varchar(20) NOT NULL,
  `secondFriend` varchar(20) NOT NULL,
  PRIMARY KEY (`idfriends`),
  KEY `firstFriend_idx` (`firstFriend`),
  KEY `secondFriend_idx` (`secondFriend`),
  CONSTRAINT `firstFriend` FOREIGN KEY (`firstFriend`) REFERENCES `user` (`email`),
  CONSTRAINT `secondFriend` FOREIGN KEY (`secondFriend`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `post` (
  `postID` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `date` datetime NOT NULL,
  `postType` set('link','location','photo') NOT NULL,
  `link` varchar(70) DEFAULT NULL,
  `photo` blob,
  `location` geometry DEFAULT NULL,
  `postDescription` mediumtext,
  `likesNum` int(11) NOT NULL DEFAULT '0',
  `commentsNum` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`postID`),
  KEY `userName_idx` (`userName`),
  CONSTRAINT `userName` FOREIGN KEY (`userName`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postreply`
--

DROP TABLE IF EXISTS `postreply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `postreply` (
  `replyID` int(11) NOT NULL AUTO_INCREMENT,
  `postID` int(11) NOT NULL,
  `userReply` varchar(20) NOT NULL,
  `like` tinyint(4) NOT NULL DEFAULT '0',
  `comment` mediumtext,
  PRIMARY KEY (`replyID`),
  KEY `postID_idx` (`postID`),
  KEY `userReply_idx` (`userReply`),
  CONSTRAINT `postID` FOREIGN KEY (`postID`) REFERENCES `post` (`postid`),
  CONSTRAINT `userReply` FOREIGN KEY (`userReply`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postreply`
--

LOCK TABLES `postreply` WRITE;
/*!40000 ALTER TABLE `postreply` DISABLE KEYS */;
/*!40000 ALTER TABLE `postreply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `email` varchar(45) NOT NULL,
  `fullName` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `phone` int(11) DEFAULT NULL,
  `photo` blob,
  `bio` mediumtext,
  `numOfFollowers` int(11) DEFAULT NULL,
  `numOfFollowing` int(11) DEFAULT NULL,
  PRIMARY KEY (`email`,`fullName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin@admin.com','admin','admin','1994-08-17',123456789,'','android Developer',2,2),('george@george.com','georgeBisharat','1234','1994-08-17',987667890,'','android Developer',2,2),('morad@morad.com','moradSleman','1234','1994-08-17',987654321,'','android developer',2,2);
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

-- Dump completed on 2019-02-02 14:47:38
