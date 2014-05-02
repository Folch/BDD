CREATE OR REPLACE FUNCTION update_game_referee() RETURNS TRIGGER AS $$
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
		RETURN NULL;
	END;
$$LANGUAGE plpgsql;
