CREATE OR REPLACE FUNCTION arbitreNegre() RETURNS TABLE(nom text, dni text) AS $$
	DECLARE
	BEGIN

		RETURN QUERY (select p.nom,p.dni from jutge,persona p where persona = p.dni order by pnegres desc limit 1);
			
	END;
$$LANGUAGE plpgsql;
