/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : practice

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2018-06-28 20:04:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type` (
  `typeID` int(20) NOT NULL AUTO_INCREMENT COMMENT '题型ID',
  `name` varchar(40) DEFAULT NULL COMMENT '类型名称(选择、判断……)',
  `subjectID` int(11) DEFAULT NULL COMMENT '所属科目',
  `description` varchar(60) DEFAULT NULL COMMENT '类型描述',
  PRIMARY KEY (`typeID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES ('1', '100以内加法', '1', '');
INSERT INTO `t_type` VALUES ('2', '100以内减法', '1', null);
INSERT INTO `t_type` VALUES ('3', '九九乘法', '1', null);
INSERT INTO `t_type` VALUES ('4', '除法', '1', null);
INSERT INTO `t_type` VALUES ('5', '混合计算', '1', null);
INSERT INTO `t_type` VALUES ('6', '应用题', '1', null);
INSERT INTO `t_type` VALUES ('7', '找规律', '1', null);
INSERT INTO `t_type` VALUES ('8', '比大小', '1', null);
INSERT INTO `t_type` VALUES ('9', '判断对错', '1', null);
INSERT INTO `t_type` VALUES ('10', '单位换算', '1', null);
