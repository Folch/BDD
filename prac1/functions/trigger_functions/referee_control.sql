--el que prohibirà que un àrbitre sigui de la mateixa nacionalitat que algun jugador de les partides que arbitra.

CREATE OR REPLACE FUNCTION referee_control() RETURNS TRIGGER AS $$
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
