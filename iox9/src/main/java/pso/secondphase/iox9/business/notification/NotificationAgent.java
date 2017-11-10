package pso.secondphase.iox9.business.notification;

import pso.secondphase.iox9.model.IORecord;

/**
 * Generate a notification.
 * 
 * @author vitorgreati
 */
public abstract class NotificationAgent {
    
    private final NotificationAgent successor;
    
    public NotificationAgent(NotificationAgent successor) {
        this.successor = successor;
    }
    
    /**
     * Check a new IORecord and check for notifications.
     * 
     * @param ioRecord 
     */
    public void handle(IORecord ioRecord) {
        if (test(ioRecord)) {
            action(ioRecord);
        }
        successor.handle(ioRecord);
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
     */
    protected abstract void action(IORecord ioRecord);
    
}
