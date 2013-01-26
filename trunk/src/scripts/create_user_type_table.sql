CREATE TABLE `client_loan`.`user_types` (
  `user_type_id` INT  NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(32)  NOT NULL,
  `description` TEXT ,
  PRIMARY KEY (`user_type_id`)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;
