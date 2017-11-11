package pso.secondphase.iox9.business.processing;

/**
 * Recognize an entity from raw data of an identity data source.
 * 
 * @author vitorgreati
 * @param <IdentityDataType>
 */
public interface EntityRecognizer<IdentityDataType> {
    
    /**
     * Returns the identifier of the entity.
     * 
     * @param identityData Raw data coming from a data source.
     * @return Entity identifier.
     */
    public String recognize(IdentityDataType identityData);
    
}
