/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author albert
 */
public class DataBase {

    private Connection conn;
    private final String pathPostgre;
    private final String userName, password;
    private final String dirQueries;

    public DataBase(String userName, String password) {
        String dir = Paths.get("").toAbsolutePath().toString();

        String dirParent = new File(dir).getParent();
        this.dirQueries = dirParent + "/functions/consultes/";
        this.userName = userName;
        this.password = password;
        String sqlFile = dirParent + "/A1_Folch_Moreno.sql";
        this.pathPostgre = "jdbc:postgresql://postgres.mat.ub.edu/" + userName; // Adreça URL on es troba la BD. 
        this.conn = this.createDataBase(sqlFile);
    }

    private Connection createDataBase(String sqlFile) {

        try {
            Class.forName("org.postgresql.Driver");//especificamos el driver
        } catch (ClassNotFoundException ex) {
            System.err.println("El driver ha fallat");
        }
        try {
            connect();
            fillDataBase(sqlFile);
            //Statement stat = conn.createStatement();
            //stat.executeUpdate(  );
            //stat.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println("Connexió a la base de dades fallida");
        }

        return conn;

    }

    private void fillDataBase(String sqlFile) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sqlFile);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            Statement stmt = conn.createStatement();
            try {
                /*line = reader.readLine();
                 String sqlQuery = "";
                 while (null != line) {
                 sqlQuery += line;
                 //stmt.execute(line);
                    
                 line = reader.readLine();
                    
                 }*/

                // Execute the query
                //Runtime.getRuntime().exec( "psql -h postgres.mat.ub.edu -U "+userName+"_adm"+" -d "+userName+" -W -f "+sqlFile );
                //crear fitxer al /home/ amb contingut: postgres.mat.ub.edu:5432:"+userName+":"+userName+"_adm:"+password
                String exec = "psql postgresql://" + userName + "_adm:" + password + "@postgres.mat.ub.edu:5432/" + userName + " -f " + sqlFile;
                System.out.println(exec);
                Runtime.getRuntime().exec(exec);
                // Close the result set, statement and the connection
            } catch (IOException /*| SQLException*/ ex) {
                System.err.println("Error al executar la query d'inicialització de la base de dades");
            } finally {
                //stmt.close();
                System.out.println("Base de dades plena");
            }
        } catch (FileNotFoundException | SQLException ex) {
        } finally {
            try {
                inputStream.close();

            } catch (IOException ex) {
            }
        }
    }

    public String canLogin(String dni) {
        String personType = null;
        try {
            connect();
            Statement stat = conn.createStatement();
            String[] personTypes = {"jutge", "taquiller", "organitzador"};
            boolean trobat = false;
            for (int i = 0; i < personTypes.length && !trobat; i++) {
                personType = personTypes[i];
                ResultSet rsPerson = stat.executeQuery("select dni from " + personType + " where dni like '" + dni + "';");
                while (rsPerson.next() && !trobat) {//recorremos todas las filas
                    trobat = true;//1 == jugador
                    personType = personTypes[i];
                }
            }

            stat.close();
            conn.close();//Finalmente cerramos la conexion
            return personType;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            return personType;
        }
    }

    private void connect() throws SQLException {
        conn = DriverManager.getConnection(pathPostgre, userName + "_adm", password);
    }

    public void queryPlayers() {
        try {
            //executar funcio /src/functions/consultes/guanyadors.sql
            /*String exec = "psql postgresql://" + userName + "_adm:" + password + "@postgres.mat.ub.edu:5432/" + userName + " -f " + dirQueries+"guanyadors.sql";
             System.out.println(exec);
             Process p = Runtime.getRuntime().exec(exec);*/
///         ///////////////////
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from guanyadors();");
            System.out.println("NOM\t"+"VICTORIA\t"+"PartidesGuanyades\t");
            while (rs.next()) {
                // do something
                System.out.println(rs.getString("nom")+"\t"+rs.getString("victoria")+
                        "\t"+rs.getString("partidesGuanyades"));
            }
            rs.close();
            stmt.close();
///         ////////////////////////
            /*
             // Turn transactions off.
             conn.setAutoCommit(false);
             // Procedure call.
             CallableStatement upperProc = conn.prepareCall("{ ? = call guanyadors() }");
             upperProc.registerOutParameter(1, Types.VARCHAR);
             upperProc.setString(2, "lowercase to uppercase");
             upperProc.execute();
             String upperCased = upperProc.getString(1);
             upperProc.close();*/
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void queryJornades() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void queryGames(int jornada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String [] queryAllHotels() {
        //mostrar aixi: 0- hotelnom,info...
        //1- hotelnom, info...
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String [] querySales(String hotel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public String[] queryJornades(String hotel, String sala) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void buyTicket(String hotel, String sala, int jornadaEscollida, int numEntrades) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int entradesDisponibles(String hotel, String sala, int jornadaEscollida) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
