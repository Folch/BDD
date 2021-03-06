\echo Creant la base de dades...
CREATE TABLE hotel ( 
	nom text,
	direccio text NOT NULL,

	--Constraints
	--nom (PK)
	CONSTRAINT clau_principal_nomhotel PRIMARY KEY (nom)
);

CREATE TABLE sala ( 
	nom text,
	hotel text NOT NULL,
	aforament int DEFAULT 0,
	
	--Constraints
	--nom (PK)
	CONSTRAINT clau_principal_nomsala_hotel PRIMARY KEY(nom, hotel),

	--hotel
	CONSTRAINT clau_foranea_hotel FOREIGN KEY (hotel) REFERENCES hotel(nom) ON DELETE CASCADE,

	--aforament
	CONSTRAINT aforament_invalid CHECK (aforament >= 0)
);

CREATE TABLE jornada (
	id int,
	datainici date, --Només contemplo els dies, no les hores, ni minuts, ni segons.
	datafi date,

	--Constraints
	--id (PK)
	CONSTRAINT clau_principal_idjornada PRIMARY KEY(id),
	
	--data*, comprovació que la datafi sigui major o igual que la datainici
	CONSTRAINT dades_incongruents CHECK (datainici <= datafi)
	
);
--Com no ens guardem qui compra ens guardem una id de la entrada, que serà autoincremental,
--per tenir control de cada entrada.
CREATE TABLE entrada (
	
	entradesvenudes int DEFAULT 0,
	jornada int,
	sala text,
	hotel text,
	
	--Constraints
	--entradesvenudes
	CONSTRAINT entradesvenudes_invalid CHECK (entradesvenudes >= 0),

	--jornada
	CONSTRAINT clau_foranea_jornada FOREIGN KEY (jornada) REFERENCES jornada(id),
	
	--(sala, hotel)
	CONSTRAINT clau_foranea_sala_hotel FOREIGN KEY (sala, hotel) REFERENCES sala(nom, hotel),

	--(jornada, sala, hotel) (PK)
	CONSTRAINT clau_principal_jornada_sala_hotel PRIMARY KEY (jornada, sala, hotel)
);

CREATE TABLE pais (
	nom text,
	capital text,

	--Constraints
	--nom (PK)
	CONSTRAINT clau_principal_nompais PRIMARY KEY(nom)
);

CREATE TABLE persona (
	dni text,
	nom text NOT NULL, 
	sexe gender,
	hotel text,
	pais text REFERENCES pais(nom),

	--Constraints
	--dni (PK)
	CONSTRAINT clau_principal_dni PRIMARY KEY(dni),

	--hotel
	CONSTRAINT clau_foranea_hotel FOREIGN KEY (hotel) REFERENCES hotel(nom),

	--pais
	CONSTRAINT clau_foranea_pais FOREIGN KEY (pais) REFERENCES pais(nom)
);

CREATE TABLE jugador (
	edat int,
	dni text,
	
	--Constraints
	--edat
	CONSTRAINT edat_invalid CHECK (edat >= 0),

	--persona (PK) - Herència:
	CONSTRAINT clau_foranea_personajugador FOREIGN KEY (dni) REFERENCES persona(dni),
	CONSTRAINT clau_principal_personajugador PRIMARY KEY(dni)
);

CREATE TABLE jutge ( 
	pblanques int DEFAULT 0,
	pnegres int DEFAULT 0,
	ptaules int DEFAULT 0,
	dni text,
		
	--Constraints
	--pblanques
	CONSTRAINT pblanques_invalid CHECK (pblanques >= 0),

	--pnegres
	CONSTRAINT pnegres_invalid CHECK (pnegres >= 0),

	--ptaules
	CONSTRAINT ptaules_invalid CHECK (ptaules >= 0),

	--persona (PK) - Herència:
	CONSTRAINT clau_foranea_personajutge FOREIGN KEY (dni) REFERENCES persona(dni),
	CONSTRAINT clau_principal_personajutge PRIMARY KEY(dni)
);

CREATE TABLE partida (
	id int,
	jblanques text,
	jnegres text,
	victoria text,
	jutge text,
	jornada int,
	sala text,
	hotel text,

	--Constraints
	--(jornada, id) (PK)
	CONSTRAINT id_invalid CHECK (id >= 1), -- La primera partida ha de tenir id = 1
	CONSTRAINT clau_foranea_jornada FOREIGN KEY (jornada) REFERENCES jornada(id),
	CONSTRAINT clau_principal_jornada_id PRIMARY KEY(jornada, id),

	--jblanques
	CONSTRAINT clau_foranea_jblanques FOREIGN KEY (jblanques) REFERENCES persona(dni),

	--jnegres
	CONSTRAINT clau_foranea_jnegres FOREIGN KEY (jnegres) REFERENCES persona(dni),

	--victoria
	CONSTRAINT clau_foranea_victoria FOREIGN KEY (victoria) REFERENCES persona(dni),

	--jutge
	CONSTRAINT clau_foranea_jutge FOREIGN KEY (jutge) REFERENCES persona(dni),

	--(sala, hotel)
	CONSTRAINT clau_foranea_sala_hotel FOREIGN KEY (sala, hotel) REFERENCES sala(nom, hotel)
);

CREATE TABLE moviment (
	jornada int,
	partida int,
	tirada text NOT NULL,
	time timestamp,
	color color NOT NULL,

	--Constraints
	--id (PK)
	CONSTRAINT clau_principal_partida_time PRIMARY KEY(jornada,partida, time),
	
	--(jornada,partida)
	CONSTRAINT clau_foranea_jornada_partida FOREIGN KEY(jornada, partida) REFERENCES partida(jornada, id)
);
\echo Base de dades creada.
