/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author albert
 */
public class Game {
    private int id;
    private String jblanques;
    private String jnegres;
    private String victoria;
    private String jutge;
    private int jornada;
    private String sala;
    private String hotel;
    
    public Game(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJblanques() {
        return jblanques;
    }

    public void setJblanques(String jblanques) {
        this.jblanques = jblanques;
    }

    public String getJnegres() {
        return jnegres;
    }

    public void setJnegres(String jnegres) {
        this.jnegres = jnegres;
    }

    public String getVictoria() {
        return victoria;
    }

    public void setVictoria(String victoria) {
        this.victoria = victoria;
    }

    public String getJutge() {
        return jutge;
    }

    public void setJutge(String jutge) {
        this.jutge = jutge;
    }

    public int getJornada() {
        return jornada;
    }

    public void setJornada(int jornada) {
        this.jornada = jornada;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }
    
}
