--Quan troba un error para
\set ON_ERROR_STOP 'on'
\set CLIENT_MIN_MESSAGE to notice

-- Borrem les taules
\i ../delete/make.sql

-- Creació de taules i enums
\i ../create/make.sql

-- Creació de funcions
\i ../functions/make.sql

-- Creació dels triggers
\i ../triggers.sql

-- Inserció a la base de dades
\i ../insert/make.sql
