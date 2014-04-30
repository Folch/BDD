\SET ON_ERROR_STOP ON --Quan troba un error para
\SET CLIENT_MIN_MESSAGE TO ERROR

\i drops.sql -- Borrem les taules

\echo Creant querylog.txt per registrar les queries...
\o querylog.txt
\echo querylog.txt creat.

\ir taules.sql

