--EntradesDisponibles(hotel,sala,jornada) = aforament(hotel,sala) - EntradesVenudes(hotel,sala,jornada)

CREATE OR REPLACE FUNCTION entrades_disponibles(text,text, int) RETURNS int AS $$
	DECLARE
		hotelInput ALIAS FOR $1;
		salaInput ALIAS FOR $2;
		jornadaInput ALIAS FOR $3;

		aforament int;
		numEntradesVenudes int;
	BEGIN
		aforament = (
			SELECT s.aforament 
			FROM hotel h INNER JOIN sala s ON h.nom = s.hotel
			WHERE (h.nom = hotelInput) AND (s.nom = salaInput)
		);

		numEntradesVenudes = (
			SELECT e.entradesVenudes
			FROM entrada e WHERE e.hotel = hotelInput
			AND e.sala = salaInput
			AND e.jornada = jornadaInput
		);

		IF (numEntradesVenudes IS NULL OR aforament IS NULL) THEN
			RETURN NULL;
		END IF;
		RETURN aforament-numEntradesVenudes;	
	END;
$$LANGUAGE plpgsql;


