
CREATE OR REPLACE FUNCTION guanyadors() RETURNS TABLE(nom text,victoria text,partidesGuanyades bigint) AS $$
	DECLARE

	BEGIN
		RETURN QUERY(
		SELECT pe.nom,p.victoria,count(p.victoria) AS partidesGuanyades 
		FROM partida p, persona pe
		where pe.dni = p.victoria
		GROUP BY p.victoria , pe.nom
		ORDER BY partidesGuanyades DESC 
		limit 3);
			
	END;
$$LANGUAGE plpgsql;


