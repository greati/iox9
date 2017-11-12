package pso.secondphase.iox9.model;

import java.util.Date;

/**
 * Represents the entity to be identified and monitored.
 * 
 * For example: cars, people, animals etc.
 * 
 * @author vitorgreati
 */
public class Entity {
    
    private String identifier;
    private Date registrationDate;
    
    public Entity(String identifier) {
        this.identifier = identifier;
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * @return the registrationDate
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    
}
