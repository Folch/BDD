/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.People;

import Model.DataBase;

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
        while (op != 4) {
            super.showMenu();
            System.out.println("\t3-Planificar partides");
            System.out.println("\t4-Sortir");
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
                        planGame();
                        break;
                    case 4:
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
    private void planGame(){
        
    }
    private void planExistingGame(int jornada, int idPartida) {

    }

    private void planNewGame() {

    }

}
