/******************************************************************
 *	make.sql de la subcarpeta trigger_functions               *
 *		S'encarrega de fer crides als scripts pertinents, *
 *		Per a la creació funcions cridades per triggers.  *
 ******************************************************************/

\i ../functions/trigger_functions/referee_control.sql
\i ../functions/trigger_functions/update_game_referee.sql
\i ../functions/trigger_functions/update_ticket.sql
