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
				INNER JOIN  jugador j ON pe.dni = j.dni 
				AND j.dni = NEW.jblanques
			);

			paisPlayerNegres = (
				SELECT pa.nom
				FROM pais pa INNER JOIN persona pe ON (pa.nom = pe.pais)
				INNER JOIN  jugador j ON pe.dni = j.dni 
				AND j.dni = NEW.jnegres
		
			);
	
			IF ((paisJutge <> paisPlayerBlanques) AND (paisJutge <> paisPlayerNegres)) THEN
				RETURN NEW;--Si el jutge es de nacionalitat diferent als 2 jugadors, es crea la partida
			ELSE
				-- Agafem un jutge aleatori que no sigui del pais ni del de negre, ni del de blanca.
				NEW.jutge = (SELECT j.dni FROM jutge j 
							INNER JOIN persona p ON (p.dni = j.dni) 
							WHERE pais <> paisPlayerBlanques AND pais <> paisPlayerNegres ORDER BY random() LIMIT 1
				);
				RAISE NOTICE 'PARTIDA CONFLICTIVA: El jutge es del mateix pais (%) que el jugador blanc (%) i/o del jugador negre (%)',paisJutge,paisPlayerBlanques,paisPlayerNegres;
				RAISE NOTICE 'Canviem el jutge per el jutge amb dni: %', NEW.jutge;
				
				RETURN NEW;
			END IF;
		END IF;	
	
	
	END;
$$LANGUAGE plpgsql;
