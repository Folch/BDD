CREATE OR REPLACE FUNCTION informe_moviments(jornadainput int, partidainput int) RETURNS TABLE(t text) AS $$
	BEGIN
		RETURN QUERY(
			SELECT tirada
			FROM moviment
			WHERE jornada=jornadainput AND partida=partidainput
		);
			
	END;
$$LANGUAGE plpgsql;


