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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
            System.out.println("NOM\t" + "VICTORIA\t" + "PartidesGuanyades\t");
            while (rs.next()) {
                // do something
                System.out.println(rs.getString("nom") + "\t" + rs.getString("victoria")
                        + "\t" + rs.getString("partidesGuanyades"));
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
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT distinct jornada from v_taquillers;");
            System.out.println("Jornada");
            while (rs.next()) {
                // do something
                System.out.println(rs.getString("jornada"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void queryGames(int jornada) {
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Id_partida,Jugador_blanques"
                    + "Jugador_negres,victoria,jutge from v_taquillers"
                    + "where jornada = " + jornada + ";");
            System.out.println("Jornada");
            while (rs.next()) {
                // do something
                System.out.println(rs.getString("jornada") + "\t" + rs.getInt("Id_partida")
                        + "\t" + rs.getString("Jugador_blanques") + "\t" + rs.getString("Jugador_negres") + "\t"
                        + rs.getString("victoria") + "\t" + rs.getString("jutge"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] queryAllHotels() {
        //mostrar aixi: 0- hotelnom,info...
        //1- hotelnom, info...
        String[] h = null;

        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT hotel,direccio from v_taquillers;");
            System.out.println("Hotel" + "\t" + "Direcció");
            int i = 0;
            ArrayList<String> hotels = new ArrayList<>();
            while (rs.next()) {
                // do something
                String nomHotel = rs.getString("hotel");
                System.out.println(i + "\t" + nomHotel + "\t" + rs.getInt("direccio"));
                i++;
                hotels.add(nomHotel);
            }
            h = new String[hotels.size()];
            for (int j = 0; j < h.length; j++) {
                h[j] = hotels.get(j);

            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return h;
    }

    public String[] querySales(String hotel) {
        String[] s = null;

        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sala from v_taquillers "
                    + "where hotel like '" + hotel + "';");
            System.out.println("Sala");
            int i = 0;
            ArrayList<String> sales = new ArrayList<>();
            while (rs.next()) {
                // do something
                String nomSala = rs.getString("sala");
                System.out.println(i + "\t" + nomSala);
                i++;
                sales.add(nomSala);
            }
            s = new String[sales.size()];
            for (int j = 0; j < s.length; j++) {
                s[j] = sales.get(j);

            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public String[] queryJornades(String hotel, String sala) {
        String[] jo = null;

        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT jornada,datainici,datafi,jugador_blanques,jugador_negres,jutge from v_taquillers "
                    + "where hotel like '" + hotel + "' AND sala like '"+sala+"' AND victoria IS NULL;");//JORNADES DE PARTIDES ACABADES
            System.out.println("Jornada\tData d'inici\tData fi\tJugador blanques\tJugador negres\tJutge");
            ArrayList<String> jornades = new ArrayList<>();
            while (rs.next()) {
                // do something
                String jornada = rs.getString("jornada");
                if(!jornades.contains(jornada))
                    System.out.println("\t" + jornada);
                jornades.add(jornada);
            }
            jo = new String[jornades.size()];
            for (int j = 0; j < jo.length; j++) {
                jo[j] = jornades.get(j);

            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jo;
    }

    public void buyTicket(String hotel, String sala, int jornadaEscollida, int numEntrades) {
        try {
            connect();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE entrada "
                    + "set entradesvenudes = entradesvenudes - "+numEntrades
                    + " where hotel like '" + hotel + "' AND sala like '"+sala+"' AND jornada like '"+jornadaEscollida+"';");
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int entradesDisponibles(String hotel, String sala, int jornadaEscollida) {
        int entrades = -1;
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT distinct entrades_disponibles from v_taquillers "
                    + "where hotel like '" + hotel + "' AND sala like '"+sala+"' AND jornada like '"+jornadaEscollida+"';");//JORNADES DE PARTIDES ACABADES
            System.out.println("Entrades disponibles");
            while (rs.next()) {
                // do something
                entrades = rs.getInt("entrades_disponibles");
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entrades;
    }

    public Game getGame(int jornada, int idPartida) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
