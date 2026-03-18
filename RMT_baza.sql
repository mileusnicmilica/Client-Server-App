/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 10.4.32-MariaDB : Database - rmt_baza
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rmt_baza` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `rmt_baza`;

/*Table structure for table `putovanje` */

DROP TABLE IF EXISTS `putovanje`;

CREATE TABLE `putovanje` (
  `id_putovanje` int(11) NOT NULL AUTO_INCREMENT,
  `jmbg` varchar(13) NOT NULL,
  `ime` varchar(50) NOT NULL,
  `prezime` varchar(50) NOT NULL,
  `pasos` varchar(10) NOT NULL,
  `zemlja` varchar(50) NOT NULL,
  `datum_ulaska` date NOT NULL,
  `datum_izlaska` date NOT NULL,
  `tip_prevoza` enum('putnicki_automobil','motocikl','autobus','avio_prevoz') NOT NULL,
  PRIMARY KEY (`id_putovanje`),
  KEY `jmbg` (`jmbg`),
  CONSTRAINT `putovanje_ibfk_1` FOREIGN KEY (`jmbg`) REFERENCES `user` (`jmbg`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `putovanje` */

insert  into `putovanje`(`id_putovanje`,`jmbg`,`ime`,`prezime`,`pasos`,`zemlja`,`datum_ulaska`,`datum_izlaska`,`tip_prevoza`) values 
(31,'1302004715063','Milica','Mileusnić','123456789','Grčka','2025-06-15','2025-06-25','avio_prevoz'),
(52,'1302004715063','Milica','Mileusnić','123456789','Grcka','2025-06-15','2025-06-25','avio_prevoz'),
(53,'1302004715063','Milica','Mileusnić','123456789','Italija','2025-07-01','2025-07-10','putnicki_automobil'),
(54,'1206990123456','Milica','Jovanović','AB123456','Madjarska','2025-06-20','2025-06-30','motocikl'),
(55,'1206990123456','Milica','Jovanović','AB123456','Austrija','2025-07-05','2025-07-12','autobus'),
(58,'1503990333333','Jelena','Nikolić','CD789012','Turska','2025-06-25','2025-07-01','autobus'),
(59,'1503990333333','Jelena','Nikolić','CD789012','Grčka','2025-07-10','2025-07-20','avio_prevoz'),
(66,'2704990666666','Ana','Stanić','GH654321','Francuska','2025-06-28','2025-07-08','avio_prevoz'),
(67,'2704990666666','Ana','Stanić','GH654321','Austrija','2025-07-10','2025-07-17','autobus'),
(68,'0402000555555','Stefan','Marković','JK112233','Slovenija','2025-06-15','2025-06-22','putnicki_automobil'),
(69,'0402000555555','Stefan','Marković','JK112233','Hrvatska','2025-07-03','2025-07-09','avio_prevoz'),
(70,'1012980777777','Nikola','Popović','LM998877','Crna Gora','2025-06-20','2025-06-30','motocikl'),
(71,'1012980777777','Nikola','Popović','LM998877','Bosna i Hercegovina','2025-07-06','2025-07-11','putnicki_automobil'),
(73,'2507990888888','Tamara','Vasiljević','NO665544','Španija','2025-06-25','2025-07-04','avio_prevoz'),
(74,'2507990888888','Tamara','Vasiljević','NO665544','Italija','2025-07-07','2025-07-15','autobus'),
(75,'2507990888888','Tamara','Vasiljević','NO665544','Italija','2025-07-07','2025-07-15','autobus');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `ime` varchar(50) DEFAULT NULL,
  `prezime` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `jmbg` varchar(13) NOT NULL,
  `pasos` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `jmbg` (`jmbg`),
  UNIQUE KEY `pasos` (`pasos`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`ime`,`prezime`,`email`,`jmbg`,`pasos`) values 
(1,'milica','milica','Milica','Mileusnic','milica@gmail.com','1302004715063','123456789'),
(2,'m.jovanovic','lozinka123','Milica','Jovanović','milica.j@example.com','1206990123456','AB123456'),
(3,'p.petrovic','sifra321','Petar','Petrović','petar.p@example.com','0101000123456','XY987654'),
(4,'j.nikolic','tajna987','Jelena','Nikolić','jelena.n@example.com','1503990333333','CD789012'),
(5,'m.ivanov','pass2024','Marko','Ivanov','marko.i@example.com','0808000456789','EF456789'),
(6,'a.stanic','123abc','Ana','Stanić','ana.s@example.com','2704990666666','GH654321'),
(7,'s.markovic','pass123','Stefan','Marković','stefan.m@example.com','0402000555555','JK112233'),
(8,'n.popovic','mojaSifra1','Nikola','Popović','nikola.p@example.com','1012980777777','LM998877'),
(9,'t.vasiljevic','pass456','Tamara','Vasiljević','tamara.v@example.com','2507990888888','NO665544'),
(10,'d.zivkovic','zmaj2023','Dejan','Živković','dejan.z@example.com','0601900999999','PQ443322'),
(11,'i.stevanovic','lozinka!','Ivana','Stevanović','ivana.s@example.com','1212910111111','RS223344'),
(12,'l.mitrovic','secureMe','Luka','Mitrović','luka.m@example.com','0903930222222','TU556677'),
(13,'s.djordjevic','pass789','Sara','Đorđević','sara.d@example.com','0303970333333','VW889900'),
(14,'d.ivanovic','abc987','Danilo','Ivanović','danilo.i@example.com','2005970444444','XX001122'),
(15,'m.ristic','loz123','Marija','Ristić','marija.r@example.com','1906990555555','YY334455'),
(16,'b.milosevic','mil2025','Bojan','Milošević','bojan.m@example.com','2308000666666','ZZ667788'),
(17,'k.vukovic','vuko321','Katarina','Vuković','katarina.v@example.com','3101000777777','AA778899'),
(18,'v.simic','vasa1234','Vasa','Simić','vasa.s@example.com','2204980888888','BB990011'),
(19,'n.todorovic','ninaT!','Nina','Todorović','nina.t@example.com','1507990999999','CC112255'),
(20,'m.radovic','rad123','Miloš','Radović','milos.r@example.com','0101010111111','DD334466'),
(21,'a.zdravkovic','zdravlje','Aleksandra','Zdravković','aleksandra.z@example.com','0402950123456','EE556677'),
(22,'milicamilica','987654321','Milica','Milica','milicamail@mail.com','1234567891234','987654321');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
