/******************************************************************
 *	make.sql de la subcarpeta delete                          *
 *		S'encarrega de fer crides als scripts pertinents, *
 *		Per a la eliminació de taules i tipus de dades.   *
 ******************************************************************/

-- Eliminem en l'ordre invers que insertem

\i ../delete/delete_triggers.sql
\i ../delete/delete_functions.sql
\i ../delete/delete_taules.sql
\i ../delete/delete_enums.sql


