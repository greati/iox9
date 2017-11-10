package pso.secondphase.iox9.model;

/**
 * Represents the entity to be identified and monitored.
 * 
 * For example: cars, people, animals etc.
 * 
 * @author vitorgreati
 */
public class Entity {
    
    private String identifier;
    
    public Entity(String identifier) {
        this.identifier = identifier;
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
}
