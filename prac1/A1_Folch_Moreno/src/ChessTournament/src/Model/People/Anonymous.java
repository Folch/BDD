/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.People;

import Model.DataBase;
import java.util.Scanner;

/**
 *
 * @author albert
 */
public class Anonymous {

    protected final DataBase database;
    protected Scanner sc;

    public Anonymous(DataBase database) {
        this.database = database;
        sc = new Scanner(System.in);
    }

    public void show() {
        String opText;
        int op = -1;
        while (op != 3) {
            showMenu();
            System.out.println("\t3-Sortir");
            System.out.print("Escull una opció: ");
            opText = sc.nextLine();
            try {
                op = Integer.parseInt(opText);

                switch (op) {
                    case 1:
                        showJornades();
                        break;
                    case 2:
                        showPlayers();
                        break;
                    case 3:
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

    protected void showGames(int jornada) {
        database.queryGames(jornada);
    }

    protected void showPlayers() {
        database.queryPlayers();
    }

    protected void showJornades() {

        int jornada;
        String opText = "";
        while (!opText.equals("0")) {
            database.queryJornades();
            System.out.println("\n\nEscull la jornada (0 per cancel·lar): ");
            opText = sc.nextLine();
            try {
                jornada = Integer.parseInt(opText);
                showGames(jornada);
            } catch (NumberFormatException e) {
                System.err.println("La jornada ha de ser un nombre enter");
            }

        }

    }

    protected void showMenu() {
        System.out.println("Opcions:");
        System.out.println("\n\t1-Mostrar partides d'una jornada");
        System.out.println("\t2-Mostrar classificació");
    }
}
