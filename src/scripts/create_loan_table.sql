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
;
