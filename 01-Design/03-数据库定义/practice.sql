/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : practice

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2018-06-28 07:30:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(40) DEFAULT NULL COMMENT '分类名称(个人挑战、智力应答……)',
  `description` varchar(60) DEFAULT NULL COMMENT '项目描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_category
-- ----------------------------

-- ----------------------------
-- Table structure for t_class
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class` (
  `id` int(20) NOT NULL COMMENT '班级ID',
  `name` varchar(40) DEFAULT NULL,
  `gradeID` int(20) DEFAULT NULL COMMENT '年级ID',
  `coordinator` varchar(40) DEFAULT NULL COMMENT '班主任',
  `description` varchar(60) DEFAULT NULL COMMENT '班级描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_class
-- ----------------------------
INSERT INTO `t_class` VALUES ('1', '二五班', '1', '1', '麒麟二五班');
INSERT INTO `t_class` VALUES ('2', '三六班', '2', '班主任李老师', '麒麟三六班');

-- ----------------------------
-- Table structure for t_examine
-- ----------------------------
DROP TABLE IF EXISTS `t_examine`;
CREATE TABLE `t_examine` (
  `examineID` int(20) NOT NULL,
  `seqNo` int(20) DEFAULT NULL COMMENT '题目序号',
  `studentID` int(20) DEFAULT NULL COMMENT '学生ID',
  `topicID` int(20) DEFAULT NULL COMMENT '题目ID',
  `categoryID` int(20) DEFAULT NULL COMMENT '题目分类',
  `topic` varchar(60) DEFAULT NULL COMMENT '题目描述',
  `options` varchar(60) DEFAULT NULL COMMENT '题目选项',
  `result` varchar(60) DEFAULT NULL COMMENT '学生答案',
  `correct` varchar(60) DEFAULT NULL COMMENT '正确打哪',
  `sunmitTime` datetime(6) DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`examineID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_examine
-- ----------------------------

-- ----------------------------
-- Table structure for t_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_grade`;
CREATE TABLE `t_grade` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '年级ID',
  `schoolId` int(10) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL COMMENT '年级名称',
  `description` varchar(60) DEFAULT NULL COMMENT '年级描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_grade
-- ----------------------------
INSERT INTO `t_grade` VALUES ('1', '1', '二年级', '麒麟小学二年级');
INSERT INTO `t_grade` VALUES ('2', '1', '三年级', '麒麟小学三年级');

-- ----------------------------
-- Table structure for t_problem
-- ----------------------------
DROP TABLE IF EXISTS `t_problem`;
CREATE TABLE `t_problem` (
  `id` int(11) NOT NULL COMMENT '题目id',
  `proTypeKey` varchar(40) DEFAULT NULL COMMENT '题型关键字',
  `categoryKey` varchar(40) DEFAULT NULL COMMENT '题型所属类别关键字',
  `categoryChildKey` varchar(40) DEFAULT NULL COMMENT '题型子类别关键字',
  `typeID` int(11) DEFAULT NULL COMMENT '所属题目分类',
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
INSERT INTO `t_problem` VALUES ('1', 'Add-A-Type', 'oralCalculation', null, '1', '1+2=(?)', null, null, null, '3');
INSERT INTO `t_problem` VALUES ('2', 'Add-B-Type', 'oralCalculation', null, '1', '22+(?)=100', null, null, null, '78');
INSERT INTO `t_problem` VALUES ('3', 'Add-C-Type', 'oralCalculation', '', '1', '(?)+66=100', null, null, null, '34');
INSERT INTO `t_problem` VALUES ('4', 'App-A-Type', 'applicationQuestions', 'directQues', '1', '商城有50辆自行车，又运进35辆。商场现在有多少辆自行车？', '', null, null, '85');
INSERT INTO `t_problem` VALUES ('5', 'App-B-Type', 'applicationQuestions', 'oneStepQues', '1', '商城有50辆自行车，又运进35辆。商场现在有多少辆自行车？', '50+35=(?)', null, null, '85');

-- ----------------------------
-- Table structure for t_protype
-- ----------------------------
DROP TABLE IF EXISTS `t_protype`;
CREATE TABLE `t_protype` (
  `proTypeKey` varchar(40) DEFAULT NULL COMMENT '题型关键字',
  `proTypeName` varchar(40) DEFAULT NULL COMMENT '题型名称',
  `categoryKey` varchar(40) DEFAULT NULL COMMENT '题型所属类别关键字',
  `categoryName` varchar(40) DEFAULT NULL COMMENT '题型所属类别名称',
  `categoryChildKey` varchar(40) DEFAULT NULL COMMENT '题型子类别关键字',
  `categoryChildName` varchar(40) DEFAULT NULL COMMENT '题型子类别名称',
  `typeID` int(10) DEFAULT NULL COMMENT '所属知识点',
  `description` varchar(60) DEFAULT NULL COMMENT '题型描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_protype
-- ----------------------------
INSERT INTO `t_protype` VALUES ('Add-A-Type', '加法A类', 'oralCalculation', '口算题', null, null, '1', 'a+b=(?)');
INSERT INTO `t_protype` VALUES ('Add-B-Type', '加法B类', 'oralCalculation', '口算题', null, null, '1', 'a+(?)=c');
INSERT INTO `t_protype` VALUES ('Add-C-Type', '加法C类', 'oralCalculation', '口算题', null, null, '1', '(?)+b=c');
INSERT INTO `t_protype` VALUES ('App-A-Type', '应用题A类', 'applicationQuestions', '应用题', 'direct\r\ndirect\r\ndirectQues', '直答题', '1', '不用写表达式，直接写最终答案');
INSERT INTO `t_protype` VALUES ('App-B-Type', '应用题B类', 'applicationQuestions', '应用题', 'oneStepQues', '单步题', '1', '要求写一步表达式，如：\r\n一个橡皮5元钱，一个铅笔刀8元钱，小宝有6元钱，小宝可以买1个橡皮，还是铅笔刀？');

-- ----------------------------
-- Table structure for t_record
-- ----------------------------
DROP TABLE IF EXISTS `t_record`;
CREATE TABLE `t_record` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '题目ID',
  `userID` int(20) NOT NULL COMMENT '学生ID',
  `recordTime` datetime(6) DEFAULT NULL COMMENT '答题时间',
  `typeID` int(20) DEFAULT NULL,
  `description` varchar(60) DEFAULT NULL COMMENT '题目描述',
  `options` varchar(40) DEFAULT NULL COMMENT '题目选项',
  `result` varchar(60) DEFAULT NULL COMMENT '学生答案',
  `correct` varchar(60) DEFAULT NULL COMMENT '正确答案',
  `status` int(6) DEFAULT NULL COMMENT '1/正确   0/错误',
  PRIMARY KEY (`id`,`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_record
-- ----------------------------

-- ----------------------------
-- Table structure for t_school
-- ----------------------------
DROP TABLE IF EXISTS `t_school`;
CREATE TABLE `t_school` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '学校ID',
  `name` varchar(40) DEFAULT NULL COMMENT '学校名称',
  `city` varchar(40) DEFAULT NULL COMMENT '学校所在城市',
  `location` varchar(60) DEFAULT NULL COMMENT '学校位置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_school
-- ----------------------------
INSERT INTO `t_school` VALUES ('1', '麒麟小学', null, null);

-- ----------------------------
-- Table structure for t_session
-- ----------------------------
DROP TABLE IF EXISTS `t_session`;
CREATE TABLE `t_session` (
  `third_session` varchar(50) NOT NULL,
  `session_key` varchar(50) DEFAULT NULL,
  `openId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`third_session`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_session
-- ----------------------------
INSERT INTO `t_session` VALUES ('11111', '22222', '33333');
INSERT INTO `t_session` VALUES ('79.83180038157984', '22222|33333', null);

-- ----------------------------
-- Table structure for t_statistics
-- ----------------------------
DROP TABLE IF EXISTS `t_statistics`;
CREATE TABLE `t_statistics` (
  `userID` int(11) NOT NULL COMMENT '学生id',
  `typeID` int(11) NOT NULL COMMENT '题型ID',
  `haveDone` int(11) DEFAULT NULL COMMENT '已做题数',
  `onceRight` int(11) DEFAULT NULL COMMENT '一次做对数',
  `flower` int(11) DEFAULT NULL COMMENT '红花数',
  PRIMARY KEY (`userID`,`typeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_statistics
-- ----------------------------
INSERT INTO `t_statistics` VALUES ('1', '1', '108', '80', '82');

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL COMMENT '学生姓名',
  `classID` int(20) DEFAULT NULL COMMENT '所属班级ID',
  `icon` varchar(60) DEFAULT NULL,
  `wxID` varchar(40) DEFAULT NULL COMMENT '微信ID号',
  `status` int(10) DEFAULT NULL COMMENT '未审核/已审核',
  `userType` int(10) DEFAULT NULL COMMENT '0:学生 1:老师',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('1', '小明老师', '1', '暂无头像', 'wx1234', '0', '1');
INSERT INTO `t_student` VALUES ('2', '小虎', '1', '暂无头像', 'wx4321', '0', '0');
INSERT INTO `t_student` VALUES ('3', '小王', '1', '暂无头像', 'wx2222', '0', '0');
INSERT INTO `t_student` VALUES ('4', '张三', '0', null, '33333', '0', '0');
INSERT INTO `t_student` VALUES ('5', '薛利飞', '1', null, '', '0', '0');

-- ----------------------------
-- Table structure for t_subject
-- ----------------------------
DROP TABLE IF EXISTS `t_subject`;
CREATE TABLE `t_subject` (
  `id` int(20) NOT NULL COMMENT '科目ID',
  `name` varchar(40) DEFAULT NULL COMMENT '科目名称',
  `gradeID` int(11) DEFAULT NULL COMMENT '所属年级ID',
  `description` varchar(60) DEFAULT NULL COMMENT '科目描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_subject
-- ----------------------------
INSERT INTO `t_subject` VALUES ('1', '数学', '1', '二年级数学');
INSERT INTO `t_subject` VALUES ('2', '语文', '1', '二年级语文');

-- ----------------------------
-- Table structure for t_topic
-- ----------------------------
DROP TABLE IF EXISTS `t_topic`;
CREATE TABLE `t_topic` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '题目ID',
  `categoryID` int(20) DEFAULT NULL COMMENT '题目分类',
  `typeID` int(20) DEFAULT NULL COMMENT '分类ID',
  `topic` varchar(60) DEFAULT NULL COMMENT '题目描述',
  `options` varchar(40) DEFAULT NULL COMMENT '题目选项',
  `result` varchar(60) DEFAULT NULL COMMENT '题目答案',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_topic
-- ----------------------------
INSERT INTO `t_topic` VALUES ('1', null, null, null, null, null);

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
