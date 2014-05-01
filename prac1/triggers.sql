 
 /*
  Com a mínim són dos triggers. El que actualitzarà les victòries dels
àrbitres
*/


    
   
   
   CREATE OR REPLACE FUNCTION updateGameReferee() RETURNS TRIGGER AS $$
    DECLARE
      
      dniWinner text;
      dniBlanc text;
      jutgeTmp text;

    BEGIN
      

      
      
	--IF NOT FOUND THEN 

	  
	  dniWinner = (SELECT victoria FROM partida);
	  
	  dniBlanc = (SELECT jblanques FROM partida);
	  
	  
	  	
	  
		IF (dniWinner like dniBlanc) THEN

			IF (NEW.victoria is not null) then
				update jutge
				SET pblanques =+ 1
				where persona like jutge.persona;
			END IF;
		ELSE

			IF (NEW.victoria is not null) then
				update jutge
				SET pnegres =+ 1
				where persona like jutge.persona;
			END IF;
		END IF;
	
	 

	--END IF;
	RETURN NULL;
	END;
  $$LANGUAGE plpgsql;





--el que prohibirà que un àrbitre sigui de la mateixa nacionalitat que algun jugador de les partides que arbitra.

CREATE OR REPLACE FUNCTION refereeControl() RETURNS TRIGGER AS $$
DECLARE
	paisJutge text;
	paisPlayerBlanques text;
	paisPlayerNegres text;

BEGIN
		
	paisJutge = (
		select pais from persona where dni = NEW.jutge

		/*
		SELECT pa.nom 
		FROM pais pa INNER JOIN persona pe ON (pa.nom = pe.pais)
		INNER JOIN  jutge j ON pe.dni = j.persona*/
		);

	paisPlayerBlanques = (
		SELECT pa.nom
		FROM pais pa INNER JOIN persona pe ON (pa.nom = pe.pais)
		INNER JOIN  jugador j ON pe.dni = j.persona 
		AND j.persona = NEW.jblanques
	);

	paisPlayerNegres = (
		SELECT pa.nom
		FROM pais pa INNER JOIN persona pe ON (pa.nom = pe.pais)
		INNER JOIN  jugador j ON pe.dni = j.persona 
		AND j.persona = NEW.jnegres
		
	);
	
	IF ((paisJutge <> paisPlayerBlanques) AND (paisJutge <> paisPlayerNegres)) THEN
		RETURN NEW;--Si el jutge es de nacionalitat diferent als 2 jugadors, es crea la partida
	ELSE
		RAISE NOTICE 'Partida: %.% no creada',NEW.jornada, NEW.id;
		RAISE NOTICE 'PARTIDA NO CREADA: El jutge es del mateix pais (%) que el jugador blanc (%) i/o del jugador negre (%)',paisJutge,paisPlayerBlanques,paisPlayerNegres;
		RETURN NULL;
	END IF;
	
END;
$$LANGUAGE plpgsql;


--EntradesDisponibles(hotel,sala,jornada) = aforament(hotel,sala) - EntradesVenudes(hotel,sala,jornada)


CREATE OR REPLACE FUNCTION entrades_disponibles(text,text, int) RETURNS int AS $$
DECLARE
	hotelInput ALIAS FOR $1;
	salaInput ALIAS FOR $2;
	jornadaInput ALIAS FOR $3;

	aforament int;
	numEntradesVenudes int;
	
BEGIN

	aforament = (
	SELECT s.aforament 
	FROM hotel h INNER JOIN sala s ON h.nom = s.hotel
	WHERE (h.nom = hotelInput) AND (s.nom = salaInput)
	);

	numEntradesVenudes = (
	SELECT e.entradesVenudes
	FROM entrada e INNER JOIN sala s ON e.sala = s.nom
	INNER JOIN hotel h ON e.hotel = h.nom
	INNER JOIN jornada j ON e.jornada = j.id 
	);
	
	return aforament-numEntradesVenudes;	
		
	
END;
$$LANGUAGE plpgsql;


--TRIGGERS

CREATE TRIGGER updateReferee 
    AFTER UPDATE
    ON partida FOR EACH ROW
    EXECUTE PROCEDURE updateGameReferee();

CREATE TRIGGER refereeControl 
    BEFORE INSERT
    ON partida FOR EACH ROW
    EXECUTE PROCEDURE refereeControl();


