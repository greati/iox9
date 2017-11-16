/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.exception.EntityNotFoundPersistedException;
import pso.secondphase.iox9.exception.FailAtPersistingException;
import pso.secondphase.iox9.model.Attribute;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;

/**
 *
 * @author vitorgreati
 */
public class JDBCEntityDAO implements EntityDAO {

    @Override
    public void save(Entity e) throws FailAtPersistingException {
        Connection c = SimpleJDBCConnectionManager.getConnection();
        if (c == null || e == null)
            return;
        try {
            String insertStmt = "INSERT INTO entity (identifier, registration_date) VALUES (?, ?);";
            PreparedStatement stm = c.prepareStatement(insertStmt);
            stm.setString(1, e.getIdentifier());
            stm.setDate(2, new java.sql.Date(new Date().getTime()));
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
    public Entity getByIdentifier(String identifier) {
        
        Connection c = SimpleJDBCConnectionManager.getConnection();
        if (c == null)
            return null;
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * ");
            query.append("FROM entity AS e ");
            query.append("WHERE e.identifier = ?;");
            
            PreparedStatement stm = c.prepareStatement(query.toString());
            stm.setString(1, identifier);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Entity e = new Entity(identifier);
                e.setIdentifier(rs.getString("identifier"));
                e.setRegistrationDate(rs.getDate("registration_date"));
                return e;
            }
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCEntityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     *
     * @param id
     * @param initialDate
     * @param finalDate
     * @return
     */
    @Override
    public List<Entity> getByFilters(String id, Date initialDate, Date finalDate){
      Connection c = SimpleJDBCConnectionManager.getConnection();
        if (c == null)
            return null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT DISTINCT e.*, sum(io.io_type) AS frequency ");
            sb.append("FROM entity AS e, io_record AS io, io_record_type AS iot ");
            sb.append("WHERE e.identifier = io.identifier_entity ");
            sb.append("AND iot.id_io_record_type = io.io_type ");
            sb.append("AND iot.name = 'in' ");
            
            if(id != null){
                sb.append("AND e.identifier = ? ");
            }
            
            if(initialDate != null){
                sb.append("AND e.registration_date >= ? ");
            }
            
            if(finalDate != null){
                sb.append("AND e.registration_date <= ? ");
            }
            
            sb.append("GROUP BY e.identifier;");
            
            PreparedStatement ps = c.prepareStatement(sb.toString());
            
            int i = 1;
            if(id != null){
                ps.setString(i++, id);
            }
            
            if(initialDate != null){
                ps.setDate(i++, java.sql.Date.valueOf( initialDate.toString() ));
            }
            
            if(finalDate != null){
                ps.setDate(i, java.sql.Date.valueOf( finalDate.toString() ));
            }
            
            ResultSet rs = ps.executeQuery();
            
            ResultSetMetaData rsmd;
            List<Entity> entities = new ArrayList<>();
            
            while(rs.next()){
                rsmd = rs.getMetaData();
                Entity e = new Entity();
                
                for(i = 1; i <= rsmd.getColumnCount(); ++i){
                    switch (rsmd.getColumnName(i)) {
                        case "identifier":
                            e.setIdentifier(rs.getString("identifier"));
                            break;
                        case "registration_date":
                            e.setRegistrationDate(rs.getDate("registration_date"));
                            break;
                        default:    
                            Class className = Class.forName( rsmd.getColumnClassName(i) );
                            Attribute<?> newAttr = new Attribute<>( rs.getObject(i, className  ) , rsmd.getColumnName(i));
                            e.getAttrs().add(newAttr);
                            break;
                    }
                }
                
                entities.add(e);
            }                                  
            c.close();
            
            return entities;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(JDBCIORecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
