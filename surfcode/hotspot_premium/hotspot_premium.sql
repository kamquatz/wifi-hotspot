CREATE DATABASE hotspot_premium;
USE hotspot_premium;

CREATE USER 'dennis'@'localhost' IDENTIFIED BY 'Mmxsp65#';
GRANT ALL PRIVILEGES ON hotspot_premium.* TO 'dennis'@'localhost' WITH GRANT OPTION;
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

INSERT INTO `users` VALUES(null,'dennis',SHA2('Mmxsp65#',256),now(),now());

DROP TABLE IF EXISTS `stations`;
CREATE TABLE `stations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `town` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `last_user` int DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `town_station` (`town`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

insert into stations values(null,'meru','makutano',1,NOW(),NOW());

DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(16) NOT NULL,
  `code` varchar(16) NOT NULL,
  `amount` int DEFAULT '0',
  `days` int DEFAULT '0',
  `expiry_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

#insert into customers values(null,'254723111920','KK',20,1,'2022-06-23 18:58:02',NOW(),NOW());

DROP TABLE IF EXISTS `logins`;
CREATE TABLE `logins` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT '0',
  `station_id` int DEFAULT '0',
  `account_id` int DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


