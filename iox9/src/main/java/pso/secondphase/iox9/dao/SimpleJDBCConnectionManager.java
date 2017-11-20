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
    // JDBC driver name and database URL
   static final String DB_URL = "jdbc:postgresql://localhost:5432/rapx9db";

   //  Database credentials
   static final String USER = "rapx9";
   static final String PASS = "123";
   
   public static Connection getConnection() {
       Properties props = new Properties();
       props.setProperty("user", USER);
       props.setProperty("password", PASS);
       Connection conn = null;
       try {       
            conn = DriverManager.getConnection(DB_URL, props);
       } catch (SQLException ex) {
           Logger.getLogger(SimpleJDBCConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
       return conn;
   }   
}
