CREATE DATABASE registropersonas;

USE registropersonas;

CREATE TABLE PERSONAS(
	codigo_pers numeric(9) not null,
    cedula_pers numeric(10) not null,
    nombre_pers varchar(15) not null,
    fechaNac_pers varchar(10) not null,
    signoZod_pers varchar(10) not null
);

insert into personas
values
('202121385','1753650389','Melany Sangucho','02-03-2001','Aries'),
('202358453','1732518652','Dayana Sangucho','30-03-1999','Piscis'),
('202157684','1753084219','Juan Perez','05-06-1988','Tauro'),
('215484351','0984651358','Maria Zapata','03-08-1969','Acuario');

SELECT * FROM PERSONAS;