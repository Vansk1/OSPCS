/**
 * Radical Motos
 * @Vanderson
 */

drop database dbradicalPcs;

create database dbradicalPcs;
show databases;
use dbradicalPcs;
drop database dbradicalPcs;

-- unique não permite valore duplicados
create table usuarios (
	iduser int primary key auto_increment,
	nome varchar(30) not null,
    login varchar(20) not null unique,
    senha varchar(250) not null,
    perfil varchar(5) not null
);

describe usuarios;

-- md5() gera um hash (criptografia)
insert into usuarios(nome,login,senha,perfil)
values ('Administrador','admin', md5('admin'),'admin');

select * from usuarios;

-- autenticar um usuário (logar)
select * from usuarios where login = 'admin' and senha = md5('admin');

-- update
update usuarios set nome='Administrador', login='admin',
senha=md5('admin') where iduser=1;

-- pesquisa avançada (semelhante a busca do google)
select * from usuarios where nome like 'j%' order by nome;


create table clientes (
    idcli int primary key auto_increment,
    nome varchar(50) not null,
    telefone varchar(15) not null,
	numero varchar(10)not null,
    cidade varchar(30)not null,
    cep varchar(9)not null,
	bairro1 varchar(50),
    uf char(2),
    complemento1 varchar(20),
    endereco varchar(100)
	
   
);
drop table clientes;
describe clientes;
select * from clientes;

insert into clientes (nome,telefone,numero,cidade,cep,bairro1,uf,complemento1,endereco)
values ('Mariana Mendes','99999-1234','0000-0000','cidade','cep','bairro1','uf','complemento1','endereco');
insert into clientes (nome,telefone)
values ('Vanessa Mendes','555555-1234','0000-0000','bairro','uf','complemento');
insert into clientes (nome,telefone)

select * from clientes;

create table tecnicos (
	idtec int primary key auto_increment,
    nome varchar(30) not null unique
);

drop table tecnicos;

insert into tecnicos(nome) values('andré');
select * from tecnicos;
describe tecnicos;

/******* RELACIONAMENTO DE TABELAS 1-N *******/

-- decimal(10,2) tipo de dados (números não inteiros)
-- (10,2) (digitos,formatação de casas decimais)
-- timestamp default current_timestamp (data e hora automático)
-- foreign key(idcli) (campo da tabela OS que será vinculado)
-- references clientes(idcli)(referencia  
create table servicos (

	os int primary key auto_increment,
    marca varchar(200) not null,
	modelo varchar(10) not null,
    serie varchar(30) not null,
    acessorios varchar(9) not null,
    observacao varchar(200) null,
    DefeitoInformado varchar(200) not null,
    DefeitoDiagnosticado varchar(200) null,
    statusOS varchar(30) not null,
    valor decimal(10,2) null,
	formaPagamento varchar(30) null,
    saida date,
    dataOS timestamp default current_timestamp,
    idcli int not null,
    idtec int null ,
    usuario varchar(30) not null,
    tecnicos varchar(30) null,
	foreign key(idcli) references clientes(idcli),
    foreign key(idtec) references tecnicos(idtec)

);
    
drop table servicos;

describe servicos;

select * from servicos;		

insert into servicos (marca,modelo,serie,acessorios,observacao,DefeitoInformado,DefeitoDiagnosticado,statusOS,valor,formaPagamento,saida,dataOS,idcli,idtec,usuario,tecnicos)
values ('Notebook LeNovo G20','pc,notebook','1234','cpu, monitor','na bancada','não liga','queimado','aguardadando','123456789''credito,debito,pix','data','123456789','1','1','Mariana','André');


insert into servicos (os,marca,modelo,equipamentos,serie,observacao,DefeitoInformado,DefeitoDiagnosticado,statusOS,valor,formaPagamento,saida,dataOS,idcli,idtec,usuario,tecnicos)
values ('12345','Notebook LeNovo G20''cpu, monitor','1234','na bancada','não liga','queimado','aguardadando','123456789''credito,debito,pix','data','123456789','1','123456789','Mariana','André');



-- formatando a data (brasil)
-- date_format()
-- função para formatar a data
-- %d %m %a dd/mm/aaaa | %d%m%y dd/mm/aa | %H:%i HH:mm
 
select os,marca,modelo,acessorios,DefeitoInformado,DefeitoDiagnosticado,statusOs,valor,date_format(dataOS,'%d%m%y') as data_entrada,date_format(saida),idcli from servicos;

delete from clientes where idcli = 1;


/************* Relatórios (select) **************/

-- Faturamento
select sum(valor) as faturamento from servicos;

-- Clientes
select nome,Fone from clientes order by nome;
insert into clientes (nome,Fone)
values ('Mariana Mendes','99999-1234','0000-0000','bairro','uf','complemento');
insert into clientes (nome,Fone)
values ('Vanessa Mendes','555555-1234','0000-0000','bairro','uf','complemento');


/** pendências **/

-- formato 1 (aguardando técnico)
select * from servicos where statusOS = 'Aguardando técnico';

-- formato 2 (aguardando técnico)
select os,moto,defeito,
date_format(dataOS,'%d/%m/%Y - %H:%i') as data_entrada,
idcli from servicos
where statusOS = 'Aguardando técnico';

-- formato 3 final (aguardando técnico)
select servicos.os,servicos.modelo,servicos.DefeitoInformado,servicos.tecnicos,
date_format(servicos.dataOS,'%d/%m/%Y - %H:%i') as data_entrada,
clientes.nome as cliente, clientes.telefone
from servicos
inner join clientes
on servicos.idcli = clientes.idcli
where statusOS = 'Finalizado';

-- relatório de serviços entregues
select servicos.os,servicos.modelo,servicos.serie,servicos.DefeitoInformado,
servicos.DefeitoDiagnosticado,tecnicos.nome as tecnicos,
servicos.valor,
date_format(servicos.dataOS,'%d/%m/%Y - %H:%i') as data_entrada,
date_format(servicos.saida,'%d/%m/%Y') as data_saida,
clientes.nome as cliente, clientes.telefone
from servicos
inner join clientes
on servicos.idcli = clientes.idcli
inner join tecnicos
on servicos.idtec = tecnicos.idtec
where statusOS = 'Aguardando tecnico';

/** Impressão da OS **/
select servicos.os,
date_format(servicos.dataOS,'%d/%m/%Y - %H:%i') as data_entrada,
servicos.usuario as usuário,
servicos.modelo,servicos.serie,servicos.DefeitoInformado,servicos.statusOS as status_OS,servicos.DefeitoDiagnosticado,
tecnicos.nome as tecnicos,
servicos.valor,
date_format(servicos.saida,'%d/%m/%Y') as data_saida,
clientes.nome as cliente, clientes.telefone
from servicos
inner join clientes
on servicos.idcli = clientes.idcli
inner join tecnicos
on servicos.idtec = tecnicos.idtec
where os = 1;


-- Obtendo o valor do PK(ID) do último registro adcionado
select max(os) from servicos;
select max(idcli) from clientes; 

