-- Schema creation

DROP SCHEMA IF EXISTS `conferences`;
CREATE SCHEMA IF NOT EXISTS `conferences`;
USE `conferences`;

CREATE TABLE IF NOT EXISTS `conferences`.`role` (
  `id` INT NOT NULL,
  `role_name` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `roleName_UNIQUE` (`role_name` ASC) VISIBLE);

CREATE TABLE IF NOT EXISTS `conferences`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(120) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
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
    REFERENCES `conferences`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `conferences`.`event` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(150) NOT NULL,
  `date` DATE NOT NULL,
  `location` VARCHAR(45) NOT NULL,
  `description` VARCHAR(400) NOT NULL,
  `visitors` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);

CREATE TABLE IF NOT EXISTS `conferences`.`report` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `topic` VARCHAR(200) NOT NULL,
  `accepted` TINYINT NULL,
  `approved` TINYINT NULL,
  `event_id` INT NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_report_event1_idx` (`event_id` ASC) VISIBLE,
  INDEX `fk_report_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_report_event1`
    FOREIGN KEY (`event_id`)
    REFERENCES `conferences`.`event` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_report_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `conferences`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `conferences`.`user_has_event` (
  `user_id` INT NOT NULL,
  `event_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `event_id`),
  INDEX `fk_user_has_event_event1_idx` (`event_id` ASC) VISIBLE,
  INDEX `fk_user_has_event_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_event_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `conferences`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_has_event_event1`
    FOREIGN KEY (`event_id`)
    REFERENCES `conferences`.`event` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
-- Start values

INSERT INTO role VALUES (1, 'ADMIN'), (2, 'MODERATOR'), (3, 'SPEAKER'), (4, 'VISITOR');
INSERT INTO user (email, password, name, surname) VALUES 
('first@gmail.com', 'Password1', 'First', 'Visitor'), 
('second@gmail.com', 'Password2', 'Second', 'Reviewer'),
('third@gmail.com', 'Password3', 'Third', 'Fan'),
('fourth@gmail.com', 'Password4', 'Fourth', 'Critic'),
('fifth@gmail.com', 'Password5', 'Fifth', 'Switcher'),
('sixth@gmail.com', 'Password6', 'Sixth', 'Student'),
('seventh@gmail.com', 'Password7', 'Seventh', 'Trainee'),
('eighth@gmail.com', 'Password8', 'Eighth', 'Junior'),
('ninth@gmail.com', 'Password9', 'Ninth', 'JustForFun'),
('tenth@gmail.com', 'Password10', 'James', 'Gosling'),
('Maxim_Veres@epam.com', 'passwordV1', 'Maxim', 'Veres'),
('Yuriy_Mischeryakov@epam.com', 'passwordM1', 'Yuriy', 'Mischeryakov'),
('Dmytro_Kolesnikov@epam.com', 'passwordK1', 'Dmytro', 'Kolesnikov'),
('moderator1@epam.com', 'passwordMod1', 'Main', 'Moderator'),
('moderator2@epam.com', 'passwordMod2', 'Support', 'Moderator'),
('admin@epam.com', 'passwordA1', 'Admin', 'Admin');
UPDATE user SET role_id=3 WHERE email LIKE '%@epam.com';
UPDATE user SET role_id=2 WHERE email='moderator1@epam.com';
UPDATE user SET role_id=2 WHERE email='moderator2@epam.com';
UPDATE user SET role_id=1 WHERE email='admin@epam.com';
INSERT INTO event (id, title, date, location, description) VALUES 
(DEFAULT, 'Java for Students Autumn 2021', '2021-10-29', 'Lutsk', 'Epam conference for Ukrainian students. Autumn 2022'),
(DEFAULT, 'Java for Switchers Spring', '2022-03-13', 'Dnipro', 'Epam conference for switchers'),
(DEFAULT, 'Java for Students Winter 2022', '2022-02-19', 'Odesa', 'Epam conference for Ukrainian students. Winter 2022'),
(DEFAULT, 'Java for Students Spring 2022', '2022-02-09', 'Lviv', 'Epam conference for Ukrainian students. Spring 2023'),
(DEFAULT, 'Java for Students Summer 2022', '2022-07-22', 'Cherkasy', 'Epam conference for Ukrainian students. Summer 2022'),
(DEFAULT, 'Java for Switchers Autumn', '2022-12-20', 'Kyiv', 'Epam conference for switchers'),
(DEFAULT, 'Java for Students Autumn 2022', '2022-11-24', 'Kherson', 'Epam conference for Ukrainian students. Autumn 2022'),
(DEFAULT, 'Java for Students Winter 2023', '2023-02-09', 'Donetsk', 'Epam conference for Ukrainian students. Winter 2023'),
(DEFAULT, 'Java for Students Spring 2023', '2023-04-17', 'Luhansk', 'Epam conference for Ukrainian students. Spring 2023'),
(DEFAULT, 'Java for Students Summer 2023', '2023-06-01', 'Simferopol', 'Epam conference for Ukrainian students. Summer 2023');
INSERT INTO user_has_event VALUES 
(1, 6),
(1, 7),
(2, 6),
(3, 7),
(4, 7),
(5, 7),
(6, 1),
(6, 2),
(6, 3),
(6, 4),
(6, 5),
(7, 2),
(7, 3),
(7, 4),
(7, 5),
(8, 2),
(8, 4),
(8, 5),
(9, 2),
(9, 5),
(10, 5);
UPDATE event SET visitors=1 WHERE id=1;
UPDATE event SET visitors=4 WHERE id=2;
UPDATE event SET visitors=2 WHERE id=3;
UPDATE event SET visitors=3 WHERE id=4;
UPDATE event SET visitors=3 WHERE id=5;
INSERT INTO report (id, topic, event_id) VALUES 
(DEFAULT, 'SQL. Introduction to SQL and JDBC', 7),
(DEFAULT, 'Collections. Intoduction to Java Collections API', 7),
(DEFAULT, 'IO. Intoduction to IO and NIO API', 7),
(DEFAULT, 'Java 8. Functional interfaces, lambdas, Stream API', 7),
(DEFAULT, 'XML. Intoduction to XML and java.xml API', 7),
(DEFAULT, 'Generics. Intoduction to Generics', 6),
(DEFAULT, 'Exceptions. Java exceptions, try-catch-finally, throw vs throws', 7);
UPDATE report SET user_id=13 WHERE id=5;
UPDATE report SET user_id=11 WHERE id=2;
UPDATE report SET user_id=11 WHERE id=3;
UPDATE report SET user_id=12 WHERE id=1;
UPDATE report SET approved=1, accepted=1 WHERE id=5;
UPDATE report SET approved=1 WHERE id=2;
UPDATE report SET approved=1 WHERE id=3;
UPDATE report SET accepted=1 WHERE id=1;