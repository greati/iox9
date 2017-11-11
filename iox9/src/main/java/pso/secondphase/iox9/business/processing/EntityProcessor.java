package pso.secondphase.iox9.business.processing;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.business.notification.NotificationAgent;
import pso.secondphase.iox9.dao.EntityDAO;
import pso.secondphase.iox9.dao.IORecordDAO;
import pso.secondphase.iox9.exception.EntityNotFoundPersistedException;
import pso.secondphase.iox9.exception.FailAtPersistingException;
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
public abstract class EntityProcessor<IdentityDataType> extends Observable<Observer<Entity, EntityProcessor>> {
    
    private final ModelAbstractFactory modelAbstractFactory;
    private final EntityRecognizer entityRecognizer;
    private final NotificationAgent notificationAgentChain;
    protected final EntityDAO entityDAO;
    protected final IORecordDAO ioDAO;
    private final IORecordType ioType;
    
    public EntityProcessor(IORecordType ioType, ModelAbstractFactory modelAbstractFactory, EntityRecognizer entityRecognizer,
            NotificationAgent notificationAgentChain, EntityDAO entityDAO, IORecordDAO ioDAO) {
        this.modelAbstractFactory = modelAbstractFactory;
        this.entityRecognizer = entityRecognizer;
        this.notificationAgentChain = notificationAgentChain;
        this.entityDAO = entityDAO;
        this.ioDAO = ioDAO;
        this.ioType = ioType;
    }
    
    public void process(IdentityDataType identityData) throws InvalidEntityException {
    
        String identifier = this.entityRecognizer.recognize(identityData);
        
        Entity e = this.modelAbstractFactory.createEntity(identifier);
                
        if(validate(e)) {
        
            IORecord ioRecord = this.modelAbstractFactory.createIORecord(e, new Date(), this.ioType);
        
            persistRecord(ioRecord);
            
            collect(e);
            
            notificationAgentChain.handle(ioRecord, this);
            
            notifyObservers(ioRecord);
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
    protected void persistRecord(IORecord io) {
        try {

            // Search the entity
            this.entityDAO.get(io.getEntity());

            // If found
            this.ioDAO.save(io);

        } catch(EntityNotFoundPersistedException | FailAtPersistingException e) {        
            Logger.getLogger(EntityProcessor.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
