CREATE DATABASE chess DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE chess;

DROP TABLE IF EXISTS `piece`;
DROP TABLE IF EXISTS `game`;


CREATE TABLE `game` (
	`game_id`	bigint AUTO_INCREMENT PRIMARY KEY,
	`turn`	varchar(20)	NOT NULL
);

CREATE TABLE `piece` (
	`piece_id`	bigint AUTO_INCREMENT PRIMARY KEY,
	`game_id`	bigint NOT NULL,
	`type`	varchar(20)	NOT NULL,
	`color`	varchar(20)	NOT NULL,
	`rank`	varchar(20)	NOT NULL,
	`file`	varchar(20)	NOT NULL,
	FOREIGN KEY (game_id) REFERENCES game (game_id)
);
