/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : practice

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2018-06-28 20:04:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_record
-- ----------------------------
DROP TABLE IF EXISTS `t_record`;
CREATE TABLE `t_record` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '题目ID',
  `userID` int(20) NOT NULL COMMENT '学生ID',
  `recordTime` datetime(6) DEFAULT NULL COMMENT '答题时间',
  `proKey` varchar(40) DEFAULT NULL,
  `typeID` int(20) DEFAULT NULL,
  `description` varchar(60) DEFAULT NULL COMMENT '题目描述',
  `expression1` varchar(60) DEFAULT NULL,
  `expression2` varchar(60) DEFAULT NULL,
  `expression3` varchar(60) DEFAULT NULL,
  `result` varchar(60) DEFAULT NULL COMMENT '学生答案',
  `correct` varchar(60) DEFAULT NULL COMMENT '正确答案',
  `status` int(6) DEFAULT NULL COMMENT '1/正确   0/错误',
  PRIMARY KEY (`id`,`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_record
-- ----------------------------
INSERT INTO `t_record` VALUES ('2', '1', null, 'calculateQues', '1', '22+(?)=100', null, null, null, '7800', '78', '0');
INSERT INTO `t_record` VALUES ('4', '1', null, 'applicationQues', '6', '商城有50辆自行车，又运进50辆，卖了30辆。商场现在有多少辆自行车？', '50 50=(100)', '(100)-30=(70)', '', '70', '70', '0');
