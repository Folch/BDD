/******************************************************************
 *	make.sql de la subcarpeta inserts                         *
 *		S'encarrega de fer crides als scripts pertinents, *
 *		Per a la inserció en copia de dades.              *
 ******************************************************************/

\echo Insertant les taules fixes (hotels, sales, paisos, jornades)...
--Inserció de les taules fixes
\copy hotel from insert/csv/hotel.csv delimiter ',' csv header
\copy sala from insert/csv/sala.csv delimiter ',' csv header
\copy pais from insert/csv/pais.csv delimiter ',' csv header
\copy jornada from insert/csv/jornada.csv delimiter ',' csv header
\echo Insertades

-- Ho realitzem així per comoditat i per agrupació de dades.
-- A més permet cridar als triggers per cada registre alhora de fer el copy.
\echo Bolcant dades a la base de dades...
\copy persona from insert/csv/persona.csv delimiter ',' csv header
\copy jugador from insert/csv/jugador.csv delimiter ',' csv header
\copy jutge from insert/csv/jutge.csv delimiter ',' csv header
\copy partida from insert/csv/partides/partida.csv delimiter ',' csv header


\copy moviment from insert/csv/partides/moviments.csv delimiter '.' csv header

\echo Bolcat
