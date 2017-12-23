
# 构建
mvn clean; mvn package -DskipTests && mvn spring-boot:run


环境依赖


- - - - - - - - 建库 - - - - - - - - - - 
mysql> CREATE DATABASE db_maidenvoyage;
mysql> show databases;
# mysql> drop database db_maidenvoyage;
# drop table query;

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


CREATE TABLE `queryv2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sourceip` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `pageurl` varchar(45) DEFAULT NULL,
  `loc` varchar(200) DEFAULT NULL,
  `useragent` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
select * from queryv2;



CREATE TABLE `queryv3` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sourceip` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `pageurl` varchar(45) DEFAULT NULL,
  `loc` varchar(200) DEFAULT NULL,
  `useragent` varchar(200) DEFAULT NULL,
  `host` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
select * from queryv3;




DROP TABLE IF EXISTS  `city`;
CREATE TABLE `city` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '城市编号',
  `province_id` int(10) unsigned  NOT NULL COMMENT '省份编号',
  `city_name` varchar(25) DEFAULT NULL COMMENT '城市名称',
  `description` varchar(25) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


INSERT city VALUES (1 ,1,'温岭市','BYSocket 的家在温岭。');
