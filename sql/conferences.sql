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
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE);

CREATE TABLE IF NOT EXISTS `conferences`.`report` (
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
('first@gmail.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'First', 'Visitor'), 
('second@gmail.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Second', 'Reviewer'),
('third@gmail.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Third', 'Fan'),
('fourth@gmail.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Fourth', 'Critic'),
('fifth@gmail.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Fifth', 'Switcher'),
('sixth@gmail.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Sixth', 'Student'),
('seventh@gmail.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Seventh', 'Trainee'),
('eighth@gmail.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Eighth', 'Junior'),
('ninth@gmail.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Ninth', 'JustForFun'),
('tenth@gmail.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'James', 'Gosling'),
('Maxim_Veres@epam.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Maxim', 'Veres'),
('Yuriy_Mischeryakov@epam.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Yuriy', 'Mischeryakov'),
('Dmytro_Kolesnikov@epam.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Dmytro', 'Kolesnikov'),
('moderator1@epam.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Main', 'Moderator'),
('moderator2@epam.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Support', 'Moderator'),
('admin@epam.com', '$argon2i$v=19$m=15360,t=2,p=1$bYZeAKEO9XMyEr2dlXtZFQ$7hniqf5oE7fF0/nm3Qhbjd2VbvaPF4nuYoLKo1O4acE', 'Admin', 'Admin');
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
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(3, 8),
(3, 9),
(3, 10),
(5, 1),
(5, 3),
(5, 5),
(5, 7),
(5, 9),
(6, 1),
(6, 2),
(6, 3),
(6, 4),
(6, 8),
(6, 9),
(6, 10),
(7, 2),
(7, 3),
(7, 4),
(7, 5),
(7, 8),
(7, 9),
(8, 2),
(8, 4),
(8, 5),
(8, 8),
(8, 9),
(9, 7),
(9, 8),
(10, 5),
(10, 1);
UPDATE event SET visitors=5 WHERE id=1;
UPDATE event SET visitors=2 WHERE id=2;
UPDATE event SET visitors=2 WHERE id=3;
UPDATE event SET visitors=3 WHERE id=4;
UPDATE event SET visitors=3 WHERE id=5;
INSERT INTO report (id, topic, event_id) VALUES 
(DEFAULT, 'SQL. Introduction to SQL and JDBC', 1),
(DEFAULT, 'SQL. Introduction to SQL and JDBC', 2),
(DEFAULT, 'SQL. Introduction to SQL and JDBC', 3),
(DEFAULT, 'SQL. Introduction to SQL and JDBC', 4),
(DEFAULT, 'SQL. Introduction to SQL and JDBC', 5),
(DEFAULT, 'SQL. Introduction to SQL and JDBC', 6),
(DEFAULT, 'SQL. Introduction to SQL and JDBC', 7),
(DEFAULT, 'SQL. Introduction to SQL and JDBC', 8),
(DEFAULT, 'SQL. Introduction to SQL and JDBC', 9),
(DEFAULT, 'SQL. Introduction to SQL and JDBC', 10),
(DEFAULT, 'Collections. Intoduction to Java Collections API', 8),
(DEFAULT, 'IO. Intoduction to IO and NIO API', 8),
(DEFAULT, 'Java 8. Functional interfaces, lambdas, Stream API', 8),
(DEFAULT, 'XML. Intoduction to XML and java.xml API', 7),
(DEFAULT, 'XML. Intoduction to XML and java.xml API', 8),
(DEFAULT, 'Generics. Intoduction to Generics', 6),
(DEFAULT, 'Generics. Intoduction to Generics', 8),
(DEFAULT, 'Generics. Intoduction to Generics', 9),
(DEFAULT, 'Exceptions. Java exceptions, try-catch-finally, throw vs throws', 7),
(DEFAULT, 'Exceptions. Java exceptions, try-catch-finally, throw vs throws', 10);
UPDATE report SET user_id=11 WHERE id=1;
UPDATE report SET user_id=11 WHERE id=2;
UPDATE report SET user_id=11 WHERE id=3;
UPDATE report SET user_id=11 WHERE id=4;
UPDATE report SET user_id=11 WHERE id=5;
UPDATE report SET user_id=11 WHERE id=6;
UPDATE report SET user_id=12 WHERE id=8;
UPDATE report SET user_id=12 WHERE id=9;
UPDATE report SET user_id=12 WHERE id=10;
UPDATE report SET user_id=12 WHERE id=11;
UPDATE report SET user_id=13 WHERE id=15;
UPDATE report SET user_id=13 WHERE id=7;
UPDATE report SET user_id=13 WHERE id=12;
UPDATE report SET user_id=12 WHERE id=13;

select * from report;