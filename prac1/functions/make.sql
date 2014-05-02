/******************************************************************
 *	make.sql de la subcarpeta functions                       *
 *		S'encarrega de fer crides als scripts pertinents, *
 *		Per a la creació o sustitució de funcions de      *
 *		triggers com d'altres.                            *
 ******************************************************************/

\i functions/entrades_disponibles.sql

-- Crida al make de la subcarpeta 'trigger_functions.sql'
\i functions/trigger_functions/make.sql
