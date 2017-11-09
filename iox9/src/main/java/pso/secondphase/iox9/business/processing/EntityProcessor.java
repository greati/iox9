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
    
    public EntityProcessor(ModelAbstractFactory modelAbstractFactory) {
        this.modelAbstractFactory = modelAbstractFactory;
    }
    
    public void process(IdentityDataType identityData) {
    
        String identifier = null;
        
        Entity e = this.modelAbstractFactory.createEntity(identifier);
                
        IORecord ioRecord = this.modelAbstractFactory.createIORecord(e, new Date());
        
    }
    
    
}
