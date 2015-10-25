create database estacionamento;

use estacionamento;

create table tarifa(
    idtarifa int(8) auto_increment not null,
    descricao varchar(30) not null,
    valor decimal(15,2) not null,
    primary key(idtarifa)
);

create table veiculo(
    placa char(7) not null,
    marca varchar(20) not null,
    modelo varchar(20) not null,
    idtarifa int(8) not null,
    primary key(placa),
    foreign key(idtarifa)
    references tarifa(idtarifa)
);

create table usuario(
	nome varchar(30) not null,
	login varchar(20) not null,
	senha varchar(20) not null,
	email varchar(30) not null,
	primary key(login)
);
