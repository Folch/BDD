--DESPRES DAFEGIR UNA PARTIDA, AFEGIR UN REGISTRE ENTRADA EN CAS QUE NO EXISTEIXI
CREATE OR REPLACE FUNCTION update_ticket() RETURNS TRIGGER AS $$
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
