CREATE OR REPLACE FUNCTION guanyadors() RETURNS TABLE(nom text,victoria text,partidesGuanyades bigint) AS $$
	BEGIN
		RETURN QUERY(
			SELECT pe.nom,p.victoria,count(p.victoria) AS partidesGuanyades 
			FROM partida p, persona pe
			WHERE pe.dni = p.victoria
			GROUP BY p.victoria , pe.nom
			ORDER BY partidesGuanyades DESC 
			LIMIT 3
		);
			
	END;
$$LANGUAGE plpgsql;


