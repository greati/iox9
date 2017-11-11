package pso.secondphase.iox9.business.processing;

/**
 * Interface for classes interested in updates of an object of interest.
 * 
 * @author vitorgreati
 * @param <InterestType>
 */
public interface Observer<InterestType> {
 
    /**
     * Called when an update must occur after a notification from the observable.
     * 
     * @param entity
     * @param observable 
     */
    public void update(InterestType entity, Observable observable);
    
}
