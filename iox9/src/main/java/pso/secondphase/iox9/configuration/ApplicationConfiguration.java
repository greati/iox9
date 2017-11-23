/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.configuration;

import java.util.HashMap;
import java.util.Map;
import pso.secondphase.iox9.business.capture.IdentityDataReceiver;
import pso.secondphase.iox9.business.capture.IdentityDataSource;
import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.business.statistics.StatisticsProcessor;

/**
 * Contains the configuration and resources of
 * the application.
 * 
 * @author vitorgreati
 */
public class ApplicationConfiguration {

    private final Map<String, Object> parameters;
    private Map<String, IdentityDataReceiver> sourceReceivers;
    private Map<String, EntityProcessor> identityProcessors;
    private Map<String, StatisticsProcessor> statisticsProcessors;
    
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
        this.identityProcessors = new HashMap<>();
        this.sourceReceivers = new HashMap<>();
        this.statisticsProcessors = new HashMap<>();
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

    /**
     * @return the sourceReceivers
     */
    public Map<String, IdentityDataReceiver> getSourceReceivers() {
        return sourceReceivers;
    }

    /**
     * @param sourceReceivers the sourceReceivers to set
     */
    public void setSourceReceivers(Map<String, IdentityDataReceiver> sourceReceivers) {
        this.sourceReceivers = sourceReceivers;
    }

    /**
     * @return the identityProcessors
     */
    public Map<String, EntityProcessor> getIdentityProcessors() {
        return identityProcessors;
    }

    /**
     * @param identityProcessors the identityProcessors to set
     */
    public void setIdentityProcessors(Map<String, EntityProcessor> identityProcessors) {
        this.identityProcessors = identityProcessors;
    }

    /**
     * @return the statisticsProcessors
     */
    public Map<String, StatisticsProcessor> getStatisticsProcessors() {
        return statisticsProcessors;
    }

    /**
     * @param statisticsProcessors the statisticsProcessors to set
     */
    public void setStatisticsProcessors(Map<String, StatisticsProcessor> statisticsProcessors) {
        this.statisticsProcessors = statisticsProcessors;
    }
    
}
