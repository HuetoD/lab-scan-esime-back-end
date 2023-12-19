CREATE DATABASE  IF NOT EXISTS `labesimecu` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `labesimecu`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: labesimecu
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asignaturas`
--

DROP TABLE IF EXISTS `asignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asignaturas` (
  `asignatura_id` int NOT NULL AUTO_INCREMENT,
  `profesor_id` int NOT NULL,
  `grupo` int NOT NULL,
  `curso_id` int NOT NULL,
  `semestre_id` int NOT NULL,
  PRIMARY KEY (`asignatura_id`),
  UNIQUE KEY `group_name_UNIQUE` (`grupo`),
  UNIQUE KEY `curso_id_UNIQUE` (`asignatura_id`),
  KEY `profesor_fk_idx` (`profesor_id`),
  KEY `curso_fk_idx` (`curso_id`),
  KEY `semestre_fk_idx` (`semestre_id`),
  CONSTRAINT `curso_fk` FOREIGN KEY (`curso_id`) REFERENCES `cursos` (`curso_id`),
  CONSTRAINT `grupo_fk` FOREIGN KEY (`grupo`) REFERENCES `grupos` (`grupo_id`),
  CONSTRAINT `profesor_fk` FOREIGN KEY (`profesor_id`) REFERENCES `profesores` (`profesor_id`),
  CONSTRAINT `semestre_fk` FOREIGN KEY (`semestre_id`) REFERENCES `semestres` (`semestre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignaturas`
--

LOCK TABLES `asignaturas` WRITE;
/*!40000 ALTER TABLE `asignaturas` DISABLE KEYS */;
INSERT INTO `asignaturas` VALUES (2,1,1,1,24),(4,3,231,4,24),(6,4,234,5,24),(7,5,235,6,24),(8,1,237,1,24);
/*!40000 ALTER TABLE `asignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asignaturas_laboratorios`
--

DROP TABLE IF EXISTS `asignaturas_laboratorios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asignaturas_laboratorios` (
  `asignaturas_labs_id` int NOT NULL AUTO_INCREMENT,
  `asignatura_id` int NOT NULL,
  `lab_id` int NOT NULL,
  PRIMARY KEY (`asignaturas_labs_id`),
  UNIQUE KEY `asignaturas_labs_id_UNIQUE` (`asignaturas_labs_id`),
  KEY `lab_fk_idx` (`lab_id`),
  KEY `asign_fk` (`asignatura_id`),
  CONSTRAINT `asign_fk` FOREIGN KEY (`asignatura_id`) REFERENCES `asignaturas` (`asignatura_id`),
  CONSTRAINT `lab_fk` FOREIGN KEY (`lab_id`) REFERENCES `laboratorios` (`laboratorio_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignaturas_laboratorios`
--

LOCK TABLES `asignaturas_laboratorios` WRITE;
/*!40000 ALTER TABLE `asignaturas_laboratorios` DISABLE KEYS */;
INSERT INTO `asignaturas_laboratorios` VALUES (1,2,1),(2,2,2),(3,6,3),(4,7,1),(5,8,1);
/*!40000 ALTER TABLE `asignaturas_laboratorios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asistencias`
--

DROP TABLE IF EXISTS `asistencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asistencias` (
  `asistencia_id` int NOT NULL AUTO_INCREMENT,
  `estudiante_id` int NOT NULL,
  `asignatura_id` int NOT NULL,
  `fecha` datetime NOT NULL,
  `asistencia` tinyint NOT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `eliminado` datetime DEFAULT NULL,
  PRIMARY KEY (`asistencia_id`),
  KEY `estudiante_fk_idx` (`estudiante_id`),
  KEY `asignatura_fk_idx` (`asignatura_id`),
  CONSTRAINT `asignatura_fk` FOREIGN KEY (`asignatura_id`) REFERENCES `asignaturas` (`asignatura_id`),
  CONSTRAINT `estudiante_fk` FOREIGN KEY (`estudiante_id`) REFERENCES `estudiantes` (`estudiante_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asistencias`
--

LOCK TABLES `asistencias` WRITE;
/*!40000 ALTER TABLE `asistencias` DISABLE KEYS */;
/*!40000 ALTER TABLE `asistencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cursos`
--

DROP TABLE IF EXISTS `cursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cursos` (
  `curso_id` int NOT NULL AUTO_INCREMENT,
  `nombre_curso` varchar(255) NOT NULL,
  PRIMARY KEY (`curso_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cursos`
--

LOCK TABLES `cursos` WRITE;
/*!40000 ALTER TABLE `cursos` DISABLE KEYS */;
INSERT INTO `cursos` VALUES (1,'INGENIERA DE SOFTWARE'),(3,'METODOLOGIA DE LA INVESTIGACION'),(4,'ARQUITECTURA DE COMPUTADORAS'),(5,'TEORIA DE CONTROL ANALOGICO'),(6,'SISTEMAS OPERATIVOS');
/*!40000 ALTER TABLE `cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiantes`
--

DROP TABLE IF EXISTS `estudiantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiantes` (
  `estudiante_id` int NOT NULL AUTO_INCREMENT,
  `tipo_identificacion` int NOT NULL,
  `identificacion` varchar(64) NOT NULL,
  `nombre_completo` varchar(255) NOT NULL,
  `numero_pc` varchar(10) DEFAULT NULL,
  `codigo_qr` varchar(128) DEFAULT NULL,
  `creacion_sacadem` date DEFAULT NULL,
  `foto` mediumblob,
  PRIMARY KEY (`estudiante_id`),
  UNIQUE KEY `student_id_UNIQUE` (`identificacion`),
  UNIQUE KEY `estudiante_id_UNIQUE` (`estudiante_id`),
  KEY `tipo_identificacion_fk_idx` (`tipo_identificacion`),
  CONSTRAINT `tipo_identificacion_fk` FOREIGN KEY (`tipo_identificacion`) REFERENCES `tipo_identificaciones` (`identificacion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiantes`
--

LOCK TABLES `estudiantes` WRITE;
/*!40000 ALTER TABLE `estudiantes` DISABLE KEYS */;
INSERT INTO `estudiantes` VALUES (6,1,'2020351372','KEVIN WILLIAMS MIRANDA SANCHEZ','24','fbb613cb862c2cac2546e356b42943b3c9d53bdbefd9e4fc9deb62589f0c9',NULL,NULL),(13,1,'2020351457','BRYAN DANIEL PEREZ ALEGRE','11','515248ec6a4ac952444c9493b4e1fabd3e28be4789b8fab291523177ecec5',NULL,NULL),(15,1,'2020351573','ARTURO HERNANDEZ VARAS',NULL,'df4979e5c75efbb449f1345bc1e274ea26fb7ffb7030b2e6f51ae7143bef563',NULL,NULL);
/*!40000 ALTER TABLE `estudiantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiantes_asignaturas`
--

DROP TABLE IF EXISTS `estudiantes_asignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiantes_asignaturas` (
  `estudiante_asignatura_id` int NOT NULL AUTO_INCREMENT,
  `estudiante_id` int NOT NULL,
  `asignatura_id` int NOT NULL,
  PRIMARY KEY (`estudiante_asignatura_id`),
  UNIQUE KEY `estudiante_asignatura_id_UNIQUE` (`estudiante_asignatura_id`),
  KEY `estudiante_fk_idx` (`estudiante_id`),
  KEY `asignatura_fk_idx` (`asignatura_id`),
  CONSTRAINT `asignatura_ref_fk` FOREIGN KEY (`asignatura_id`) REFERENCES `asignaturas` (`asignatura_id`),
  CONSTRAINT `estudiante_ref_fk` FOREIGN KEY (`estudiante_id`) REFERENCES `estudiantes` (`estudiante_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiantes_asignaturas`
--

LOCK TABLES `estudiantes_asignaturas` WRITE;
/*!40000 ALTER TABLE `estudiantes_asignaturas` DISABLE KEYS */;
INSERT INTO `estudiantes_asignaturas` VALUES (20,6,6),(25,13,2),(26,13,7),(27,13,8),(28,13,2);
/*!40000 ALTER TABLE `estudiantes_asignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupos`
--

DROP TABLE IF EXISTS `grupos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grupos` (
  `grupo_id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(5) NOT NULL,
  `turno` char(1) NOT NULL,
  PRIMARY KEY (`grupo_id`),
  UNIQUE KEY `name_UNIQUE` (`nombre`),
  UNIQUE KEY `grupo_id_UNIQUE` (`grupo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=299 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupos`
--

LOCK TABLES `grupos` WRITE;
/*!40000 ALTER TABLE `grupos` DISABLE KEYS */;
INSERT INTO `grupos` VALUES (1,'6CV15','V'),(2,'1CM11','M'),(3,'1CM12','M'),(4,'1CM13','M'),(5,'1CM14','M'),(6,'1CM15','M'),(7,'1CM16','M'),(8,'1CM21','M'),(9,'1CM22','M'),(10,'1CM23','M'),(11,'1CM24','M'),(12,'1CM25','M'),(13,'1CM26','M'),(14,'1CM31','M'),(15,'1CM32','M'),(16,'1CM33','M'),(17,'1CM34','M'),(18,'1CM35','M'),(19,'1CM36','M'),(20,'1CM41','M'),(21,'1CM42','M'),(22,'1CM43','M'),(23,'1CM44','M'),(24,'1CM45','M'),(25,'1CM46','M'),(26,'1CM51','M'),(27,'1CM52','M'),(28,'1CM53','M'),(29,'1CM54','M'),(30,'1CM55','M'),(31,'1CM56','M'),(32,'1CV11','V'),(33,'1CV12','V'),(34,'1CV13','V'),(35,'1CV14','V'),(36,'1CV15','V'),(37,'1CV16','V'),(38,'1CV21','V'),(39,'1CV22','V'),(40,'1CV23','V'),(41,'1CV24','V'),(42,'1CV25','V'),(43,'1CV26','V'),(44,'1CV31','V'),(45,'1CV32','V'),(46,'1CV33','V'),(47,'1CV34','V'),(48,'1CV35','V'),(49,'1CV36','V'),(50,'1CV41','V'),(51,'1CV42','V'),(52,'1CV43','V'),(53,'1CV44','V'),(54,'1CV45','V'),(55,'1CV46','V'),(56,'1CV51','V'),(57,'1CV52','V'),(58,'1CV53','V'),(59,'1CV54','V'),(60,'1CV55','V'),(61,'1CV56','V'),(62,'1CX11','X'),(63,'1CX13','X'),(64,'1CX23','X'),(65,'2CM11','M'),(66,'2CM12','M'),(67,'2CM13','M'),(68,'2CM14','M'),(69,'2CM15','M'),(70,'2CM16','M'),(71,'2CM21','M'),(72,'2CM22','M'),(73,'2CM23','M'),(74,'2CM24','M'),(75,'2CM25','M'),(76,'2CM26','M'),(77,'2CM31','M'),(78,'2CM32','M'),(79,'2CM33','M'),(80,'2CM34','M'),(81,'2CM35','M'),(82,'2CM36','M'),(83,'2CV11','V'),(84,'2CV12','V'),(85,'2CV13','V'),(86,'2CV14','V'),(87,'2CV15','V'),(88,'2CV16','V'),(89,'2CV21','V'),(90,'2CV22','V'),(91,'2CV23','V'),(92,'2CV24','V'),(93,'2CV25','V'),(94,'2CV26','V'),(95,'2CV31','V'),(96,'2CV32','V'),(97,'2CV33','V'),(98,'2CV34','V'),(99,'2CV35','V'),(100,'2CV36','V'),(101,'3CM11','M'),(102,'3CM12','M'),(103,'3CM13','M'),(104,'3CM14','M'),(105,'3CM15','M'),(106,'3CM16','M'),(107,'3CM21','M'),(108,'3CM22','M'),(109,'3CM23','M'),(110,'3CM24','M'),(111,'3CM25','M'),(112,'3CM26','M'),(113,'3CM31','M'),(114,'3CM32','M'),(115,'3CM33','M'),(116,'3CM34','M'),(117,'3CM35','M'),(118,'3CM36','M'),(119,'3CM41','M'),(120,'3CM42','M'),(121,'3CM43','M'),(122,'3CM44','M'),(123,'3CM45','M'),(124,'3CM46','M'),(125,'3CV11','V'),(126,'3CV12','V'),(127,'3CV13','V'),(128,'3CV14','V'),(129,'3CV15','V'),(130,'3CV16','V'),(131,'3CV21','V'),(132,'3CV22','V'),(133,'3CV23','V'),(134,'3CV24','V'),(135,'3CV25','V'),(136,'3CV26','V'),(137,'3CV31','V'),(138,'3CV32','V'),(139,'3CV33','V'),(140,'3CV34','V'),(141,'3CV35','V'),(142,'3CV36','V'),(143,'3CV41','V'),(144,'3CV42','V'),(145,'3CV43','V'),(146,'3CV44','V'),(147,'3CV45','V'),(148,'3CV46','V'),(149,'4CM11','M'),(150,'4CM12','M'),(151,'4CM13','M'),(152,'4CM14','M'),(153,'4CM15','M'),(154,'4CM16','M'),(155,'4CM21','M'),(156,'4CM22','M'),(157,'4CM23','M'),(158,'4CM24','M'),(159,'4CM25','M'),(160,'4CM26','M'),(161,'4CM31','M'),(162,'4CM32','M'),(163,'4CM33','M'),(164,'4CM34','M'),(165,'4CM35','M'),(166,'4CM36','M'),(167,'4CV11','V'),(168,'4CV12','V'),(169,'4CV13','V'),(170,'4CV14','V'),(171,'4CV15','V'),(172,'4CV16','V'),(173,'4CV21','V'),(174,'4CV22','V'),(175,'4CV23','V'),(176,'4CV24','V'),(177,'4CV25','V'),(178,'4CV26','V'),(179,'4CV31','V'),(180,'4CV32','V'),(181,'4CV33','V'),(182,'4CV34','V'),(183,'4CV35','V'),(184,'4CV36','V'),(185,'4CX14','X'),(186,'5CM11','M'),(187,'5CM12','M'),(188,'5CM13','M'),(189,'5CM14','M'),(190,'5CM15','M'),(191,'5CM16','M'),(192,'5CM21','M'),(193,'5CM22','M'),(194,'5CM23','M'),(195,'5CM24','M'),(196,'5CM25','M'),(197,'5CM26','M'),(198,'5CM31','M'),(199,'5CM32','M'),(200,'5CM33','M'),(201,'5CM34','M'),(202,'5CM35','M'),(203,'5CM36','M'),(204,'5CV11','V'),(205,'5CV12','V'),(206,'5CV13','V'),(207,'5CV14','V'),(208,'5CV15','V'),(209,'5CV16','V'),(210,'5CV21','V'),(211,'5CV22','V'),(212,'5CV23','V'),(213,'5CV24','V'),(214,'5CV25','V'),(215,'5CV26','V'),(216,'6CM11','M'),(217,'6CM12','M'),(218,'6CM13','M'),(219,'6CM14','M'),(220,'6CM15','M'),(221,'6CM16','M'),(222,'6CM21','M'),(223,'6CM22','M'),(224,'6CM23','M'),(225,'6CM24','M'),(226,'6CM25','M'),(227,'6CM26','M'),(228,'6CV11','V'),(229,'6CV12','V'),(230,'6CV13','V'),(231,'6CV14','V'),(232,'6CV16','V'),(233,'6CV21','V'),(234,'6CV22','V'),(235,'6CV23','V'),(236,'6CV24','V'),(237,'6CV25','V'),(238,'6CV26','V'),(239,'7CM11','M'),(240,'7CM12','M'),(241,'7CM13','M'),(242,'7CM14','M'),(243,'7CM15','M'),(244,'7CM21','M'),(245,'7CM22','M'),(246,'7CM23','M'),(247,'7CM24','M'),(248,'7CM25','M'),(249,'7CM31','M'),(250,'7CM32','M'),(251,'7CM33','M'),(252,'7CM34','M'),(253,'7CV11','V'),(254,'7CV12','V'),(255,'7CV13','V'),(256,'7CV14','V'),(257,'7CV15','V'),(258,'7CV21','V'),(259,'7CV22','V'),(260,'7CV23','V'),(261,'7CV24','V'),(262,'7CV25','V'),(263,'7CV31','V'),(264,'7CV32','V'),(265,'7CV33','V'),(266,'7CV34','V'),(267,'8CM11','M'),(268,'8CM12','M'),(269,'8CM13','M'),(270,'8CM14','M'),(271,'8CM15','M'),(272,'8CM21','M'),(273,'8CM22','M'),(274,'8CM23','M'),(275,'8CM24','M'),(276,'8CM25','M'),(277,'8CM31','M'),(278,'8CM32','M'),(279,'8CM33','M'),(280,'8CM34','M'),(281,'8CM41','M'),(282,'8CM42','M'),(283,'8CM46','M'),(284,'8CV11','V'),(285,'8CV12','V'),(286,'8CV13','V'),(287,'8CV14','V'),(288,'8CV15','V'),(289,'8CV21','V'),(290,'8CV22','V'),(291,'8CV23','V'),(292,'8CV24','V'),(293,'8CV25','V'),(294,'8CV31','V'),(295,'8CV32','V'),(296,'8CV33','V'),(297,'8CV41','V'),(298,'8CV42','V');
/*!40000 ALTER TABLE `grupos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horarios`
--

DROP TABLE IF EXISTS `horarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horarios` (
  `horario_id` int NOT NULL AUTO_INCREMENT,
  `asignatura_id` int NOT NULL,
  `dia` enum('1','2','3','4','5') NOT NULL,
  PRIMARY KEY (`horario_id`),
  UNIQUE KEY `horario_id_UNIQUE` (`horario_id`),
  KEY `asginatura_horario_fk_idx` (`asignatura_id`),
  CONSTRAINT `asginatura_horario_fk` FOREIGN KEY (`asignatura_id`) REFERENCES `asignaturas` (`asignatura_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horarios`
--

LOCK TABLES `horarios` WRITE;
/*!40000 ALTER TABLE `horarios` DISABLE KEYS */;
INSERT INTO `horarios` VALUES (1,2,'2'),(2,2,'3'),(3,2,'5'),(7,4,'2'),(8,4,'4'),(9,4,'5'),(10,6,'2'),(11,6,'3'),(12,6,'4'),(13,6,'5'),(14,7,'1'),(15,7,'2'),(16,7,'4'),(17,7,'5'),(18,8,'2'),(19,8,'3'),(20,8,'5');
/*!40000 ALTER TABLE `horarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laboratorios`
--

DROP TABLE IF EXISTS `laboratorios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `laboratorios` (
  `laboratorio_id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`laboratorio_id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laboratorios`
--

LOCK TABLES `laboratorios` WRITE;
/*!40000 ALTER TABLE `laboratorios` DISABLE KEYS */;
INSERT INTO `laboratorios` VALUES (1,'Lab. Computación 1'),(2,'Lab. Computación 2'),(3,'Lab. Micros');
/*!40000 ALTER TABLE `laboratorios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesores`
--

DROP TABLE IF EXISTS `profesores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesores` (
  `profesor_id` int NOT NULL AUTO_INCREMENT,
  `nombre_completo` varchar(255) NOT NULL,
  PRIMARY KEY (`profesor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesores`
--

LOCK TABLES `profesores` WRITE;
/*!40000 ALTER TABLE `profesores` DISABLE KEYS */;
INSERT INTO `profesores` VALUES (1,'Martinez Corona Eduardo'),(2,'Rodríguez Acosta Adolfo'),(3,'Bautista Arias Jose Luis'),(4,'Cruz Gonzalez Hayari Lizet'),(5,'De La Cruz Tellez Arturo');
/*!40000 ALTER TABLE `profesores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(128) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_id_UNIQUE` (`role_id`),
  UNIQUE KEY `role_name_UNIQUE` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'SUPERUSER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_usuarios`
--

DROP TABLE IF EXISTS `roles_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_usuarios` (
  `rol_usuario_id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `rol_id` int NOT NULL,
  PRIMARY KEY (`rol_usuario_id`),
  UNIQUE KEY `rol_usuario_id_UNIQUE` (`rol_usuario_id`),
  KEY `rol_fk_idx` (`rol_id`),
  KEY `usuario_fk_idx` (`usuario_id`),
  CONSTRAINT `rol_fk` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `usuario_fk` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`usuario_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_usuarios`
--

LOCK TABLES `roles_usuarios` WRITE;
/*!40000 ALTER TABLE `roles_usuarios` DISABLE KEYS */;
INSERT INTO `roles_usuarios` VALUES (12,14,1),(13,15,1),(15,17,2);
/*!40000 ALTER TABLE `roles_usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `semestres`
--

DROP TABLE IF EXISTS `semestres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `semestres` (
  `semestre_id` int NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL,
  PRIMARY KEY (`semestre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semestres`
--

LOCK TABLES `semestres` WRITE;
/*!40000 ALTER TABLE `semestres` DISABLE KEYS */;
INSERT INTO `semestres` VALUES (24,'2023-08-28','2024-01-22');
/*!40000 ALTER TABLE `semestres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_identificaciones`
--

DROP TABLE IF EXISTS `tipo_identificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_identificaciones` (
  `identificacion_id` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(128) NOT NULL,
  PRIMARY KEY (`identificacion_id`),
  UNIQUE KEY `tipo_UNIQUE` (`tipo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_identificaciones`
--

LOCK TABLES `tipo_identificaciones` WRITE;
/*!40000 ALTER TABLE `tipo_identificaciones` DISABLE KEYS */;
INSERT INTO `tipo_identificaciones` VALUES (1,'CREDENCIAL ESTUDIANTE');
/*!40000 ALTER TABLE `tipo_identificaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `usuario_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(128) NOT NULL,
  PRIMARY KEY (`usuario_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `usuario_id_UNIQUE` (`usuario_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (14,'remove3ZKiH@example.com','zRpqQ3Oj'),(15,'admin@admin.com','$2a$10$dO3dblr3c.Nfpd0O4j/Xtu27oYv435NJhHfP4Sh9snnOxVzGEyy5O'),(17,'super@super.com','$2a$10$dO3dblr3c.Nfpd0O4j/Xtu27oYv435NJhHfP4Sh9snnOxVzGEyy5O');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-19 16:21:22
