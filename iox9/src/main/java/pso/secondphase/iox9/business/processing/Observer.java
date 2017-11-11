package pso.secondphase.iox9.business.processing;

/**
 * Interface for classes interested in updates of an object of interest.
 * 
 * @author vitorgreati
 * @param <EntityProcessorType> The entity processor.
 */
public interface Observer<InterestType, ObservableType extends Observable> {
 
    /**
     * Called when an update must occur after a notification from the observable.
     * 
     * @param entity
     * @param processor 
     */
    public void update(InterestType entity, ObservableType observable);
    
}
