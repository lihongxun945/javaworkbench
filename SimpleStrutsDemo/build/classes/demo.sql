-- MySQL dump 10.13  Distrib 5.5.15, for osx10.7 (i386)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	5.5.15

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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `name` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(20) DEFAULT NULL,
  `gender` smallint(6) DEFAULT '1',
  `bio` text,
  `is_admin` smallint(6) DEFAULT '0',
  `email` varchar(30) DEFAULT NULL,
  `avatar_path` varchar(60) DEFAULT 'default.jpg',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('123','123',1,'',0,NULL,'default.jpg'),('2143532151','123',1,'123',0,'','default.jpg'),('4132412','123',1,'1',0,'','default.jpg'),('axun','123',1,'我是测试用户！',1,NULL,'default.jpg'),('emailuser','123',1,'4312411',0,'','default.jpg'),('emailuser1','123',1,'4312411',0,'xxx@163.com','default.jpg'),('google','123456',0,'i am google!',0,'google@gmail.com','2012-09-22-04-49-25-15951.tmp'),('img1','123',1,'touxiang ceshi',0,'123@123.com','2012-09-22-04-19-36-11428.jpg'),('test','123',1,'',0,NULL,'default.jpg'),('test1','123',1,'',0,NULL,'default.jpg'),('test2','123',1,'',0,NULL,'default.jpg'),('test3','123',1,'',0,NULL,'default.jpg'),('test4','1234',0,'fbuaibfiabgibg\r\n',0,NULL,'default.jpg');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-09-22 23:28:53
