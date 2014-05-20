/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.People;

import Model.DataBase;
import Model.Game;

/**
 *
 * @author albert
 */
public class Organizer extends Anonymous {

    public Organizer(DataBase database) {
        super(database);
    }

    @Override
    public void show() {
        String opText;
        int op = -1;
        while (op != 5) {
            super.showMenu();
            System.out.println("\t3-Planificar una partida nova");
            System.out.println("\t4-Canviar la planificació d'una partida existent");
            System.out.println("\t5-Sortir");
            System.out.print("Escull una opció: ");
            opText = sc.nextLine();
            try {
                op = Integer.parseInt(opText);

                switch (op) {
                    case 1://1-Mostrar partides d'una jornada
                        showJornades();
                        break;
                    case 2://2-Mostrar classificació
                        showPlayers();
                        break;
                    case 3:
                        planNewGame();
                        break;
                    case 4:
                        String j,idP;
                        int jornada,idPartida;
                        boolean correcte = false;
                        while (!correcte) {
                            System.out.println("Entra la jornada de la partida a modificar: ");
                            j = sc.nextLine();
                            System.out.println("Entra la partida:");
                            idP = sc.nextLine();

                            try {
                                jornada = Integer.parseInt(j);
                                idPartida = Integer.parseInt(idP);
                                correcte = planExistingGame(jornada, idPartida);
                            } catch (NumberFormatException e) {
                                correcte = false;
                                System.err.println("Error en la jornada i/o partida");
                            }
                        }
                        break;
                    case 5:
                        break;
                    default:
                        System.err.println("Escull una altra opció");
                }
            } catch (NumberFormatException e) {
                op = -1;
                System.err.println("Escull una altra opció");
            }

        }
    }

    private boolean planExistingGame(int jornada, int idPartida) {
        Game g =  database.getGame(jornada,idPartida);
        
        int op = -1;
        System.out.println("Modificacions");
        System.out.println("Jornada");
        return true;
        
    }

    private boolean planNewGame() {
return true;
    }

}
