CREATE VIEW v_taquillers AS

SELECT h.nom as Hotel,h.direccio,s.nom as Sala,(s.aforament - e.entradesvenudes) as Entrades_disponibles, j.id AS jornada,j.datainici,j.datafi,p.id as Id_partida,p.jblanques AS Jugador_blanques,p.jnegres AS Jugador_negres,p.victoria,p.jutge
FROM hotel h INNER JOIN sala s ON (h.nom = s.hotel) 
INNER JOIN entrada e ON (e.sala = s.nom AND e.hotel = h.nom) 
INNER JOIN jornada j ON (e.jornada = j.id) 
INNER JOIN partida p ON (p.jornada = j.id AND p.hotel = h.nom AND p.sala = s.nom)
ORDER BY jornada;
