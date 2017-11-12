package pso.secondphase.iox9.business.notification;

import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.Notification;

/**
 * Generate a notification.
 * 
 * @author vitorgreati
 */
public abstract class NotificationAgent {
    
    private NotificationAgent successor;
    
    public NotificationAgent(NotificationAgent successor) {
        this.successor = successor;
    }
    
    /**
     * Check a new IORecord and check for notifications.
     * 
     * @param ioRecord The IORecord to be processed.
     * @param processor The processor from which the IORecord came.
     */
    public void handle(IORecord ioRecord, EntityProcessor processor) {
        if (test(ioRecord)) {
            Notification not = action(ioRecord);
            NotifierChainSingleton.getInstance().notify(ioRecord, processor);
        }
        if (successor != null)
            successor.handle(ioRecord, processor);
    }
    
    /**
     * Set the sucessor in the chain.
     * 
     * @param notificationAgentChain 
     */
    public void setSucessor(NotificationAgent notificationAgentChain) {
        this.successor = notificationAgentChain;
    }
    
    /**
     * Condition for throwing the notification.
     * 
     * @param ioRecord
     * @return 
     */
    protected abstract boolean test(IORecord ioRecord);
    
    /**
     * Action based on the IORecord.
     * 
     * @param ioRecord
     * @return 
     */
    protected abstract Notification action(IORecord ioRecord);
    
}
