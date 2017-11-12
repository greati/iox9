package pso.secondphase.iox9.business.processing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interface for classes interested in updates of an object of interest.
 * 
 * @author vitorgreati
 * @param <InterestType>
 */
public abstract class Observer {
 
    /**
     * Called when an update must occur after a notification from the observable.
     * 
     * @param entity
     * @param observable 
     */
    public void update(Observable observable, Object object) {
        
        try {
            Method update = getClass().getMethod("update", observable.getClass(), Object.class);
            update.invoke(this, observable, object);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Observer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
