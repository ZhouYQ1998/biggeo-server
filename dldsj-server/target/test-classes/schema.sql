CREATE TABLE IF NOT EXISTS `tb_user` (
  `id`       int(11) NOT NULL AUTO_INCREMENT,
  `name`     varchar(255)     DEFAULT NULL,
  `password` varchar(255)     DEFAULT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `dldsj`.`tb_user` (`id`, `name`, `password`)
VALUES (1, 'jack1', '123456');
INSERT INTO `dldsj`.`tb_user` (`id`, `name`, `password`)
VALUES (2, 'mary2', '123456');
