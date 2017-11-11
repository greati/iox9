package pso.secondphase.iox9.exception;

/**
 * Thrown when an entity is not found persisted.
 * 
 * @author vitorgreati
 */
public class EntityNotFoundPersistedException extends Exception {
    public EntityNotFoundPersistedException() {
        super("Entity not found persisted.");
    }
}
