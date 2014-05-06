/******************************************************************
 *	make.sql de la subcarpeta functions                       *
 *		S'encarrega de fer crides als scripts pertinents, *
 *		Per a la creació o sustitució de funcions de      *
 *		triggers com d'altres.                            *
 ******************************************************************/

\i functions/entrades_disponibles.sql
\i functions/guanyadors.sql
\i functions/guanyadorPais.sql
\i functions/arbitreBlanc.sql
\i functions/arbitreNegre.sql
\i functions/arbitreTaules.sql
\i functions/partida_mes_curta.sql
\i functions/partida_mes_llarga.sql

-- Crida al make de la subcarpeta 'trigger_functions.sql'
\i functions/trigger_functions/make.sql
