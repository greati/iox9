/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.dao;

import java.util.Date;
import java.util.List;
import pso.secondphase.iox9.exception.FailAtPersistingException;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;

/**
 * Interface for IORecord persistence.
 * 
 * @author vitorgreati
 */
public interface IORecordDAO {
    
    /**
     * Save a new record.
     * 
     * @param io 
     * @throws pso.secondphase.iox9.exception.FailAtPersistingException 
     */
    public void save(IORecord io) throws FailAtPersistingException;
    
    /**
     * Returns a list of records of a given entity.
     * 
     * @param e
     */
    public List<IORecord> listByEntity(Entity e);
    
    /**
     * Return the date and time of last visit of entity.
     * 
     * @param e
     * @return
     */
    public List<Date> getLastVisit(Entity e);
}
