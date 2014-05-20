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
public class Salesman extends Anonymous {


    public Salesman(DataBase database) {
        super(database);
    }

    @Override
    public void show() {
        String opText;
        int op = -1;
        while (op != 4) {
            super.showMenu();
            System.out.println("\t3-Vendre entrades");
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
                        sellTickets();
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

    private void sellTickets() {
        String opText;
        int op = -1;
        while (op != 2) {
            System.out.println("\t1-Vendre entrades");
            System.out.println("\t2-Sortir");
            System.out.print("Escull una opció: ");
            opText = sc.nextLine();
            try {
                op = Integer.parseInt(opText);

                switch (op) {
                    case 1:
                        chooseHotel();
                        break;
                    case 2:
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

    private void chooseHotel() {
        boolean correctHotel = false;
        while (!correctHotel) {

            String[] hotels = database.queryAllHotels();
            System.out.println("Escriu el número d'un hotel(0 per cancel·lar la compra): ");
            String nHotel = sc.nextLine();
            if(nHotel.equals("0"))return;
            try {
                int numHotel = Integer.parseInt(nHotel);
                String hotel = hotels[numHotel];
                boolean canviarHotel = chooseSala(hotel);
                correctHotel = !canviarHotel;
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                System.err.println("Escull un altre hotel");
            }
        }
    }

    private boolean chooseSala(String hotel) {
        boolean correctSala = false;
        while (!correctSala) {
            String[] sales = database.querySales(hotel);
            System.out.println("Escriu el número d'una sala(0 per canviar d'hotel): ");
            String nSala = sc.nextLine();
            if(nSala.equals("0"))return true;
            try {
                int numSala = Integer.parseInt(nSala);
                String sala = sales[numSala];
                boolean esVolCanviarDeSala = chooseJornada(hotel, sala);
                correctSala = !esVolCanviarDeSala;

            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                System.err.println("Escull un altre sala");
            }
        }
        return false;
    }

    private boolean chooseJornada(String hotel, String sala) {
    //retorna true si es vol canviar de sala i false si no 
        int op = -1;
        String opText;
        int jornadaEscollida = -1;
        while (op != 2) {
            System.out.println("Opcions:");
            System.out.println("\n\t1-Escollir jornada");
            System.out.println("\t2-Canviar de sala");
            System.out.println("\t3-Sortir");
            opText = sc.nextLine();
            try {
                op = Integer.parseInt(opText);
                switch (op) {
                    case 1:
                        boolean correctJornada = false;
                        while (!correctJornada) {
                            String[] jornades = database.queryJornades(hotel, sala);
                            System.out.println("Escull una jornada(0 per cancel·lar): ");
                            String nJornada = sc.nextLine();
                            if (nJornada.equals("0")) {
                                break;
                            }
                            try {
                                jornadaEscollida = Integer.parseInt(nJornada);
                                correctJornada = true;
                                boolean correctEntrades = false;

                                while (!correctEntrades) {
                                    try {
                                        System.out.println("Número d'entrades: ");
                                        String nEntrades = sc.nextLine();
                                        int numEntrades = Integer.parseInt(nEntrades);
                                        if (numEntrades < 1) {
                                            System.err.println("El número d'entrades ha de ser un enter major que 0");
                                            continue;
                                        }
                                        int entradesDisponibles = database.entradesDisponibles(hotel, sala, jornadaEscollida);
                                        if (entradesDisponibles < numEntrades) {
                                            System.err.println("Error: Només queden " + entradesDisponibles + " entrades per l'hotel  " + hotel + ", sala "
                                                    + sala + " i jornada: " + jornadaEscollida + ". Escull una altra jornada o "
                                                    + "cancel·la per canviar de sala");
                                            correctJornada = false;
                                            correctEntrades = false;
                                        } else {
                                            database.buyTicket(hotel, sala, jornadaEscollida, numEntrades);
                                            System.out.println("Comprades " + numEntrades + " entrades");
                                            if ((entradesDisponibles - numEntrades) > 0) {//si queden entrades encara
                                                System.out.println("Vols comprar més entrades per l'hotel  " + hotel + ", sala "
                                                        + sala + " i jornada: " + jornadaEscollida + " ?[s/n]");
                                                String resposta = sc.nextLine().toLowerCase();
                                                correctEntrades = !resposta.equals("s");
                                                correctJornada = true;
                                            } else {
                                                correctJornada = true;
                                                correctEntrades = true;
                                            }

                                        }

                                    } catch (NumberFormatException es) {
                                        System.err.println("Escriu un número d'entrades");
                                    }

                                }

                            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                                System.err.println("Escull una altra jornada");
                            }
                        }
                        break;
                    case 2:
                        return true;
                    case 3:
                        return false;
                    default:
                        System.err.println("Escull una altra opció");

                }
            } catch (NumberFormatException e) {
                System.err.println("Escull una altra opció");
            }
        }
        return false;

    }

}
