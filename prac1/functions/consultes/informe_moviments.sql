CREATE OR REPLACE FUNCTION informe_moviments(jornadainput int, partidainput int) RETURNS text AS $$
	DECLARE
		r moviment%rowtype;
		out text;
	BEGIN
		out = '';
		FOR r IN SELECT * FROM moviment m
		WHERE m.partida = partidainput AND m.jornada = jornadainput ORDER BY m.time ASC
		LOOP
			out = out || r.tirada || '\n';
		END LOOP;
		RETURN out;
	END;
$$LANGUAGE plpgsql;


