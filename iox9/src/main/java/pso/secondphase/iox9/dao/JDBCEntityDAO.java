/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.exception.EntityNotFoundPersistedException;
import pso.secondphase.iox9.exception.FailAtPersistingException;
import pso.secondphase.iox9.model.Entity;

/**
 *
 * @author vitorgreati
 */
public class JDBCEntityDAO implements EntityDAO {

    @Override
    public void save(Entity e) throws FailAtPersistingException {
        Connection c = SimpleJDBCConnectionManager.getConnection();
        if (c == null)
            return;
        try {
            String insertStmt = "INSERT INTO entity (identifier) VALUES (?);";
            PreparedStatement stm = c.prepareStatement(insertStmt);
            stm.setString(1, e.getIdentifier());
            stm.executeUpdate();
            c.close(); 
        } catch (SQLException ex) {
            Logger.getLogger(JDBCEntityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Entity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Entity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getByIdentifier(Entity e) throws EntityNotFoundPersistedException {
        
        Connection c = SimpleJDBCConnectionManager.getConnection();
        if (c == null)
            return;
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * ");
            query.append("FROM entity AS e ");
            query.append("WHERE e.identifier = ?;");
            
            PreparedStatement stm = c.prepareStatement(query.toString());
            stm.setString(1, e.getIdentifier());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                e.setIdentifier(rs.getString("identifier"));
            }
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCEntityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
