\echo Insertant les taules fixes (hotels, sales, paisos, jornades)...
--Inserció de les taules fixes
\copy hotel from inserts/csv/hotel.csv delimiter ',' csv header
\copy sala from inserts/csv/sala.csv delimiter ',' csv header
\copy pais from inserts/csv/pais.csv delimiter ',' csv header
\copy jornada from inserts/csv/jornada.csv delimiter ',' csv header
\echo Insertades

-- Ho realitzem així per comoditat i per agrupació de dades.
-- A més permet cridar als triggers per cada registre alhora de fer el copy.
\echo Bolcant dades a la base de dades...
\copy persona from inserts/csv/persona.csv delimiter ',' csv header
\copy jugador from inserts/csv/jugador.csv delimiter ',' csv header
\copy jutge from inserts/csv/jutge.csv delimiter ',' csv header
\copy partida from inserts/csv/partida.csv delimiter ',' csv header
\echo Bolcat


