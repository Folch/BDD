/******************************************************************
 *	make.sql de la subcarpeta functions                       *
 *		S'encarrega de fer crides als scripts pertinents, *
 *		Per a la creació o sustitució de funcions de      *
 *		triggers com d'altres.                            *
 ******************************************************************/


-- Crida al make de la subcarpeta 'consultes'
\i functions/consultes/make.sql

-- Crida al make de la subcarpeta 'trigger_functions'
\i functions/trigger_functions/make.sql
