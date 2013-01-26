CREATE TABLE `client_loan`.`user_logins` (
  `user_login_id` INT  NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(128)  NOT NULL,
  `password` VARCHAR(256)  NOT NULL,
  PRIMARY KEY (`user_login_id`)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `client_loan`.`users` (
  `user_id` INT  NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(128)  NOT NULL,
  `name` VARCHAR(128) ,
  `type` INT  NOT NULL,
  `status` BOOLEAN  DEFAULT 1,
  `last_login` DATETIME ,
  PRIMARY KEY (`user_id`)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `client_loan`.`clients` (
  `client_id` INT  NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64)  NOT NULL,
  `email` VARCHAR(64) ,
  `phone` VARCHAR(32) ,
  `address` VARCHAR(255) ,
  `employment` VARCHAR(32) ,
  `is_activate` BOOLEAN ,
  PRIMARY KEY (`client_id`)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `client_loan`.`loans` (
  `loan_id` INT  NOT NULL AUTO_INCREMENT,
  `loan_amount` FLOAT  NOT NULL,
  `loan_program` VARCHAR(64)  NOT NULL,
  `rate` FLOAT  NOT NULL,
  `closing_date` DATE  NOT NULL,
  `refi_interval` INT  DEFAULT 4 COMMENT 'unit is month',
  `property_name` VARCHAR(128) ,
  `status` ENUM('active', 'terminated')  NOT NULL DEFAULT 'active',
  `client_id` INT  NOT NULL,
  `loan_officer_id` INT  NOT NULL,
  `update_time` DATETIME  NOT NULL,
  PRIMARY KEY (`loan_id`)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `client_loan`.`user_types` (
  `user_type_id` INT  NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(32)  NOT NULL,
  `description` TEXT ,
  PRIMARY KEY (`user_type_id`)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `client_loan`.`users` MODIFY COLUMN `status` ENUM('active','terminated')  DEFAULT 'active';

ALTER TABLE `client_loan`.`loans` MODIFY COLUMN `update_time` DATETIME  DEFAULT NULL,
 ADD COLUMN `notes` TEXT  AFTER `update_time`;

ALTER TABLE `client_loan`.`users` ADD CONSTRAINT `user_type_constraint` FOREIGN KEY `user_type_constraint` (type)
    REFERENCES `user_types` (user_type_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `client_loan`.`loans` ADD CONSTRAINT `loan_client_constraint` FOREIGN KEY `loan_client_constraint` (client_id)
    REFERENCES `clients` (client_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `client_loan`.`loans` ADD CONSTRAINT `loan_officer_constraint` FOREIGN KEY `loan_officer_constraint` (loan_officer_id)
    REFERENCES `users` (user_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
