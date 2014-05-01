 
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
	--si el jutgeee usasundels 3 es null, pots afegir

	IF (NEW.jblanques IS NULL OR NEW.jnegres IS NULL OR NEW.jutge IS NULL) THEN
		RETURN NEW;
	ELSE
		paisJutge = (
			select pais from persona where dni = NEW.jutge
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
	IF (numEntradesVenudes IS NULL) THEN
		numEntradesVenudes = 0;
	END IF;
	
	return aforament-numEntradesVenudes;	
		
	
END;
$$LANGUAGE plpgsql;

--dESPRES DAFEGIR UNA PARTIDA, AFEGIR UN REGISTRE ENTRADA EN CAS QUE NO EXISTEIXI
CREATE OR REPLACE FUNCTION updateTicket() RETURNS TRIGGER AS $$
DECLARE
	ticket int;

BEGIN
	--ACONSEGUIR L'ENTRADA CORRESPONENT A LA PARTIDA
	--SI ES NULL->CREARLA
	--SI JA EXISTEIX->RES
	ticket = (
	SELECT e.entradesvenudes
	from entrada e 
	where (e.jornada = NEW.jornada AND
	e.sala = NEW.sala AND e.hotel = NEW.hotel)
	limit 1);




	IF ticket IS NULL THEN 
		
		INSERT INTO entrada(jornada,sala,hotel)
		VALUES (NEW.jornada,NEW.sala,NEW.hotel);
	END IF;
	RETURN NULL;
		
	
END;
$$LANGUAGE plpgsql;



--TRIGGERS

CREATE TRIGGER updateReferee 
AFTER UPDATE
ON partida FOR EACH ROW
EXECUTE PROCEDURE updateGameReferee();

CREATE TRIGGER refereeControl 
BEFORE INSERT OR UPDATE
ON partida FOR EACH ROW
EXECUTE PROCEDURE refereeControl();

CREATE TRIGGER updateTicket
AFTER INSERT
ON partida FOR EACH ROW
EXECUTE PROCEDURE updateTicket();




