/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : practice

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2018-06-28 20:04:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_problem
-- ----------------------------
DROP TABLE IF EXISTS `t_problem`;
CREATE TABLE `t_problem` (
  `id` int(11) NOT NULL COMMENT '题目id',
  `proKey` varchar(40) DEFAULT NULL COMMENT '题型关键字',
  `typeID` int(11) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL COMMENT '题目描述',
  `expression1` varchar(60) DEFAULT NULL COMMENT '表达式1',
  `expression2` varchar(60) DEFAULT NULL COMMENT '表达式2',
  `expression3` varchar(60) DEFAULT NULL COMMENT '表达式3',
  `answer` varchar(100) DEFAULT NULL COMMENT '答案',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_problem
-- ----------------------------
INSERT INTO `t_problem` VALUES ('1', 'calculateQues', '1', '1+2=(?)', null, null, null, '3');
INSERT INTO `t_problem` VALUES ('2', 'calculateQues', '1', '22+(?)=100', null, null, null, '78');
INSERT INTO `t_problem` VALUES ('3', 'calculateQues', '1', '(?)+66=100', null, null, null, '34');
INSERT INTO `t_problem` VALUES ('4', 'applicationQues', '6', '商城有50辆自行车，又运进50辆，卖了30辆。商场现在有多少辆自行车？', '50+50=(100)', '(100)-30=(70)', null, '70');
INSERT INTO `t_problem` VALUES ('5', 'applicationQues', '6', '商城有50辆自行车，又运进35辆。商场现在有多少辆自行车？', '50+35=(?)', null, null, '85');
