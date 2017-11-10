package pso.secondphase.iox9.business.processing;

/**
 * Recognize an entity from raw data of an identity data source.
 * 
 * @author vitorgreati
 * @param <IdentityDataType>
 */
public abstract class EntityRecognizer<IdentityDataType> {
    
    /**
     * Returns the identifier of the entity.
     * 
     * @return Entity identifier.
     */
    public abstract String recognize(IdentityDataType identityData);
    
}
