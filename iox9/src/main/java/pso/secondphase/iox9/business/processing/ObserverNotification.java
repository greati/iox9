package pso.secondphase.iox9.business.processing;

import pso.secondphase.iox9.model.Notification;

/**
 * Interface for classes interested in notifications.
 * 
 * @author vitorgreati
 */
public interface ObserverNotification {
 
    /**
     * Called when an update must occur after a notification from the observable.
     * 
     * @param entity
     * @param observable 
     */
    public void update(Notification entity, Observable observable);
    
}
