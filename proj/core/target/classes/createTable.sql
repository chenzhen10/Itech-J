CREATE SCHEMA kim ;
#attachment
CREATE TABLE IF NOT EXISTS `kim`.`attachment` (
  `idattachment` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `date` DATE NULL DEFAULT NULL,
  `commentary` VARCHAR(255) NULL DEFAULT NULL,
  `idclient` INT(10) UNSIGNED NOT NULL,
  `path` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idattachment`),
  INDEX `idclient_idx` (`idclient` ASC) VISIBLE,
  CONSTRAINT `idclien`
  FOREIGN KEY (`idclient`)
  REFERENCES `kim`.`client` (`idclient`)
    ON DELETE CASCADE)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

#client
CREATE TABLE IF NOT EXISTS `kim`.`client` (
  `idclient` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(85) NOT NULL,
  `surname` VARCHAR(85) NOT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `date` DATE NULL DEFAULT NULL,
  `gender` ENUM('Male', 'Female') NULL DEFAULT NULL,
  `citizenship` VARCHAR(65) NULL DEFAULT NULL,
  `marital_status` VARCHAR(45) NULL DEFAULT NULL,
  `web_site` VARCHAR(75) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `workplace` VARCHAR(75) NULL DEFAULT NULL,
  `country` VARCHAR(45) NULL DEFAULT NULL,
  `city` VARCHAR(45) NULL DEFAULT NULL,
  `street` VARCHAR(45) NULL DEFAULT NULL,
  `house` VARCHAR(45) NULL DEFAULT NULL,
  `num_of_house` VARCHAR(45) NULL DEFAULT NULL,
  `index` INT(11) NULL DEFAULT NULL,
  `photo_path` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`idclient`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

#phone
CREATE TABLE IF NOT EXISTS `kim`.`phone` (
  `idphone` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `country_code` INT(11) NULL DEFAULT NULL,
  `operator_code` INT(11) NULL DEFAULT NULL,
  `number` INT(11) NOT NULL,
  `type` ENUM('Mobile', 'Home') NULL DEFAULT NULL,
  `commentary` VARCHAR(255) NULL DEFAULT NULL,
  `idclient` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`idphone`),
  INDEX `idclient_idx` (`idclient` ASC) VISIBLE,
  CONSTRAINT `idclient`
  FOREIGN KEY (`idclient`)
  REFERENCES `kim`.`client` (`idclient`)
    ON DELETE CASCADE)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


