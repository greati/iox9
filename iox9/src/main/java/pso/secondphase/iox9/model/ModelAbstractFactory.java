/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.model;

import java.util.Date;

/**
 * Abstract factory for models.
 * 
 * @author vitorgreati
 */
public abstract class ModelAbstractFactory {
    
    public abstract Entity createEntity(String identifier);
    
    public abstract IORecord createIORecord(Entity entity, Date instant, IORecordType type);
    
}
