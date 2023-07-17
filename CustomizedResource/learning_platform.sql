/*
 Navicat Premium Data Transfer

 Source Server         : LearningPlatform后端数据库
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : 192.168.170.129:3306
 Source Schema         : learning_platform

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 17/07/2023 10:53:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for allocated_host
-- ----------------------------
DROP TABLE IF EXISTS `allocated_host`;
CREATE TABLE `allocated_host`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `image_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `镜像名外键`(`image_name`) USING BTREE,
  INDEX `镜像标签外键`(`tag`) USING BTREE,
  CONSTRAINT `镜像名外键` FOREIGN KEY (`image_name`) REFERENCES `image_info` (`image_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `镜像标签外键` FOREIGN KEY (`tag`) REFERENCES `image_info` (`tag`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of allocated_host
-- ----------------------------

-- ----------------------------
-- Table structure for course_info
-- ----------------------------
DROP TABLE IF EXISTS `course_info`;
CREATE TABLE `course_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course_des` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_deleted` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_info
-- ----------------------------
INSERT INTO `course_info` VALUES (1, '测试课程1', '测试课程1描述', NULL);

-- ----------------------------
-- Table structure for image_info
-- ----------------------------
DROP TABLE IF EXISTS `image_info`;
CREATE TABLE `image_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `image_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `port` int(0) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_deleted` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `image_name`(`image_name`) USING BTREE,
  INDEX `tag`(`tag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of image_info
-- ----------------------------
INSERT INTO `image_info` VALUES (1, 'vimclass', '1', 22, 'root', '123456', 0);
INSERT INTO `image_info` VALUES (2, 'clonebase', 'test', 22, 'root', '123456', 0);

-- ----------------------------
-- Table structure for online_user_info
-- ----------------------------
DROP TABLE IF EXISTS `online_user_info`;
CREATE TABLE `online_user_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `userid` int(0) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `expire_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `在线用户映射User表`(`userid`) USING BTREE,
  CONSTRAINT `在线用户映射User表` FOREIGN KEY (`userid`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of online_user_info
-- ----------------------------
INSERT INTO `online_user_info` VALUES (38, 9, '333', 'f520340a-eef8-43a4-9b2e-5a387e393875', '2023-07-13 18:20:22');
INSERT INTO `online_user_info` VALUES (46, 1, 'admin', '481aa268-4fca-4e13-81fb-31e5872788a1', '2023-07-14 11:25:13');

-- ----------------------------
-- Table structure for pdf_info
-- ----------------------------
DROP TABLE IF EXISTS `pdf_info`;
CREATE TABLE `pdf_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `pdf_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pdf_des` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pdf_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course_id` int(0) NULL DEFAULT NULL,
  `is_deleted` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `课程pdf映射`(`course_id`) USING BTREE,
  CONSTRAINT `课程pdf映射` FOREIGN KEY (`course_id`) REFERENCES `course_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pdf_info
-- ----------------------------
INSERT INTO `pdf_info` VALUES (1, '迪昂与民国时期中国学人的科学论_李醒民.pdf', '测试pdf1描述', 'D:\\\\Work\\\\Paper\\\\迪昂与民国时期中国学人的科学论_李醒民.pdf', 1, 0);
INSERT INTO `pdf_info` VALUES (3, 'navicat.pdf', '测试pdf2描述', 'D:\\\\Software\\\\Navicat\\\\navicat\\\\Navicat Premium 15\\\\navicat.pdf', 1, 0);

-- ----------------------------
-- Table structure for pdf_to_image_map
-- ----------------------------
DROP TABLE IF EXISTS `pdf_to_image_map`;
CREATE TABLE `pdf_to_image_map`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `pdf_id` int(0) NOT NULL,
  `image_id` int(0) NOT NULL,
  `is_deleted` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pdf映射外键`(`pdf_id`) USING BTREE,
  INDEX `image映射外键`(`image_id`) USING BTREE,
  CONSTRAINT `image映射外键` FOREIGN KEY (`image_id`) REFERENCES `image_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pdf映射外键` FOREIGN KEY (`pdf_id`) REFERENCES `pdf_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pdf_to_image_map
-- ----------------------------
INSERT INTO `pdf_to_image_map` VALUES (1, 3, 1, 0);
INSERT INTO `pdf_to_image_map` VALUES (2, 1, 2, 0);

-- ----------------------------
-- Table structure for route_info
-- ----------------------------
DROP TABLE IF EXISTS `route_info`;
CREATE TABLE `route_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `route_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `access_vector` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of route_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_deleted` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2023-07-05 17:57:49', '2023-07-13 10:44:30', '管理员', 0);
INSERT INTO `user_info` VALUES (4, '111', '111', '2023-07-13 10:45:28', '2023-07-13 18:02:52', '普通用户', 0);
INSERT INTO `user_info` VALUES (5, '222', '222', '2023-07-13 15:05:17', '2023-07-13 20:03:39', '普通用户', 0);
INSERT INTO `user_info` VALUES (9, '333', '556d7dc3a115356350f1f9910b1af1ab0e312d4b3e4fc788d2da63668f36d017', '2023-07-13 18:03:29', '2023-07-13 18:03:29', '普通用户', 0);

SET FOREIGN_KEY_CHECKS = 1;
