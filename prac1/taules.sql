\echo Creant la base de dades...
CREATE TABLE hotel ( 
	nom text PRIMARY KEY,
	direccio text
);

CREATE TABLE sala ( 
	nom text,
	hotel text REFERENCES hotel(nom),
	aforament int,
	PRIMARY KEY(nom, hotel)
);

CREATE TABLE jornada (
	id int PRIMARY KEY,
	datainici timestamp, 
	datafi timestamp
);
/*
CREATE TABLE entrada (
	--Com no ens guardem qui compra ens guardem una id de la entrada, que serà autoincremental,
	--per tenir control de cada entrada.
	id SERIAL PRIMARY KEY,
	jornada int REFERENCES jornada(id),
	sala text,
	hotel text,
	FOREIGN KEY (sala, hotel) REFERENCES sala(nom, hotel)
);*/

CREATE TABLE pais ( 
	nom text PRIMARY KEY
);

CREATE TABLE persona ( 
	dni text PRIMARY KEY,
	nom text, 
	sexe char,
	hotel text REFERENCES hotel(nom),
	pais text REFERENCES pais(nom)
);

CREATE TABLE jugador (
	edat int,
	--Herència:
	persona text REFERENCES persona(dni) PRIMARY KEY
);

CREATE TABLE jutge ( 
	pblanques int DEFAULT 0,
	pnegres int DEFAULT 0,
	ptaules int DEFAULT 0,
	--Herència:
	persona text REFERENCES persona(dni) PRIMARY KEY
);

CREATE TABLE partida (
	id int PRIMARY KEY,
	jblanques text REFERENCES persona(dni),
	jnegres text REFERENCES persona(dni),
	victoria text REFERENCES persona(dni),
	jutge text REFERENCES persona(dni),
	jornada int REFERENCES jornada(id),
	sala text,
	hotel text,
	FOREIGN KEY (sala, hotel) REFERENCES sala(nom, hotel)
);

CREATE TABLE moviment (
	id SERIAL PRIMARY KEY,
	partida int REFERENCES partida(id),
	color char,
	pesa char,
	posOr text, /*posició de la peça*/
	posDe text  /*posició a la qual va a parar la peça*/
);
\echo Base de dades creada.
