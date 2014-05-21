/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.People;

import Model.DataBase;
import Model.Game;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author albert
 */
public class Referee extends Anonymous {

    private String dni;

    public Referee(DataBase database, String dni) {
        super(database);
        this.dni = dni;
    }

    @Override
    public void show() {
        String opText;
        int op = -1;
        while (op != 5) {
            super.showMenu();
            System.out.println("\t3-Partides que jutjaré");
            System.out.println("\t4-Marcar guanyador");//s'han de poder introduir els moviments
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
                        queryMyGames();
                        break;
                    case 4:
                        queryMyGames();
                        boolean correcte = false;
                        while (!correcte) {
                            System.out.println("Jornada: ");
                            String jornadaT = sc.nextLine();
                            System.out.println("Id partida: ");
                            String idPartidaT = sc.nextLine();
                            try {
                                int jornada = Integer.parseInt(jornadaT);
                                int idPartida = Integer.parseInt(idPartidaT);
                                Game g = database.getGame(jornada, idPartida);
                                if (g != null) {
                                    closeGame(jornada, idPartida);
                                    correcte = true;
                                } else {
                                    correcte = false;
                                }

                            } catch (NumberFormatException er) {
                                correcte = false;
                                System.err.println("La jornada i el id de la partida"
                                        + "han de ser números enters existents");
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

    private void queryMyGames() {
        database.queryRefereeGames(dni);
    }

    private void closeGame(int jornada, int partida) {
        boolean correcte = false;
        while (!correcte) {
            System.out.println("Vols introduir els moviments? per terminal o per fitxer?\n "
                    + "Entra 'n' si es que no, 't' si es que sí i per terminal i f si es que sí i per fitxer[n/c/f]");
            String op = sc.nextLine().toLowerCase();
            if (op.equals("n")) {
                correcte = true;
            } else if (op.equals("t")) {
                ArrayList<String[]> moviments = new ArrayList<>();
                boolean mesTirades = false;
                while (!mesTirades) {
                    System.out.println("Tirada(0 per parar): ");
                    String tirada = sc.nextLine();
                    if (tirada.equals("0")) {
                        break;
                    }
                    System.out.println("Time en format aaaa-mm-dd hh:mm:ss(0 per parar): ");
                    String time = sc.nextLine();
                    if (time.equals("0")) {
                        break;
                    }
                    System.out.println("Color en format B/N(0 per parar): ");
                    String color = sc.nextLine().toUpperCase();
                    if (color.equals("0")) {
                        break;
                    }
                    String[] moviment = {tirada, time, color};
                    moviments.add(moviment);
                }
                correcte = database.setMoviments(moviments, jornada, partida);

            } else if (op.equals("f")) {
                try {
                    System.out.println("Avís: el fitxer ha d'estar en el següent format:"
                            + "tirada" + " tabulador " + "time(aaaa-mm-dd hh:mm:ss)" + " tabulador " + " color(N/B)");
                    System.out.println("Path del fitxer (per exemple: home/albert/Escritorio/moviments.txt): ");
                    String path = sc.nextLine();
                    ArrayList<String[]> moviments = getMovements(path);
                    correcte = database.setMoviments(moviments, jornada, partida);

                } catch (IOException ex) {
                    System.err.println("No s'ha pogut llegir el fitxer");
                    correcte = false;
                }
                

            } else {
                System.err.println("Escull una altra opció");
            }
        }

    }

    private ArrayList<String[]> getMovements(String path) throws IOException {
        Path p = Paths.get(path);
        String[] moviments = new String(Files.readAllBytes(p)).split("\n");
        ArrayList<String[]> moves = new ArrayList<>();
        System.out.println("Número de moviments: " + moviments.length);
        for (String movi : moviments) {
            String[] tirada = movi.split("\t");
            String[] moviment = {tirada[0], tirada[1], tirada[2].toUpperCase()};
            moves.add(moviment);
        }
        return moves;
    }

}
