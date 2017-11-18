/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.dao;

import java.util.Date;
import java.util.List;
import pso.secondphase.iox9.exception.EntityNotFoundPersistedException;
import pso.secondphase.iox9.exception.FailAtPersistingException;
import pso.secondphase.iox9.model.Entity;

/**
 * Interface for classes implementing persistence of general entities.
 * 
 * @author vitorgreati
 */
public interface EntityDAO {
    
    /**
     * Persist an entity.
     * 
     * @param e 
     * @throws pso.secondphase.iox9.exception.FailAtPersistingException 
     */
    public void save(Entity e) throws FailAtPersistingException;
    
    /**
     * Update an entity.
     * 
     * @param e
     */
    public void update(Entity e);
    
    /**
     * Remove an entity.
     * 
     * @param e
     */
    public void remove(Entity e);
    
    /**
     * Get an entity by its identity.
     * 
     * @param identifier
     * @return 
     * @throws pso.secondphase.iox9.exception.EntityNotFoundPersistedException
     */
    public Entity getByIdentifier(String identifier);
    
    /**
     *
     * @param id
     * @param initialDate
     * @param finalDate
     * @return
     */
    public List<Entity> getByFilters(String id, Date initialDate, Date finalDate);
    
    /**
     *
     * @return
     */
    public Integer getNumberOfCars();
    
    /**
     * List all entities.
     * 
     * @return 
     */
    public List<Entity> list();
}
