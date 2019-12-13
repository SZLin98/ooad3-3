ALTER TABLE share_rule modify gmt_create datetime DEFAULT CURRENT_TIMESTAMP; 
ALTER TABLE share_rule modify gmt_modified datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;