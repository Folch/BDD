CREATE OR REPLACE FUNCTION arbitre_taules() RETURNS TABLE(nom text, dni text) AS $$
	DECLARE
	BEGIN
		RETURN QUERY (select p.nom,p.dni from jutge j,persona p where j.dni = p.dni order by ptaules desc limit 1);
			
	END;
$$LANGUAGE plpgsql;
