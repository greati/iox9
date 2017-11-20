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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.exception.EntityNotFoundPersistedException;
import pso.secondphase.iox9.exception.FailAtPersistingException;
import pso.secondphase.iox9.model.Attribute;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.IORecordType;
import pso.secondphase.iox9.model.SimpleIORecordType;

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
        
        try {
            Connection c = SimpleJDBCConnectionManager.getConnection();
            
            StringBuilder sql = new StringBuilder();
            
            // Compose the query string
            sql.append("UPDATE entity SET ");
            int fieldsCounter = 1;
            for (Map.Entry<String, Attribute<?>> a : e.getAttrs().entrySet()) {
                sql.append(a.getValue().description + " = ? ");
                if (fieldsCounter < e.getAttrs().size())
                    sql.append(",");
                ++fieldsCounter;
            }
            sql.append(" WHERE identifier = ?");
            
            // Prepare the statement
            PreparedStatement ps = c.prepareStatement(sql.toString());
            for (Map.Entry<String, Attribute<?>> a : e.getAttrs().entrySet()) {
                ps.setObject(fieldsCounter - e.getAttrs().size(), a.getValue().value);
            }
            ps.setString(fieldsCounter, e.getIdentifier());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(JDBCEntityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
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
            sb.append("FROM entity AS e, io_record AS io ");
            sb.append("WHERE e.identifier = io.identifier_entity ");
            sb.append("AND io.io_type = ? ");
            
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
            
            ps.setLong(i++, SimpleIORecordType.IN.getIORecordType());
            
            if(id != null){
                ps.setString(i++, id);
            }
            
            if(initialDate != null){
                ps.setDate(i++, new java.sql.Date( initialDate.getTime() ));
            }
            
            if(finalDate != null){
                ps.setDate(i++, new java.sql.Date( finalDate.getTime() ));
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
                            e.getAttrs().put(newAttr.description, newAttr);
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

    @Override
    public Integer getNumberOfCars() {
        Connection c = SimpleJDBCConnectionManager.getConnection();
        if (c == null)
            return null;        
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("SELECT DISTINCT ((SELECT COUNT(ir.io_type) FROM io_record ir WHERE ir.io_type = ?) -");
            sb.append("(SELECT COUNT(ir.io_type) FROM io_record ir WHERE ir.io_type = ?)) AS inside_cars FROM io_record;");
            
            PreparedStatement ps = c.prepareStatement(sb.toString());
            
            ps.setLong(1, SimpleIORecordType.IN.getIORecordType());
            ps.setLong(2, SimpleIORecordType.OUT.getIORecordType());
            
            ResultSet rs = ps.executeQuery();
            Integer numberOfCars = 0;
            
            if(rs.next()){
                numberOfCars = rs.getInt("inside_cars");
            }
            
            c.close();
            return numberOfCars;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCEntityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    public List<Entity> list() {
        List<Entity> entityList = new ArrayList<>();
        try {
            Connection c = SimpleJDBCConnectionManager.getConnection();
            
            StringBuilder sql = new StringBuilder();
            
            sql.append("SELECT * FROM entity");
            
            PreparedStatement ps = c.prepareStatement(sql.toString());
            
            ResultSet rs = ps.executeQuery();
            
            
            while (rs.next()) {
                Entity e = new Entity();
                e.setIdentifier(rs.getString("identifier"));
                entityList.add(e);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(JDBCEntityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entityList;

    }
}
