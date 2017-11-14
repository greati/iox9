/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains the configuration and resources of
 * the application.
 * 
 * @author vitorgreati
 */
public class ApplicationConfiguration {
    
    private final Map<String, Object> parameters;
    private Map<String, Object> receivers;
    private Map<String, Object> sources;
    
    private Long entityCount = new Long(0);
    
    private static ApplicationConfiguration instance;
    
    public static ApplicationConfiguration getInstance() {
        if (instance == null)
            instance = new ApplicationConfiguration();
        return instance;
    }    
    
    private ApplicationConfiguration() {
        this.parameters = new HashMap<>();
        this.parameters.put("maxCapacity", new Long(7));
    }
    
    public Map<String,Object> getParameters() {
        return this.parameters;
    }
    
    public Long getEntityCount() {
        return this.entityCount;
    }
    
    public void incrementEntityCount() {
        this.entityCount++;
    }
    
    public void decrementEntityCount() {
        this.entityCount--;
    }
}
