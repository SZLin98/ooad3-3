/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : oomall

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 29/11/2019 09:34:35
*/
-- ----------------------------
-- Table structure for be_shared_item
-- ----------------------------
DROP TABLE IF EXISTS `be_shared_item`;
CREATE TABLE `be_shared_item` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(11) unsigned DEFAULT NULL,
  `sharer_id` bigint(11) unsigned DEFAULT NULL,
  `status` tinyint(2) DEFAULT NULL,
  `birthtime` datetime DEFAULT NULL,
  `be_shared_user_id` bigint(11) unsigned DEFAULT NULL,
  `gmt_create` datetime(2) DEFAULT NULL,
  `gmt_modified` datetime(2) DEFAULT NULL,
  `is_deleted` tinyint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
);
-- ----------------------------
-- Table structure for share_item
-- ----------------------------
DROP TABLE IF EXISTS `share_item`;
CREATE TABLE `share_item` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned DEFAULT NULL,
  `goods_id` bigint(11) unsigned DEFAULT NULL,
  `status` tinyint(2) unsigned DEFAULT NULL,
  `success_num` smallint(5) unsigned DEFAULT '0',
  `gmt_create` datetime(2) DEFAULT NULL,
  `gmt_modified` datetime(2) DEFAULT NULL,
  `is_deleted` tinyint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for share_rule
-- ----------------------------
DROP TABLE IF EXISTS `share_rule`;
CREATE TABLE `share_rule` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `begin_time` datetime(2) DEFAULT NULL,
  `end_time` datetime(2) DEFAULT NULL,
  `share_level_strategy` varchar(255) DEFAULT NULL,
  `goods_id` bigint(11) unsigned DEFAULT NULL,
  `gmt_modified` datetime(2) DEFAULT NULL,
  `is_deleted` tinyint(1) unsigned DEFAULT '0',
  `gmt_create` datetime(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
