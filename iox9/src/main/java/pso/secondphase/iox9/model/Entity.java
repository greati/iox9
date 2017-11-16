package pso.secondphase.iox9.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<Attribute<?>> attrs;
    
    public Entity(){
        this.attrs = new ArrayList<>();
    }
    
    public Entity(String identifier) {
        this.identifier = identifier;
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
        this.attrs = new ArrayList<>();
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

    /**
     * @return the attrs
     */
    public List<Attribute<?>> getAttrs() {
        return attrs;
    }

    /**
     * @param attrs the attrs to set
     */
    public void setAttrs(List<Attribute<?>> attrs) {
        this.attrs = attrs;
    }    
}
