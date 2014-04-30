\echo Creant la base de dades...
CREATE TABLE hotel ( 
	nom text PRIMARY KEY,
	direccio text
);

CREATE TABLE sala ( 
	id SERIAL PRIMARY KEY,
	nom text,
	hotel text REFERENCES hotel(nom),
	aforament int
);

CREATE TABLE jornada (
	id int PRIMARY KEY,
	data timestamp
);

CREATE TABLE entrada ( 
	id SERIAL PRIMARY KEY,
	jornada int REFERENCES jornada(id),
	sala int REFERENCES sala(id)
);

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
	edat int
) INHERITS (persona);

CREATE TABLE jutge ( 
	pblanques int,
	pnegres int, 
	ptaules int
) INHERITS (persona);

CREATE TABLE partida (
	id SERIAL PRIMARY KEY,
	jblanques text REFERENCES persona(dni),
	jnegres text REFERENCES persona(dni),
	victoria text REFERENCES persona(dni),
	jutge text REFERENCES persona(dni),
	jornada int REFERENCES jornada(id),
	sala int REFERENCES sala(id)
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
