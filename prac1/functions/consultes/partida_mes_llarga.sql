CREATE OR REPLACE FUNCTION partida_mes_llarga() RETURNS TABLE(jornada int, partida int, duration interval) AS $$
	BEGIN
		RETURN QUERY(
			SELECT m1.jornada, m1.partida, m1.time-m2.time
				FROM moviment m1, moviment m2 
				WHERE m1.time = (SELECT m3.time 
						FROM moviment m3 
						WHERE m3.partida=m1.partida AND m3.jornada=m1.jornada
						ORDER BY time DESC limit 1)
					AND m2.time = (SELECT m4.time 
						FROM moviment m4 
						WHERE m4.partida=m2.partida AND m4.jornada=m2.jornada
						ORDER BY time ASC limit 1)
					AND m1.jornada = m2.jornada 
					AND m1.partida = m2.partida
				ORDER BY m1.time-m2.time DESC
				LIMIT 1
			);
	END;
$$LANGUAGE plpgsql;


