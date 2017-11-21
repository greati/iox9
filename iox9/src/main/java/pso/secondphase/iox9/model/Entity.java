package pso.secondphase.iox9.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    private Map<String, Attribute<?>> attrs;
    
    public Entity(){
        this.attrs = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }
    
    public Entity(String identifier) {
        this.identifier = identifier;
        this.attrs = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
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

    /**
     * @return the attrs
     */
    public Map<String, Attribute<?>> getAttrs() {
        return attrs;
    }

    /**
     * @param attrs the attrs to set
     */
    public void setAttrs(Map<String, Attribute<?>> attrs) {
        this.attrs = attrs;
    }   
}
