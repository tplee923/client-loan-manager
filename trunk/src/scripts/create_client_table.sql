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
