/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.DataBase;
import Controller.Controller;
import java.util.Scanner;

/**
 *
 * @author albert
 */
public class ChessTournament {

    /**
     * @param args the command line arguments
     */
    private static Scanner sc;

    public static void main(String[] args) {
        // TODO code application logic here
        sc = new Scanner(System.in);
        System.out.println("Benvingut al campionat d'escacs!");
        int op = -1;
        while (op != 2) {
            op = showOptions();
            switch (op) {
                case 1:
                    login();
                    break;
                case 2:
                    break;
                default:
                    System.err.println("Escull una altra opció");
            }

        }

    }

    private static int showOptions() {
        System.out.println("Opcions");
        System.out.println("\n\t1-Login");
        System.out.println("\t2-Sortir");
        System.out.print("Escull una opció: ");
        String opText = sc.nextLine();
        int op = -1;
        try {
            op = Integer.parseInt(opText);
        } catch (NumberFormatException n) {
        }
        return op;
    }

    private static int login() {

        System.out.println("Login");
        /*System.out.println("Entra el teu nom d'usuari: ");
         String userName = sc.nextLine();
         System.out.println("Entra el teu password: ");
         String password = sc.nextLine();*/
        String userName = "xaviml93";
        String password = "xavi.93";
        DataBase db = new DataBase(userName, password);
        Controller controller = new Controller(db);
        int opcio = -1;
        while (opcio != 3) {
            System.out.println("\nOpcions");
            System.out.println("\n\t1-Entrar com un usuari logat");
            System.out.println("\t2-Entrar com a usuari anònim");
            System.out.println("\t3-Sortir");
            System.out.print("Escull una opció: ");
            String opText = sc.nextLine();
            try {
                opcio = Integer.parseInt(opText);
                if (opcio != 3) {
                    controller.manageOption(opcio);
                }
            } catch (NumberFormatException n) {
                System.err.println("Opció incorrecta");
            }
        }
        return 0;
    }

}
