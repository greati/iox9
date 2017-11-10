package pso.secondphase.iox9.business.processing;

import java.util.Date;
import java.util.Observable;
import pso.secondphase.iox9.business.notification.NotificationAgentChain;
import pso.secondphase.iox9.exception.InvalidEntityException;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.IORecordType;
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
    private final NotificationAgentChain notificationAgentChain;
    
    public EntityProcessor(ModelAbstractFactory modelAbstractFactory, EntityRecognizer entityRecognizer,
            NotificationAgentChain notificationAgentChain) {
        this.modelAbstractFactory = modelAbstractFactory;
        this.entityRecognizer = entityRecognizer;
        this.notificationAgentChain = notificationAgentChain;
    }
    
    public void process(IdentityDataType identityData) throws InvalidEntityException {
    
        String identifier = this.entityRecognizer.recognize(identityData);
        
        Entity e = this.modelAbstractFactory.createEntity(identifier);
                
        if(validate(e)) {
        
            collect(e);
            
            IORecord ioRecord = this.modelAbstractFactory.createIORecord(e, new Date(), createRecordType());
        
            persist(ioRecord);
            
            notificationAgentChain.handle(ioRecord, this);
            
            notifyAll();
        } else {
            throw new InvalidEntityException();
        }
        
    }
    
    /**
     * Method for validating the entity. 
     * 
     * For example, check if the entity is exiting only if
     * there is an unmatched entering record.
     * 
     * @param e The entity.
     * @return True if the entity is valid.
     */
    protected abstract boolean validate(Entity e); 
    
    /**
     * Collect complementary data.
     * 
     * @param e The entity.
     */
    protected abstract void collect(Entity e);
    
    /**
     * Persist the data.
     * 
     * @param io The record to be persisted.
     */
    protected abstract void persist(IORecord io);
    
    /**
     * Create the right record type for IO records in this processor.
     * 
     * @return The IO record type.
     */
    protected abstract IORecordType createRecordType();
}
