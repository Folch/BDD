CREATE OR REPLACE FUNCTION guanyador_pais(paisEntrada text) RETURNS TABLE(dni text,nom text,partidesGuanyades bigint) AS $$
	BEGIN
		RETURN QUERY(
			SELECT pe.dni,pe.nom,count(p.victoria) AS partidesGuanyades 
			FROM partida p INNER JOIN jugador j ON (p.victoria = j.dni) 
					INNER JOIN persona pe ON (pe.dni = j.dni) 
					INNER JOIN pais pa ON (pe.pais = pa.nom)
			WHERE pa.nom = paisEntrada 
			GROUP BY pa.nom , pe.dni,pe.nom
			ORDER BY partidesGuanyades DESC 
			LIMIT 1
		);
	END;
$$LANGUAGE plpgsql;
