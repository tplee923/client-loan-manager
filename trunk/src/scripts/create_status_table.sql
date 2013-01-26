CREATE TABLE `client_loan`.`status` (
  `status_id` INT  NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32)  NOT NULL,
  `description` TEXT ,
  PRIMARY KEY (`status_id`)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;
