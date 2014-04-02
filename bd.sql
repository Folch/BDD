\echo Iniciant la execució de les comanes

drop table if exists pais cascade;
create table pais ( 
	nom text PRIMARY KEY,
	habitants integer
);

drop table if exists persona;
create table persona (
	nom text PRIMARY KEY,
	dni text,
	pais text REFERENCES pais(nom)
);

insert into pais values('Canada', 38);
insert into pais values('Portugal', 15);
insert into pais values('Brasil',50);

insert into persona values('Joan', '11111111A', 'Canada');
insert into persona values('Maria', '22222222B', 'Portugal');
insert into persona values('Gloria','33333333C','Brasil');
insert into persona valueS('Josep','44444444D','Brasil');
