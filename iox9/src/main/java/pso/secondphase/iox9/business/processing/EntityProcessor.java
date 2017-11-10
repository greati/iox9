package pso.secondphase.iox9.business.processing;

import java.util.Date;
import java.util.Observable;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.ModelAbstractFactory;

/**
 * Performs a processing of the data received from an identity data source.
 * 
 * When a identity data is received, a recognizer capture the
 * identity, then data is collected from external sources,
 * then the entity is persisted, and, finally, all the registered
 * observers are notified.
 * 
 * @author vitorgreati
 * @param <IdentityDataType> Identity data type.
 */
public abstract class EntityProcessor<IdentityDataType> extends Observable {
    
    private final ModelAbstractFactory modelAbstractFactory;
    private final EntityRecognizer entityRecognizer;
    
    public EntityProcessor(ModelAbstractFactory modelAbstractFactory, EntityRecognizer entityRecognizer) {
        this.modelAbstractFactory = modelAbstractFactory;
        this.entityRecognizer = entityRecognizer;
    }
    
    public void process(IdentityDataType identityData) {
    
        String identifier = this.entityRecognizer.recognize(identityData);
        
        Entity e = this.modelAbstractFactory.createEntity(identifier);
                
        if(isValid(e)) {
        
            collect(e);
            
            IORecord ioRecord = this.modelAbstractFactory.createIORecord(e, new Date());
        
            persist(e);
            
            notifyAll();
        }
        
    }
    
    /**
     * Method for validating the entity. 
     * 
     * For example, check if the entity in exiting only if
     * there is an unmatched entering record.
     * 
     * @param e The entity.
     * @return True if the entity is valid.
     */
    protected abstract boolean isValid(Entity e); 
    
    /**
     * 
     * @param e 
     */
    protected abstract void collect(Entity e);
    
    /**
     * 
     * @param e 
     */
    protected abstract void persist(Entity e);
    
    
}
