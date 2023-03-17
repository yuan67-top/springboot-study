/*
SQLyog Ultimate v12.3.1 (64 bit)
MySQL - 8.0.24 : Database - spring-security
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`spring-security` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `spring-security`;

/*Table structure for table `base_admin` */

DROP TABLE IF EXISTS `base_admin`;

CREATE TABLE `base_admin` (
  `id` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` varchar(12) COLLATE utf8mb4_general_ci DEFAULT 'admin',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `base_admin` */

insert  into `base_admin`(`id`,`username`,`password`,`name`,`type`,`create_time`) values 
('1','1','$2a$10$tIzYl5VlcUrZnx4HKV8J0uOT7fDfN3PdN7m4qFu40.XmWBmc38LFa','admin-1','admin','2023-03-17 12:29:01'),
('2','2','$2a$10$tIzYl5VlcUrZnx4HKV8J0uOT7fDfN3PdN7m4qFu40.XmWBmc38LFa','admin-2','admin','2023-03-17 14:12:31');

/*Table structure for table `base_user` */

DROP TABLE IF EXISTS `base_user`;

CREATE TABLE `base_user` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` varchar(12) COLLATE utf8mb4_general_ci DEFAULT 'user',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `base_user` */

insert  into `base_user`(`id`,`username`,`password`,`name`,`type`,`create_time`) values 
('3','3','$2a$10$tIzYl5VlcUrZnx4HKV8J0uOT7fDfN3PdN7m4qFu40.XmWBmc38LFa','user-3','user','2023-03-17 12:29:11'),
('4','4','$2a$10$tIzYl5VlcUrZnx4HKV8J0uOT7fDfN3PdN7m4qFu40.XmWBmc38LFa','user-4','user','2023-03-17 14:11:57');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
