--Quan troba un error para
\set ON_ERROR_STOP 'on'
--\set CLIENT_MIN_MESSAGE to WARNING

-- Borrem les taules
\i drops.sql

\echo Creant querylog.txt per registrar les queries...
\o querylog.txt
\echo querylog.txt creat.

\ir taules.sql

\ir inserts/insert_csv.sql

/*
EntradesDisponibles(sala, jornada) = aforament(sala) - sumatori(EntradesVenudes(partida), per a cada partida)

*/

