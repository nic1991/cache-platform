-- MySQL dump 10.13  Distrib 5.1.69, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: serverbase
-- ------------------------------------------------------
-- Server version	5.1.69

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
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` varchar(64) NOT NULL,
  `name` varchar(200) NOT NULL,
  `url` varchar(400) NOT NULL,
  `sequence` int(11) NOT NULL,
  `parentid` varchar(64) NOT NULL,
  `issysmenu` varchar(10) NOT NULL,
  `icon` varchar(200) DEFAULT NULL,
  `description` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('1','system','/rest/pages/menuManage',1000,'0','true','glyphicon glyphicon-cog','system menu'),('2','menuManage','/rest/pages/menuManage',1,'1','true','glyphicon glyphicon-th-list','system menu'),('3','urlManage','/rest/pages/urlManage',2,'1','true','glyphicon glyphicon-send','system menu'),('4','roleManage','/rest/pages/roleManage',3,'1','true','glyphicon glyphicon-book','system menu'),('5','userManage','/rest/pages/userManage',5,'1','true','glyphicon glyphicon-user','system menu'),('ebacd0dd94654c14a01a67d23eb9b816','apps','/rest/pages/apps',10,'0','false','glyphicon glyphicon-dashboard','dashboard'),('02923ac374e942e18696f0f90cf15b94','eureka','/',0,'0','false','glyphicon glyphicon-dashboard','dashboard');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` varchar(64) NOT NULL,
  `rolename` varchar(100) NOT NULL,
  `description` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('00565d22970a4a3d9d5557a15f9a723c','app role','app role');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menu_rt`
--

DROP TABLE IF EXISTS `role_menu_rt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_menu_rt` (
  `roleid` varchar(64) NOT NULL,
  `menuid` varchar(64) NOT NULL,
  PRIMARY KEY (`roleid`,`menuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu_rt`
--

LOCK TABLES `role_menu_rt` WRITE;
/*!40000 ALTER TABLE `role_menu_rt` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_menu_rt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_url_rt`
--

DROP TABLE IF EXISTS `role_url_rt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_url_rt` (
  `roleid` varchar(64) NOT NULL,
  `urlid` varchar(64) NOT NULL,
  PRIMARY KEY (`roleid`,`urlid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_url_rt`
--

LOCK TABLES `role_url_rt` WRITE;
/*!40000 ALTER TABLE `role_url_rt` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_url_rt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `url_table`
--

DROP TABLE IF EXISTS `url_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `url_table` (
  `id` varchar(64) NOT NULL,
  `url` varchar(400) NOT NULL,
  `description` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `url_table`
--

LOCK TABLES `url_table` WRITE;
/*!40000 ALTER TABLE `url_table` DISABLE KEYS */;
INSERT INTO `url_table` VALUES ('254fb96cc0734191a3fb8628e4384d7e','/rest/menu/addMenu','add menu'),('36669be446254e39b7e8a9995c831486','/rest/menu/updateMenu','update menu'),('8410acd97b734a5280e06d392f97218e','/rest/menu/deleteMenu','delete menu'),('e5c27af062444ab1be037504370ecadc','/rest/role/saveRole','save role'),('a395b3c731694274bb94cb5c25533aea','/rest/role/deleteRole','delete role'),('ce304158e8d041138e6be473cd9eddbe','/rest/url/saveUrl','save url'),('99d2c6cdfd3448fcad20dba7502d3bd4','/rest/url/deleteUrl','delete url'),('60fbd2aefcee487f857b387f826defb7','/rest/user/updateUserPassword','update user(profile)'),('0014056a5f214c1b8df5615e3f5b11af','/rest/user/saveUser','save user'),('fc49b4768f7745119c5955827f988ad1','/rest/user/deleteUser','delete user');
/*!40000 ALTER TABLE `url_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(64) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1','root','123456'),('81dc4bca35f3459cb5a9149bc0fb82d2','jn50','000000');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_rt`
--

DROP TABLE IF EXISTS `user_role_rt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role_rt` (
  `userid` varchar(64) NOT NULL,
  `roleid` varchar(64) NOT NULL,
  PRIMARY KEY (`userid`,`roleid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_rt`
--

LOCK TABLES `user_role_rt` WRITE;
/*!40000 ALTER TABLE `user_role_rt` DISABLE KEYS */;
INSERT INTO `user_role_rt` VALUES ('81dc4bca35f3459cb5a9149bc0fb82d2','00565d22970a4a3d9d5557a15f9a723c');
/*!40000 ALTER TABLE `user_role_rt` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-06 13:20:56
