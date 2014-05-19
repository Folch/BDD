/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chesstournament;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author albert
 */
public class DataBase {
    private Connection conn;
    private String pathPostgre;

    public DataBase(String userName,String password){
        String dir = Paths.get("").toAbsolutePath().toString();
        
        String dirParent = new File(dir).getParent();
        
        String sqlFile = dirParent+"/A1_Folch_Moreno.sql";
        this.pathPostgre = "jdbc:postgresql://postgres.mat.ub.edu/"+userName; // Adreça URL on es troba la BD. 
        this.conn = this.createDataBase(userName,password,sqlFile);
    }
    private Connection createDataBase(String userName,String password,String sqlFile) {

        try {
            Class.forName("org.postgresql.Driver");//especificamos el driver
        } catch (ClassNotFoundException ex) {
            System.err.println("El driver ha fallat");
        }
        try {
            conn = DriverManager.getConnection(pathPostgre,userName+"_adm",password);
            fillDataBase(sqlFile,userName);
            //Statement stat = conn.createStatement();
            //stat.executeUpdate(  );
            //stat.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println("Connexió a la base de dades fallida");
        }
       
        
        return conn;

    }
    private void fillDataBase(String sqlFile,String userName) {
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
                    String exec = "psql postgesql://"+userName+"_adm:"+userName+"@postgres.mat.ub.edu:5432/"+userName+" -f "+"\""+sqlFile+"\"";
                    System.out.println(exec);
                    Runtime.getRuntime().exec(exec);
                // Close the result set, statement and the connection
            } catch (IOException /*| SQLException*/ ex) {
                System.err.println("Error al executar la query d'inicialització de la base de dades");
            } finally {
                //stmt.close();
                conn.close();
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
}
