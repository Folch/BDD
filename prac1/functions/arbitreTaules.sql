CREATE OR REPLACE FUNCTION arbitreTaules() RETURNS TABLE(nom text, dni text) AS $$
	DECLARE
	BEGIN

		RETURN QUERY (select p.nom,p.dni from jutge,persona p where persona = p.dni order by ptaules desc limit 1);
			
	END;
$$LANGUAGE plpgsql;
