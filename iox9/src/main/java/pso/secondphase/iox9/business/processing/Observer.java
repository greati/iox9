package pso.secondphase.iox9.business.processing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interface for classes interested in updates of an object of interest.
 * 
 * @author vitorgreati
 */
public abstract class Observer {
 
    private String id;
    
    /**
     * Called when an update must occur after a notification from the observable.
     * 
     * @param object
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

    /**
     * @return the identifier
     */
    public String getId() {
        return id;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setId(String id) {
        this.id = id;
    }
    
}
