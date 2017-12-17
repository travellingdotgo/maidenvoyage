

环境依赖


- - - - - - - - 建库 - - - - - - - - - - 
mysql> CREATE DATABASE db_maidenvoyage;
mysql> show databases;
# mysql> drop database db_maidenvoyage;

mysql>    use db_maidenvoyage;
mysql>    show tables;


CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
select * from user;


CREATE TABLE `query` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sourceip` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `pageurl` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
select * from query;



