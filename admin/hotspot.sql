CREATE DATABASE hotspot;
USE hotspot;

CREATE USER 'dennis'@'localhost' IDENTIFIED BY 'Mmxsp65#';
GRANT ALL PRIVILEGES ON hotspot.* TO 'dennis'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;

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


CREATE USER 'steve'@'localhost' IDENTIFIED BY 'Mvprx8@#';
GRANT ALL PRIVILEGES ON laravel_blog.* TO 'steve'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;

