/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.People.Anonymous;
import Model.DataBase;
import Model.People.Organizer;
import Model.People.Referee;
import Model.People.Salesman;
import java.util.Scanner;

/**
 *
 * @author albert
 */
public class Controller {
    private final DataBase dataBase;
    private final Scanner sc;
    public Controller(DataBase db){
        this.dataBase = db;
        this.sc = new Scanner (System.in);
    }

    public void manageOption(int opcio) {
        switch(opcio){
            case 1://usuari logat
                
                String dni = "";
                while(!dni.equals("0")){
                    System.out.print("\nEntra el teu dni per logarte(0 per cancel·lar): ");
                    dni = sc.nextLine();
                    if(!dni.equals("0")){
                        String personType = dataBase.canLogin(dni);
                        if(personType == null){
                            System.err.println("Aquest dni no està a la base de dades");
                        }else{
                            classifyPerson(personType);
                        }
                    }
                }
                
                break;
            case 2://usuari anonim
                Anonymous anonymous = new Anonymous(dataBase);
                anonymous.show();
                break;
        }
    }
    private void classifyPerson(String personType){
        if(personType.equals("jutge")){
            Referee jutge = new Referee(dataBase);
            jutge.show();
        }else if(personType.equals("taquiller")){
            Salesman taquiller = new Salesman(dataBase);
            taquiller.show();
        }else if(personType.equals("organitzador")){
            Organizer organitzador = new Organizer(dataBase);
            organitzador.show();
        }
    }
}
