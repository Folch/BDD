CREATE OR REPLACE FUNCTION update_game_referee() RETURNS TRIGGER AS $$
	DECLARE
		dniWinner text;
		dniBlanc text;
	BEGIN

		dniWinner = NEW.victoria;
		dniBlanc = NEW.jblanques;

		IF (dniWinner like dniBlanc) THEN
			IF (NEW.victoria is not null) then
				update jutge
				SET pblanques = pblanques + 1
				where new.jutge = jutge.dni;
			END IF;
		ELSE

			IF (NEW.victoria is not null) then
				update jutge
				SET pnegres =pnegres + 1
				where new.jutge = jutge.dni;
			END IF;
		END IF;
		RETURN NULL;
	END;
$$LANGUAGE plpgsql;
