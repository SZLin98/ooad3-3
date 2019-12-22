-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS oomall_payment;
CREATE TABLE oomall_payment (
  id bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  actual_price decimal(10,2)  DEFAULT NULL,
  pay_channel tinyint(3) unsigned DEFAULT NULL,
  status tinyint(3) unsigned DEFAULT '0',
  pay_time datetime(2) DEFAULT NULL,
  pay_sn varchar(63) DEFAULT NULL,
  begin_time datetime(2) DEFAULT NULL,
  end_time datetime(2) DEFAULT NULL,
  order_id bigint(11) unsigned DEFAULT NULL,
  is_deleted tinyint(1) unsigned DEFAULT '0',
  gmt_create datetime(2) DEFAULT NULL,
  gmt_modified datetime(2) DEFAULT NULL,
  PRIMARY KEY (id)
);
