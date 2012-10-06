/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50041
Source Host           : localhost:3306
Source Database       : chess

Target Server Type    : MYSQL
Target Server Version : 50041
File Encoding         : 65001

Date: 2010-11-30 22:33:43
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `chess`
-- ----------------------------
DROP TABLE IF EXISTS `chess`;
CREATE TABLE `chess` (
  `ID` char(20) NOT NULL,
  `password` char(20) default NULL,
  `name` text,
  `sex` char(1) default NULL,
  `winNumber` int(11) default NULL,
  `failNumber` int(11) default NULL,
  `rank` int(11) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chess
-- ----------------------------
INSERT INTO `chess` VALUES ('12132', '123', '11', null, null, null, null);
INSERT INTO `chess` VALUES ('123', '11', '123', null, null, null, null);
INSERT INTO `chess` VALUES ('aaa', '123', 'aaa', null, null, null, null);
INSERT INTO `chess` VALUES ('aaaa', '123', '1312', null, null, null, null);
INSERT INTO `chess` VALUES ('abc', '123', '', 'm', '25', '18', '3');
INSERT INTO `chess` VALUES ('axun', '123', 'aaaaa', 'm', '25', '18', '3');
INSERT INTO `chess` VALUES ('bbb', '123', '123', null, null, null, null);
INSERT INTO `chess` VALUES ('ccc', '123', '123', null, null, null, null);
INSERT INTO `chess` VALUES ('ddd', '123', '123', null, null, null, null);
INSERT INTO `chess` VALUES ('eee', '123', '123', null, null, null, null);
