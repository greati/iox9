/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vitorgreati
 */
public class SimpleJDBCConnectionManager {
   
    private static String dbUrl;
    private static String username;
    private static String password;
    
   // JDBC driver name and database URL
   //static final String DB_URL = "jdbc:postgresql://localhost:5432/rapx9db";

   //  Database credentials
   //static final String USER = "rapx9";
   //static final String PASS = "123";
   
   public static Connection getConnection() {
       Properties props = new Properties();
       props.setProperty("user", username);
       props.setProperty("password", password);
       Connection conn = null;
       try {       
            conn = DriverManager.getConnection(dbUrl, props);
       } catch (SQLException ex) {
           Logger.getLogger(SimpleJDBCConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
       return conn;
   }   

    /**
     * @return the dbUrl
     */
    public static String getDbUrl() {
        return dbUrl;
    }

    /**
     * @param dbUrl the dbUrl to set
     */
    public static void setDbUrl(String dbUrl) {
        SimpleJDBCConnectionManager.dbUrl = dbUrl;
    }

    /**
     * @return the username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public static void setUsername(String username) {
        SimpleJDBCConnectionManager.username = username;
    }

    /**
     * @return the password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public static void setPassword(String password) {
        SimpleJDBCConnectionManager.password = password;
    }
}
