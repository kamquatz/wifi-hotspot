CREATE DATABASE hotspot;
USE hotspot;

CREATE USER 'dennis'@'localhost' IDENTIFIED BY 'Mmxsp65#';
GRANT ALL PRIVILEGES ON hotspot.* TO 'dennis'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(256) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
INSERT INTO `users` VALUES(null,'tharaka','tharaka@123',now(),now());
INSERT INTO `users` VALUES(null,'meru','tharaka@123',now(),now());
INSERT INTO `users` VALUES(null,'embu','embu@456',now(),now());
INSERT INTO `users` VALUES(null,'bungoma','bungoma@789',now(),now());

DROP TABLE IF EXISTS `sites`;
CREATE TABLE `sites` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL,
  `host` varchar(32) NOT NULL,
  `port` int DEFAULT '8728',
  `user` varchar(16) NOT NULL,
  `pass` varchar(64) NOT NULL,
  `profile` varchar(16) NOT NULL,
  `limit_uptime` varchar(16) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
INSERT INTO `sites` VALUES(null,'tharaka','197.156.141.34',8729,'admin','tharakanithi#1','default','0d 00:30:00',now(),now());
#INSERT INTO `sites` VALUES(null,'meru','197.156.141.34',8729,'admin','meru#1','default','0d 00:30:00',now(),now());
#INSERT INTO `sites` VALUES(null,'embu','197.156.141.34',8729,'admin','embu#1','default','0d 00:30:00',now(),now());
#INSERT INTO `sites` VALUES(null,'bungoma','197.156.141.34',8729,'admin','bungoma#1','default','0d 00:30:00',now(),now());

DROP TABLE IF EXISTS `tharaka`;
CREATE TABLE `tharaka` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(16) NOT NULL,
  `device` int DEFAULT '0',
  `connections` int DEFAULT '0',
  `expiry_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `meru`;
CREATE TABLE `meru` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(16) NOT NULL,
  `device` int DEFAULT '0',
  `connections` int DEFAULT '0',
  `expiry_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `embu`;
CREATE TABLE `embu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(16) NOT NULL,
  `device` int DEFAULT '0',
  `connections` int DEFAULT '0',
  `expiry_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `bungoma`;
CREATE TABLE `bungoma` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(16) NOT NULL,
  `device` int DEFAULT '0',
  `connections` int DEFAULT '0',
  `expiry_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
