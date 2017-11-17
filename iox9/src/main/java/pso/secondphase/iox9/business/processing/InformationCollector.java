/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.processing;

import pso.secondphase.iox9.model.Entity;

    
/**
 * Collect information about the entity,
 * dealing with custom data and custom DAO manipulation.
 * 
 * @author vitorgreati
 */
public interface InformationCollector {
    
    public void collect(Entity e);
    
}
