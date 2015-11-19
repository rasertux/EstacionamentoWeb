DROP DATABASE IF EXISTS estacionamento;

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
	login varchar(20) not null unique,
	senha varchar(65) not null,
    hashrecuperasenha varchar(65) unique,
	email varchar(30) not null,
	primary key(login)
);

create table movimentacao(
	idmov int not null auto_increment,
    placa char(7) not null,
    entrada datetime not null,
    saida datetime,
    fatura decimal(15,2) default 0,
    primary key(idmov),
    foreign key(placa) references veiculo(placa)
);

/* Inserir usuário admin para administrar a aplicação - DML */

insert into usuario(nome, login, senha, email)
values('admin', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin@admin.com');
