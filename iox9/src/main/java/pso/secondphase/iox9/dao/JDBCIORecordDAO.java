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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.exception.FailAtPersistingException;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.SimpleIORecordType;

/**
 * JDBC implementation of IORecordDAO.
 * 
 * @author vitorgreati
 */
public class JDBCIORecordDAO implements IORecordDAO {

    @Override
    public void save(IORecord io) throws FailAtPersistingException {
        Connection c = SimpleJDBCConnectionManager.getConnection();
        if (c == null)
            return;
        try {
            String sql = "INSERT INTO io_record (identifier_entity, io_type, instant) VALUES (?,?,?)";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, io.getEntity().getIdentifier());
            stm.setLong(2, io.getType().getIORecordType());
            stm.setTimestamp(3, new Timestamp(io.getInstant().getTime()));
            stm.executeUpdate();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCIORecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @Override
    public List<IORecord> listByEntity(Entity e) {
        Connection c = SimpleJDBCConnectionManager.getConnection();
        if (c == null)
            return null;
        List<IORecord> records = new ArrayList<>();
        try {
            String query = "SELECT * FROM io_record WHERE identifier_entity = ? ORDER BY instant DESC;";
            PreparedStatement stm = c.prepareStatement(query);
            stm.setString(1, e.getIdentifier());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                IORecord record = new IORecord(e, 
                        new Date(rs.getTimestamp("instant").getTime()),
                        SimpleIORecordType.getEnumEntry(rs.getLong("io_type")));
                records.add(record);
            }
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCIORecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;    }

    @Override
    public List<Date> getLastVisit(Entity e) {
        Connection c = SimpleJDBCConnectionManager.getConnection();
        if (c == null)
            return null;
        List<Date> instants = new ArrayList<>();
        
        try {
            
            StringBuilder query = new StringBuilder();
            query.append("(SELECT instant FROM io_record WHERE identifier_entity = ? AND io_type = ? ORDER BY instant DESC LIMIT 1) ");
            query.append("UNION (SELECT instant FROM io_record WHERE identifier_entity = ? AND io_type = ? ");
            query.append("and instant < (SELECT instant FROM io_record WHERE identifier_entity = ? AND io_type = ? ORDER BY instant DESC LIMIT 1) ");
            query.append("ORDER BY instant DESC LIMIT 1) ORDER BY instant;");
            
            PreparedStatement ps = c.prepareStatement(query.toString());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                instants.add( rs.getDate("instant") );
            }
            
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCIORecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return instants;
    }
}
