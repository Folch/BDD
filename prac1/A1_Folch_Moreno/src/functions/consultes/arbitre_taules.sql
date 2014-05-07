CREATE OR REPLACE FUNCTION arbitre_taules() RETURNS TABLE(nom text, dni text) AS $$
	DECLARE
	BEGIN
		RETURN QUERY (SELECT p.nom,p.dni
				FROM jutge j, persona p
				WHERE j.dni = p.dni
				ORDER BY ptaules DESC 
				LIMIT 1
		);
	END;
$$LANGUAGE plpgsql;
