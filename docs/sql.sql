-- MySQL dump 10.13  Distrib 5.1.69, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: cacheplatform
-- ------------------------------------------------------
-- Server version	5.1.69
--
-- Table structure for table `cache_platform_machine`
--

DROP TABLE IF EXISTS `cache_platform_machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cache_platform_machine` (
  `ip` varchar(64) NOT NULL DEFAULT '',
  `usename` varchar(32) DEFAULT NULL,
  `passwd` varchar(32) DEFAULT NULL,
  `location` varchar(64) DEFAULT NULL,
  `memory` int(10) DEFAULT NULL,
  `coreSize` int(10) DEFAULT NULL,
  `isVM` varchar(4) DEFAULT NULL,
  `hostIp` varchar(64) DEFAULT NULL,
  `isMonitor` varchar(4) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `score` varchar(32) DEFAULT NULL,
  `updateTime` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `cache_platform_machine_monitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cache_platform_machine_monitor` (
  `ip` varchar(64) NOT NULL DEFAULT '',
  `osVersion` varchar(32) DEFAULT NULL,
  `cpuModel` varchar(32) DEFAULT NULL,
  `cpuInfo` varchar(64) DEFAULT NULL,
  `memory` varchar(64) DEFAULT NULL,
  `loadAverage` varchar(64) DEFAULT NULL,
  `swap` varchar(64) DEFAULT NULL,
  `network` varchar(64) DEFAULT NULL,
  `connectNum` int(10) DEFAULT NULL,
  `psNum` int(10) DEFAULT NULL,
  `treadNum` int(10) DEFAULT NULL,
  `logType` varchar(8) DEFAULT NULL,
  `topThree` varchar(256) DEFAULT NULL,
  `updateTime` varchar(64) NOT NULL DEFAULT '',
  PRIMARY KEY (`ip`,`updateTime`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `cache_platform_machine_network`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cache_platform_machine_network` (
  `ip` varchar(64) NOT NULL DEFAULT '',
  `rxpck` varchar(64) DEFAULT NULL,
  `txpck` varchar(64) DEFAULT NULL,
  `rxbyt` varchar(64) DEFAULT NULL,
  `txbyt` varchar(64) DEFAULT NULL,
  `rxcmp` varchar(64) DEFAULT NULL,
  `txcmp` varchar(64) DEFAULT NULL,
  `rxmcst` varchar(64) DEFAULT NULL,
  `updateTime` varchar(64) NOT NULL DEFAULT '',
  PRIMARY KEY (`ip`,`updateTime`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `cache_platform_sysconfig`;
CREATE TABLE `cache_platform_sysconfig` (
  `conf_key` varchar(64) NOT NULL DEFAULT '',
  `conf_value` varchar(64) DEFAULT NULL,
  `info` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`conf_key`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
-- machine rule table
--

DROP TABLE IF EXISTS `cache_platform_machine_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cache_platform_machine_rule` (
  `id` varchar(255) NOT NULL,
  `ip` varchar(64) NOT NULL,
  `limitName` varchar(25) NOT NULL,
  `formula` varchar(255) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `updateTime` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- machine warning table
--

DROP TABLE IF EXISTS `cache_platform_machine_warning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cache_platform_machine_warning` (
  `id` varchar(255) NOT NULL,
  `ip` varchar(64) NOT NULL,
  `limitName` varchar(25) NOT NULL,
  `formula` varchar(255) NOT NULL,
  `grade` int(2) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `data` varchar(2000) NOT NULL,
  `isChecked` int(4) NOT NULL,
  `updateTime` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;