/*
 Navicat Premium Data Transfer

 Source Server         : USER
 Source Server Type    : MySQL
 Source Server Version : 50540
 Source Host           : localhost:3306
 Source Schema         : supermarket

 Target Server Type    : MySQL
 Target Server Version : 50540
 File Encoding         : 65001

 Date: 19/08/2021 09:11:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clock_info
-- ----------------------------
DROP TABLE IF EXISTS `clock_info`;
CREATE TABLE `clock_info`  (
  `clock_id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clock_in_time` datetime NULL DEFAULT NULL,
  `clock_off_time` datetime NULL DEFAULT NULL,
  `clock_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`clock_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of clock_info
-- ----------------------------
INSERT INTO `clock_info` VALUES (1, 'S0002', '2018-08-01 11:30:52', '2021-08-12 07:14:28', '2018-08-01');
INSERT INTO `clock_info` VALUES (2, 's0004', '2018-08-02 09:46:52', '2021-08-12 09:00:00', '2018-08-02');
INSERT INTO `clock_info` VALUES (3, 's0005', '2018-08-03 08:46:52', '2021-08-12 07:00:12', '2018-08-03');
INSERT INTO `clock_info` VALUES (4, 's0003', '2018-08-04 09:13:52', '2018-08-04 18:30:30', '2018-08-04');
INSERT INTO `clock_info` VALUES (5, 's0004', '2018-08-05 09:46:52', '2018-08-04 09:44:00', '2018-08-05');
INSERT INTO `clock_info` VALUES (6, 's0005', NULL, '2021-08-12 17:00:12', '2018-08-06');
INSERT INTO `clock_info` VALUES (7, 'S0005', '2018-08-01 08:46:52', '2021-08-12 17:00:12', '2018-08-01');
INSERT INTO `clock_info` VALUES (8, 'S0002', '2018-08-02 09:46:52', '2021-08-12 17:14:28', '2018-08-02');
INSERT INTO `clock_info` VALUES (9, 'S0006', '2018-08-03 08:46:52', '2018-08-03 17:30:30', '2018-08-03');
INSERT INTO `clock_info` VALUES (10, 'S0003', '2018-08-01 09:46:52', '2018-08-01 18:30:30', '2018-08-01');
INSERT INTO `clock_info` VALUES (11, 'S0004', '2018-08-02 09:46:52', NULL, '2018-08-02');
INSERT INTO `clock_info` VALUES (12, 'S0003', '2018-08-03 08:46:52', '2018-08-03 17:30:30', '2018-08-03');
INSERT INTO `clock_info` VALUES (13, 'S0004', '2018-08-04 09:13:52', '2018-08-04 09:44:00', '2018-08-04');
INSERT INTO `clock_info` VALUES (15, 'S0006', '2018-08-06 09:00:00', '2018-08-06 18:30:30', '2018-08-06');
INSERT INTO `clock_info` VALUES (25, 'S0001', '2021-08-12 07:03:07', '2021-08-18 14:11:40', '2021-08-12');
INSERT INTO `clock_info` VALUES (26, 'S0005', NULL, '2021-08-12 07:03:25', '2021-08-12');
INSERT INTO `clock_info` VALUES (27, 'S0004', '2021-08-12 09:00:00', '2021-08-12 09:00:00', '2021-08-12');
INSERT INTO `clock_info` VALUES (28, 'S0002', '2021-08-12 09:00:00', '2021-08-12 07:14:28', '2021-08-12');
INSERT INTO `clock_info` VALUES (29, 'S0007', NULL, '2021-08-12 11:11:54', '2021-08-12');
INSERT INTO `clock_info` VALUES (31, 'S0001', '2021-08-13 08:49:53', '2021-08-18 14:11:40', '2021-08-13');
INSERT INTO `clock_info` VALUES (32, 'S0007', '2021-08-13 10:11:37', '2021-08-13 18:00:04', '2021-08-13');
INSERT INTO `clock_info` VALUES (36, 'S0001', '2021-08-16 20:13:15', '2021-08-18 14:11:40', '2021-08-16');
INSERT INTO `clock_info` VALUES (37, 'S0009', NULL, '2021-08-17 11:07:23', '2021-08-16');
INSERT INTO `clock_info` VALUES (38, 'S0001', '2021-08-17 09:04:27', '2021-08-18 14:11:40', '2021-08-17');
INSERT INTO `clock_info` VALUES (39, 'S0009', '2021-08-17 09:05:49', '2021-08-17 11:07:23', '2021-08-17');
INSERT INTO `clock_info` VALUES (40, 'S0006', '2021-08-17 12:47:10', '2021-08-17 18:00:00', '2021-08-17');
INSERT INTO `clock_info` VALUES (41, 'S0003', '2021-08-18 09:21:23', NULL, '2021-08-18');
INSERT INTO `clock_info` VALUES (43, 'S0001', '2021-08-18 14:11:36', '2021-08-18 14:11:40', '2021-08-18');
INSERT INTO `clock_info` VALUES (44, 'S0004', '2021-08-18 14:13:03', NULL, '2021-08-18');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` int(11) NOT NULL,
  `remark` int(1) NULL DEFAULT NULL,
  `register_time` datetime NOT NULL,
  `re_clock_count` int(11) NULL DEFAULT NULL,
  `salary` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`number`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE,
  UNIQUE INDEX `UQ_phone`(`phone`) USING BTREE,
  INDEX `role_id_FK`(`role`) USING BTREE,
  CONSTRAINT `role_id_FK` FOREIGN KEY (`role`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('S0000', '王惹', 's123', '男', '13553799956', 2, 1, '2017-08-16 11:27:34', 5, 5232);
INSERT INTO `employee` VALUES ('S0001', '洪七公', 's123', '男', '13569602314', 1, 1, '2017-08-16 11:27:44', 1, 20000);
INSERT INTO `employee` VALUES ('S0002', '杨过', 's123', '男', '15919764435', 2, 1, '2017-08-16 11:27:44', 4, 2332);
INSERT INTO `employee` VALUES ('S0003', '黄黄溶', 's123', '女', '15949754435', 2, 1, '2017-08-16 11:27:44', 5, 4323);
INSERT INTO `employee` VALUES ('S0004', '王五', 's456', '女', '13553732995', 2, 1, '2017-08-16 11:27:44', 4, 2435);
INSERT INTO `employee` VALUES ('S0005', '郭靖', 's123', '男', '15919754485', 1, 1, '2017-08-16 11:27:44', 0, 5778);
INSERT INTO `employee` VALUES ('S0006', '国平', 's123', '女', '15919759485', 3, 1, '2017-08-16 11:27:44', 3, 2457);
INSERT INTO `employee` VALUES ('S0007', '黄新协', 's123', '女', '15919754425', 3, 1, '2017-08-16 11:27:44', 4, 1236);
INSERT INTO `employee` VALUES ('S0009', '东阿', 's123', '男', '13553799904', 2, 1, '2017-08-16 11:27:44', 5, 7844);
INSERT INTO `employee` VALUES ('S0010', '乔峰', 's123', '男', '15919754415', 2, 1, '2017-08-16 11:27:44', 5, 8556);
INSERT INTO `employee` VALUES ('S0011', '扫地僧', 's123', '男', '15919751415', 1, 1, '2017-08-16 11:27:44', 5, 9806);
INSERT INTO `employee` VALUES ('S0013', '张三', 's123', '男', '12315125513', 2, 1, '2017-08-16 11:27:44', 5, 2357);
INSERT INTO `employee` VALUES ('S0014', '王五', 's123', '男', '21411252323', 2, 1, '2017-08-16 11:27:44', 5, 6785);
INSERT INTO `employee` VALUES ('S0016', '扫天僧', 's123', '男', '13553792959', 2, 1, '2017-08-16 11:27:44', 5, 8543);
INSERT INTO `employee` VALUES ('S0017', '章一', 's123', '男', '11353799959', 2, 1, '2017-08-16 11:27:44', 5, 3657);
INSERT INTO `employee` VALUES ('S0018', '章三', 's123', '男', '13553794219', 2, 0, '2017-08-16 11:27:44', 5, 6784);
INSERT INTO `employee` VALUES ('S0019', '王浩', 's123', '女', '13553799923', 3, 1, '2017-08-16 11:27:44', 5, 34567);
INSERT INTO `employee` VALUES ('S0020', '王五', 's123', '男', '13553793952', 3, 0, '2017-08-16 11:27:44', 5, 3446);
INSERT INTO `employee` VALUES ('S0021', '温润', 's123', '男', '13553799950', 2, 1, '2017-08-16 11:27:44', 5, 6790);
INSERT INTO `employee` VALUES ('S0022', '王浩', 's123', '男', '13543799950', 2, 0, '2017-08-16 11:27:39', 5, 19000);
INSERT INTO `employee` VALUES ('S0023', '克鲁斯', 's123', '男', '13553799952', 2, 0, '2017-08-16 11:27:42', 5, 23456);
INSERT INTO `employee` VALUES ('S0024', '王五', 's123', '男', '13553799959', 2, 1, '2017-08-16 11:27:44', 5, 1334);
INSERT INTO `employee` VALUES ('S0025', '德豪', 's123', '男', '13553799951', 3, 0, '2017-08-16 11:27:44', 5, 5678);
INSERT INTO `employee` VALUES ('S0031', '旺盛', 's123', '男', '13553799021', 2, 1, '2021-08-17 12:43:37', 5, 2000);
INSERT INTO `employee` VALUES ('S0032', '王郝', 's123', '男', '13553799920', 2, 1, '2021-08-16 11:31:07', 5, 4567);
INSERT INTO `employee` VALUES ('S005', 'jhgdaj', 'ad123A', '女', '15256778859', 2, 0, '2021-08-17 20:05:27', 5, 0);
INSERT INTO `employee` VALUES ('S0222', '微信', 's123', '男', '13553799945', 2, 1, '2021-08-18 14:19:07', 5, 2999);
INSERT INTO `employee` VALUES ('S1003', '李四', 's123', '女', '13553799231', 2, 0, '2021-08-16 22:22:11', NULL, 1000);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `c_number` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_price` decimal(10, 2) NULL DEFAULT NULL,
  `vip_price` decimal(10, 2) NULL DEFAULT NULL,
  `inventory` int(11) NULL DEFAULT NULL,
  `remark` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`c_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '洗衣粉', 10.50, 8.40, 106, 1);
INSERT INTO `goods` VALUES (2, '洗衣机', 1200.00, 960.00, 170, 1);
INSERT INTO `goods` VALUES (3, '电视机', 5000.00, 4000.00, 1000, 1);
INSERT INTO `goods` VALUES (4, '空调', 3400.00, 2720.00, 55, 1);
INSERT INTO `goods` VALUES (5, '电磁炉', 120.00, 96.00, 95, 1);
INSERT INTO `goods` VALUES (6, '牙膏', 12.00, 9.60, 5000, 1);
INSERT INTO `goods` VALUES (7, '洗面奶', 10.00, 8.00, 30, 1);
INSERT INTO `goods` VALUES (8, '香皂', 4.00, 3.20, 802, 1);
INSERT INTO `goods` VALUES (9, '奶粉', 100.00, 80.00, 5000, 1);
INSERT INTO `goods` VALUES (10, '苹果', 10.00, 8.00, 400, 1);
INSERT INTO `goods` VALUES (11, '大米', 3.00, 2.40, 60000, 1);
INSERT INTO `goods` VALUES (12, '烂白菜', 12.00, 9.60, 5000, 0);
INSERT INTO `goods` VALUES (13, '口香糖', 11.00, 8.80, 1008, 1);
INSERT INTO `goods` VALUES (14, '大白兔', 10.00, 8.00, 4500, 1);
INSERT INTO `goods` VALUES (15, '味精', 1.00, 0.80, 50000, 1);
INSERT INTO `goods` VALUES (16, '拖鞋', 18.00, 14.40, 23, 0);
INSERT INTO `goods` VALUES (17, '裤子', 34.50, 27.60, 12, 1);
INSERT INTO `goods` VALUES (18, '笔记本电脑', 20000.00, 16000.00, 100, 1);
INSERT INTO `goods` VALUES (19, '水杯', 20.00, 16.00, 100, 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL,
  `r_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员');
INSERT INTO `role` VALUES (2, '收银员');
INSERT INTO `role` VALUES (3, '采购员');

-- ----------------------------
-- Table structure for score_info
-- ----------------------------
DROP TABLE IF EXISTS `score_info`;
CREATE TABLE `score_info`  (
  `s_vip_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_time` datetime NULL DEFAULT NULL,
  `s_quantity` int(11) NULL DEFAULT NULL,
  `s_g_number` int(11) NULL DEFAULT NULL,
  INDEX `FK_g_number`(`s_g_number`) USING BTREE,
  CONSTRAINT `FK_g_number` FOREIGN KEY (`s_g_number`) REFERENCES `goods` (`c_number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of score_info
-- ----------------------------
INSERT INTO `score_info` VALUES ('vip201901020001', '2021-08-13 04:47:48', 1, 1);
INSERT INTO `score_info` VALUES ('vip201901020001', '2021-08-13 04:48:32', 1, 1);
INSERT INTO `score_info` VALUES ('vip201901020001', '2021-08-13 04:48:54', 1, 1);
INSERT INTO `score_info` VALUES ('vip201901020001', '2021-08-13 04:49:44', 1, 1);
INSERT INTO `score_info` VALUES ('vip201901020001', '2021-08-13 04:50:06', 10, 1);
INSERT INTO `score_info` VALUES ('vip201901020001', '2021-08-13 04:51:40', 1, 1);
INSERT INTO `score_info` VALUES ('vip201901020001', '2021-08-13 04:52:23', 3, 1);
INSERT INTO `score_info` VALUES ('vip201901020001', '2021-08-13 04:52:26', 3, 1);
INSERT INTO `score_info` VALUES ('vip202108120002', '2021-08-17 09:13:24', 1, 1);
INSERT INTO `score_info` VALUES ('vip202108170001', '2021-08-18 09:58:24', 1, 1);
INSERT INTO `score_info` VALUES ('vip202108170001', '2021-08-18 09:58:32', 1, 1);
INSERT INTO `score_info` VALUES ('vip202108170001', '2021-08-18 11:13:55', -1, 1);
INSERT INTO `score_info` VALUES ('vip202108170001', '2021-08-18 11:15:55', 3, 1);
INSERT INTO `score_info` VALUES ('vip202108170001', '2021-08-18 11:16:33', 2, 1);
INSERT INTO `score_info` VALUES ('vip202108170001', '2021-08-18 11:19:26', 1, 1);
INSERT INTO `score_info` VALUES ('vip202108170001', '2021-08-18 11:21:37', 1, 1);
INSERT INTO `score_info` VALUES ('vip202108170001', '2021-08-18 11:22:36', 2, 5);
INSERT INTO `score_info` VALUES ('vip202108170001', '2021-08-18 13:08:02', 2, 1);
INSERT INTO `score_info` VALUES ('vip202108170001', '2021-08-18 13:08:23', 97, 1);
INSERT INTO `score_info` VALUES ('vip202108170001', '2021-08-18 13:11:17', 2, 1);

-- ----------------------------
-- Table structure for sell_info
-- ----------------------------
DROP TABLE IF EXISTS `sell_info`;
CREATE TABLE `sell_info`  (
  `s_c_number` int(50) NULL DEFAULT NULL,
  `s_quantity` int(11) NULL DEFAULT NULL,
  `s_time` datetime NULL DEFAULT NULL,
  `s_e_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_vip_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  INDEX `s_c_number_FK`(`s_c_number`) USING BTREE,
  INDEX `s_e_number_FK`(`s_e_number`) USING BTREE,
  CONSTRAINT `s_c_number_FK` FOREIGN KEY (`s_c_number`) REFERENCES `goods` (`c_number`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `s_e_number_FK` FOREIGN KEY (`s_e_number`) REFERENCES `employee` (`number`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sell_info
-- ----------------------------
INSERT INTO `sell_info` VALUES (2, 2, '2021-01-01 23:35:33', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (2, 100, '2020-06-01 14:17:24', 's0002', 'vip201901020001');
INSERT INTO `sell_info` VALUES (2, 100, '2021-07-01 14:18:28', 's0002', 'vip201901020001');
INSERT INTO `sell_info` VALUES (1, 4, '2021-04-09 08:10:46', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (1, 4, '2021-04-01 08:11:06', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (1, 4, '2021-05-01 08:21:22', 'S0002', 'vip201901020002');
INSERT INTO `sell_info` VALUES (1, 4, '2021-05-12 08:29:37', 'S0002', 'vip201901020002');
INSERT INTO `sell_info` VALUES (2, 3, '2021-05-12 08:29:39', 'S0002', 'vip201901020002');
INSERT INTO `sell_info` VALUES (1, 12, '2021-05-12 09:19:31', 'S0002', 'vip201901020002');
INSERT INTO `sell_info` VALUES (2, 3, '2021-04-12 09:19:34', 'S0002', 'vip201901020002');
INSERT INTO `sell_info` VALUES (1, 12, '2021-07-12 09:23:31', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (2, 13, '2021-08-12 09:23:34', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (1, 12, '2021-08-12 09:24:27', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (2, 12, '2021-08-12 09:24:29', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (1, 23, '2021-08-12 09:25:19', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (2, 1, '2021-08-12 09:25:22', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (1, 23, '2021-07-01 09:26:07', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (2, 1, '2021-08-12 09:26:08', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (1, 2, '2021-08-12 09:28:58', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (2, 3, '2021-08-12 09:29:00', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-12 09:30:46', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (2, 1, '2021-04-28 09:30:48', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-12 09:31:17', 'S0002', 'vip201901020001');
INSERT INTO `sell_info` VALUES (2, 1, '2021-08-12 09:31:19', 'S0002', 'vip201901020001');
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-12 09:31:45', 'S0002', 'vip201901020001');
INSERT INTO `sell_info` VALUES (2, 1, '2021-08-12 09:31:46', 'S0002', 'vip201901020001');
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-12 09:34:15', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (2, 1, '2021-08-12 09:34:20', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-12 09:35:11', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (2, 1, '2021-08-12 09:35:12', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-12 09:44:09', 'S0002', 'vip201901020001');
INSERT INTO `sell_info` VALUES (2, 1, '2021-08-12 09:44:11', 'S0002', 'vip201901020001');
INSERT INTO `sell_info` VALUES (1, 4, '2021-08-12 11:32:57', 'S0002', 'vip201901020003');
INSERT INTO `sell_info` VALUES (2, 4, '2021-08-12 11:32:59', 'S0002', 'vip201901020003');
INSERT INTO `sell_info` VALUES (5, 1, '2021-08-12 11:33:02', 'S0002', 'vip201901020003');
INSERT INTO `sell_info` VALUES (1, 2, '2021-08-13 04:03:14', 'S0009', 'vip202108120001');
INSERT INTO `sell_info` VALUES (3, 4, '2021-08-13 04:03:15', 'S0009', 'vip202108120001');
INSERT INTO `sell_info` VALUES (1, 3, '2021-08-13 04:04:26', 'S0009', 'vip202108120001');
INSERT INTO `sell_info` VALUES (1, 4, '2021-08-13 04:05:49', 'S0009', 'vip202108120002');
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-13 04:11:36', 'S0009', 'vip202108120002');
INSERT INTO `sell_info` VALUES (2, 1, '2021-08-13 04:11:37', 'S0009', 'vip202108120002');
INSERT INTO `sell_info` VALUES (2, 1, '2021-08-13 04:11:51', 'S0009', 'vip202108120002');
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-13 04:46:55', 'S0013', 'vip202108120001');
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-16 20:21:56', 'S0009', 'vip202108120002');
INSERT INTO `sell_info` VALUES (2, 3, '2021-08-16 20:22:00', 'S0009', 'vip202108120002');
INSERT INTO `sell_info` VALUES (1, 2, '2021-08-16 20:25:22', 'S0009', 'vip202108120002');
INSERT INTO `sell_info` VALUES (2, 3, '2021-08-16 20:25:24', 'S0009', 'vip202108120002');
INSERT INTO `sell_info` VALUES (1, 0, '2021-08-16 20:28:31', 'S0009', NULL);
INSERT INTO `sell_info` VALUES (2, 0, '2021-08-16 20:28:34', 'S0009', NULL);
INSERT INTO `sell_info` VALUES (1, -1, '2021-08-16 20:28:53', 'S0009', NULL);
INSERT INTO `sell_info` VALUES (1, 2, '2021-08-17 09:05:05', 'S0009', 'vip202108120002');
INSERT INTO `sell_info` VALUES (3, 1, '2021-08-17 09:05:07', 'S0009', 'vip202108120002');
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-17 09:05:12', 'S0009', 'vip202108120002');
INSERT INTO `sell_info` VALUES (1, 2, '2021-08-17 12:44:24', 'S0009', NULL);
INSERT INTO `sell_info` VALUES (2, 1, '2021-08-17 12:44:26', 'S0009', NULL);
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-17 12:44:41', 'S0009', 'vip202108170001');
INSERT INTO `sell_info` VALUES (2, 1, '2021-08-17 12:44:43', 'S0009', 'vip202108170001');
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-17 18:37:36', 'S0009', 'vip202108160002');
INSERT INTO `sell_info` VALUES (2, 3, '2021-08-17 18:37:38', 'S0009', 'vip202108160002');
INSERT INTO `sell_info` VALUES (1, 1, '2021-08-18 11:12:05', 'S0003', 'vip202108160002');
INSERT INTO `sell_info` VALUES (2, 3, '2021-08-18 11:12:06', 'S0003', 'vip202108160002');
INSERT INTO `sell_info` VALUES (1, 2, '2021-08-18 14:12:43', 'S0004', 'vip202108120002');
INSERT INTO `sell_info` VALUES (3, 1, '2021-08-18 14:12:44', 'S0004', 'vip202108120002');

-- ----------------------------
-- Table structure for vip
-- ----------------------------
DROP TABLE IF EXISTS `vip`;
CREATE TABLE `vip`  (
  `v_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `v_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `v_score` int(11) NULL DEFAULT NULL,
  `v_phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `v_date` date NULL DEFAULT NULL,
  `v_remark` int(11) NOT NULL,
  PRIMARY KEY (`v_number`) USING BTREE,
  UNIQUE INDEX `v_phone`(`v_phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vip
-- ----------------------------
INSERT INTO `vip` VALUES ('null', 'null', NULL, 'null', '0000-00-00', 1);
INSERT INTO `vip` VALUES ('vip201901020001', '东方不败', 90, '15844760501', '2019-01-17', 1);
INSERT INTO `vip` VALUES ('vip201901020002', '令狐冲', 410, '15844760502', '2019-01-17', 1);
INSERT INTO `vip` VALUES ('vip201901020003', '雯儿', 13, '13553799923', '2019-01-17', 0);
INSERT INTO `vip` VALUES ('vip202108120002', '张大葱', 9137, '13553799954', '2021-08-12', 1);
INSERT INTO `vip` VALUES ('vip202108160001', '杂志', 13, '13553799953', '2021-08-16', 0);
INSERT INTO `vip` VALUES ('vip202108160002', '往往', 5899, '13553799952', '2021-08-16', 1);
INSERT INTO `vip` VALUES ('vip202108170001', '张三', 10234, '13553799951', '2021-08-17', 0);
INSERT INTO `vip` VALUES ('vip202108180001', '王五', 2000, '13553799966', '2021-08-18', 0);
INSERT INTO `vip` VALUES ('vip202108180002', '张桑', 0, '13553799950', '2021-08-18', 1);

-- ----------------------------
-- Table structure for work_date
-- ----------------------------
DROP TABLE IF EXISTS `work_date`;
CREATE TABLE `work_date`  (
  `work_date_id` int(11) NOT NULL AUTO_INCREMENT,
  `work_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`work_date_id`) USING BTREE,
  UNIQUE INDEX `UQ_work_date`(`work_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of work_date
-- ----------------------------
INSERT INTO `work_date` VALUES (19, '2018-08-23');
INSERT INTO `work_date` VALUES (20, '2018-08-24');
INSERT INTO `work_date` VALUES (21, '2018-08-27');
INSERT INTO `work_date` VALUES (22, '2018-08-28');
INSERT INTO `work_date` VALUES (23, '2019-01-17');
INSERT INTO `work_date` VALUES (24, '2019-01-18');
INSERT INTO `work_date` VALUES (25, '2019-01-19');
INSERT INTO `work_date` VALUES (26, '2019-01-20');
INSERT INTO `work_date` VALUES (27, '2019-01-21');
INSERT INTO `work_date` VALUES (28, '2019-01-22');
INSERT INTO `work_date` VALUES (41, '2020-01-01');
INSERT INTO `work_date` VALUES (29, '2021-08-12');
INSERT INTO `work_date` VALUES (30, '2021-08-13');
INSERT INTO `work_date` VALUES (34, '2021-08-14');
INSERT INTO `work_date` VALUES (35, '2021-08-15');
INSERT INTO `work_date` VALUES (36, '2021-08-16');
INSERT INTO `work_date` VALUES (37, '2021-08-17');
INSERT INTO `work_date` VALUES (39, '2021-08-18');
INSERT INTO `work_date` VALUES (40, '2021-08-19');

-- ----------------------------
-- View structure for check_info
-- ----------------------------
DROP VIEW IF EXISTS `check_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `check_info` AS select t.work_date,t.employee_no,clock_in_time,clock_off_time, 
	case 
	        when diff_in_time<-120
			then '旷工'
		when diff_in_time<0 
			then '迟到'
		when diff_in_time>=0
			then '正常'
		else '忘记打卡'
	end as diff_in_status,
	case 
	        when diff_off_time>120
			then '旷工'
		when diff_off_time>0 
			then '早退'
		when diff_off_time<=0
			then '正常'
		else '忘记打卡'
	end as diff_off_time


from check_info_son t ;

-- ----------------------------
-- View structure for check_info_son
-- ----------------------------
DROP VIEW IF EXISTS `check_info_son`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `check_info_son` AS select work_date,employee_no,clock_in_time,clock_off_time,
TIMESTAMPDIFF(MINUTE, clock_in_time, concat(clock_date,' 09:00:00')) as diff_in_time,
TIMESTAMPDIFF(MINUTE, clock_off_time, concat(clock_date,' 18:00:00')) as diff_off_time
from work_date w 
left join clock_info t 
on t.clock_date = w.work_date ;

-- ----------------------------
-- Triggers structure for table sell_info
-- ----------------------------
DROP TRIGGER IF EXISTS `t1`;
delimiter ;;
CREATE TRIGGER `t1` AFTER INSERT ON `sell_info` FOR EACH ROW BEGIN
UPDATE goods SET inventory = (inventory-new.s_quantity)WHERE c_number = new.s_c_number;
UPDATE vip SET v_score= v_score+(new.s_quantity*(select vip_price from goods where c_number=new.s_c_number
))WHERE v_number = new.s_vip_number and new.s_vip_number != 'null';
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
