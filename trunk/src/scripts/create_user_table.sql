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
