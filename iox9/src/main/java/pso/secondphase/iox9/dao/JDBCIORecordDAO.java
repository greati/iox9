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
}
