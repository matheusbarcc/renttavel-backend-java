DROP DATABASE IF EXISTS dbrenttavel;
CREATE DATABASE dbrenttavel;
USE dbrenttavel;

CREATE TABLE `anfitriao`(
	`id` INT NOT NULL AUTO_INCREMENT
    , `nome` VARCHAR(120) NOT NULL
    , `email` VARCHAR(120) NOT NULL
    , `perfil_acesso` VARCHAR(255) DEFAULT 'ANFITRIAO' NOT NULL COMMENT 'ANFITRIAO, COLABORADOR'
    , `senha` VARCHAR(255) NULL COMMENT 'Senha cifrada'
    , `id_sessao` VARCHAR(255) NULL
    , PRIMARY KEY(`id`)
);

CREATE TABLE `inquilino`(
	`id` INT NOT NULL AUTO_INCREMENT
    , `nome` VARCHAR(120) NOT NULL
    , `email` VARCHAR(120) NOT NULL
    , `telefone` VARCHAR(15)
    , `id_anfitriao` INT NOT NULL
    , FOREIGN KEY (`id_anfitriao`) REFERENCES `anfitriao`(`id`)
    , PRIMARY KEY(`id`)
);

CREATE TABLE `endereco`(
	`id` INT NOT NULL AUTO_INCREMENT
    , `numero` INT NOT NULL
    , `cep` VARCHAR(8) NOT NULL
    , `rua` VARCHAR(120) NOT NULL
    , `bairro` VARCHAR(120) NOT NULL
    , `cidade` VARCHAR(120) NOT NULL
    , `estado` VARCHAR(120) NOT NULL
    , `pais` VARCHAR(120) NOT NULL
    , `id_anfitriao` INT NOT NULL
    , FOREIGN KEY (`id_anfitriao`) REFERENCES `anfitriao`(`id`)
    , PRIMARY KEY(`id`)
);

CREATE TABLE `imovel`(
	`id` INT NOT NULL AUTO_INCREMENT
    , `nome` VARCHAR(120) NOT NULL
	, `tipo` INT NOT NULL -- 1 = Casa | 2 = Apartamento | 3 = Unidade Secundaria
    , `capacidadePessoas` INT NOT NULL
    , `qtdQuarto` INT NOT NULL
    , `qtdCama` INT NOT NULL
    , `qtdBanheiro` INT NOT NULL
    , `descricao` VARCHAR(300)
    , `ocupado` BOOLEAN NOT NULL
    , `id_anfitriao` INT NOT NULL
    , `id_endereco` INT NOT NULL
    , PRIMARY KEY(`id`)
    , FOREIGN KEY (`id_anfitriao`) REFERENCES `anfitriao`(`id`)
    , FOREIGN KEY (`id_endereco`) REFERENCES `endereco`(`id`)
);

CREATE TABLE `aluguel`(
	`id` INT NOT NULL AUTO_INCREMENT
    , `data_checkin` DATETIME NOT NULL
	, `data_checkoutPrevisto` DATETIME NOT NULL
    , `data_checkoutEfetivo` DATETIME
    , `valorTotal` DECIMAL(8,2) NOT NULL
    , `valorDiaria` DECIMAL(8,2) NOT NULL
    , `valorLimpeza` DECIMAL(8,2) NOT NULL
    , `valorMulta` DECIMAL(8,2)
    , `qtdDias` INT NOT NULL
    , `id_imovel` INT NOT NULL
    , `id_inquilino` INT NOT NULL
    , `id_anfitriao` INT NOT NULL
    , PRIMARY KEY(`id`)
    , FOREIGN KEY (`id_imovel`) REFERENCES `imovel`(`id`)
	, FOREIGN KEY (`id_inquilino`) REFERENCES `inquilino`(`id`)
    , FOREIGN KEY (`id_anfitriao`) REFERENCES `anfitriao`(`id`)
);