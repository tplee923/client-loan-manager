CREATE TABLE `client_loan`.`user_logins` (
  `user_login_id` INT  NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(128)  NOT NULL,
  `password` VARCHAR(256)  NOT NULL,
  PRIMARY KEY (`user_login_id`)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;
