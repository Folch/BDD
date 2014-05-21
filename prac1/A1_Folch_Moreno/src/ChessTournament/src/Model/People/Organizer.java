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
                        planExistingGame(-1, -1,false);
                        break;
                    case 4:
                        String j,
                         idP;
                        int jornada,
                         idPartida;
                        boolean correcte = false;
                        while (!correcte) {
                            System.out.println("Entra la jornada de la partida a modificar: ");
                            j = sc.nextLine();
                            System.out.println("Entra la partida:");
                            idP = sc.nextLine();

                            try {
                                jornada = Integer.parseInt(j);
                                idPartida = Integer.parseInt(idP);
                                correcte = planExistingGame(jornada, idPartida,true);
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

    private boolean planExistingGame(int jornadaAntic, int idPartidaAntic, boolean existent) {
        Game g;
        if(existent)  {
            g = database.getGame(jornadaAntic, idPartidaAntic);
        }else{
            g = new Game();
        }
        String opText;
        int op = -1;
        while (op != 8) {
            if(existent) System.out.println("Modificacions");
            else System.out.println("Nova partida");
            System.out.println("1-Jornada(actual = " + g.getJornada() + ")");
            System.out.println("2-Id partida(actual = " + g.getId() + ")");
            System.out.println("3-Hotel(actual = " + g.getHotel() + ")");
            System.out.println("4-Jugador fitxes blanques(actual = " + g.getJblanques() + ")");
            System.out.println("5-Jugador fitxes negres(actual = " + g.getJnegres() + ")");
            System.out.println("6-Jutge(actual = " + g.getJutge() + ")");
            System.out.println("7-Sala(actual = " + g.getSala() + ")");
            System.out.println("8-Canviar de partida");
            System.out.println("9-Confirmar i sortir");
            opText = sc.nextLine();
            try {
                op = Integer.parseInt(opText);
                switch (op) {
                    case 1:
                        database.queryJornades();
                        System.out.println("Jornada: ");
                        String jornadaT = sc.nextLine();
                        boolean jornadaCorrecta = false;
                        while (!jornadaCorrecta) {
                            try {
                                int jornada = Integer.parseInt(jornadaT);
                                g.setJornada(jornada);
                                jornadaCorrecta = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Escull una altra jornada");
                            }
                        }

                        break;
                    case 2:
                        System.out.println("Id partida: ");
                        String idT = sc.nextLine();
                        boolean idCorrecte = false;
                        while (!idCorrecte) {
                            try {
                                int idPartida = Integer.parseInt(idT);
                                g.setId(idPartida);
                                idCorrecte = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Escull un altre id");
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Hotel: ");
                        String hotel = sc.nextLine();
                        g.setHotel(hotel);
                        break;
                    case 4:
                        System.out.println("Jugador fitxes blanques: ");
                        String jblanques = sc.nextLine();
                        g.setJblanques(jblanques);
                        break;
                    case 5:
                        System.out.println("Jugador fitxes negres: ");
                        String jnegres = sc.nextLine();
                        g.setJnegres(jnegres);
                        break;
                    case 6:
                        System.out.println("Jutge: ");
                        String jutge = sc.nextLine();
                        g.setJutge(jutge);
                        break;
                    case 7:
                        System.out.println("Sala: ");
                        String sala = sc.nextLine();
                        g.setSala(sala);
                        break;
                    case 8:
                        break;
                    case 9:
                        //retorna true si s'ha pogut actualitzar
                        if (database.updateGame(jornadaAntic, idPartidaAntic, g)) {
                            return true;
                        } else {//algun parametre esta malament
                            System.err.println("Error");
                            break;
                        }
                }
            } catch (NumberFormatException e) {
                op = -1;
                System.err.println("Escull una altra opció");
            }
        }

        return false;

    }

}
