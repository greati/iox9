/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.exception.FailAtPersistingException;
import pso.secondphase.iox9.model.IORecord;

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
    public List<IORecord> getIORecords(String id, Date initialDate, Date finalDate, Double frequency) {
        /*        
        SELECT DISTINCT v.*, sum(io.id_io_record_type) AS frequency
        FROM vehicle AS v, io_record AS io, io_record_type AS iot
        WHERE v.id_vehicle = io.id_vehicle 
        AND iot.id_io_record_type = io.id_io_record_type 
        AND iot.name = 'in'
        GROUP BY v.id_vehicle;
        */
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
