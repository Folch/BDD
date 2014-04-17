\echo inicialitzant la base de dades...
CREATE TABLE hotel ( 
	id SERIAL PRIMARY KEY,
	nom text,
	direccio text
);

CREATE TABLE sala ( 
	id SERIAL PRIMARY KEY,
	hotel int REFERENCES hotel(id),
	aforament int
);

CREATE TABLE jornada (
	id SERIAL PRIMARY KEY,
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
	pais text REFERENCES pais(nom)
);

CREATE TABLE jugador ( 
	hotel int REFERENCES hotel(id)
) INHERITS (persona);

CREATE TABLE jutge ( 
	identificador int UNIQUE
) INHERITS (persona);

CREATE TABLE partida (
	id SERIAL PRIMARY KEY,
	jblancas text REFERENCES persona(dni),
	jnegres text REFERENCES persona(dni),
	victoria char,
	jutge int REFERENCES jutge(identificador),
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
