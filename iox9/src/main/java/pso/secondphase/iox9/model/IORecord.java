/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.model;

import java.util.Date;

/**
 * Represents the event of an entity entering or exiting
 * 
 * @author vitorgreati
 */
public class IORecord {
    
    private final Entity entity;
    
    private final Date instant;
    
    private final IORecordType type;
    
    public IORecord(Entity entity, Date instant, IORecordType type) {
        this.entity = entity;
        this.instant = instant;
        this.type = type;
    }
    
}
