CREATE OR REPLACE FUNCTION pais_guanyador() RETURNS TABLE(pais text, victories bigint) AS $$
	BEGIN
		RETURN QUERY(
			SELECT pa.nom, (SELECT count(g.victoria) 
					FROM partida g, persona pe 
					WHERE g.victoria = pe.dni 
					AND pe.pais = pa.nom) AS victories
				FROM pais pa
				GROUP BY pa.nom
				ORDER BY victories DESC
				LIMIT 1
		);
			
	END;
$$LANGUAGE plpgsql;
