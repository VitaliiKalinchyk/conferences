-- Schema creation

DROP SCHEMA IF EXISTS `empty_conferences`;
CREATE SCHEMA IF NOT EXISTS `empty_conferences`;
USE `empty_conferences`;

CREATE TABLE IF NOT EXISTS `role` (
  `id` INT NOT NULL,
  `role_name` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `roleName_UNIQUE` (`role_name` ASC) VISIBLE);

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(120) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `role_id` INT NOT NULL DEFAULT 4,
  `notification` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_role_idx` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `empty_conferences`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `event` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(150) NOT NULL,
  `date` DATE NOT NULL,
  `location` VARCHAR(45) NOT NULL,
  `description` VARCHAR(400) NOT NULL,
  `visitors` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE);

CREATE TABLE IF NOT EXISTS `report` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `topic` VARCHAR(200) NOT NULL,
  `event_id` INT NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_report_event1_idx` (`event_id` ASC) VISIBLE,
  INDEX `fk_report_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_report_event1`
    FOREIGN KEY (`event_id`)
    REFERENCES `empty_conferences`.`event` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_report_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `empty_conferences`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `user_has_event` (
  `user_id` INT NOT NULL,
  `event_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `event_id`),
  INDEX `fk_user_has_event_event1_idx` (`event_id` ASC) VISIBLE,
  INDEX `fk_user_has_event_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_event_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `empty_conferences`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_has_event_event1`
    FOREIGN KEY (`event_id`)
    REFERENCES `empty_conferences`.`event` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
    INSERT INTO role VALUES (1, 'ADMIN'), (2, 'MODERATOR'), (3, 'SPEAKER'), (4, 'VISITOR');