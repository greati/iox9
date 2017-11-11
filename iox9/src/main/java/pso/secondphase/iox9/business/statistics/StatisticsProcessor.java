/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.statistics;


import pso.secondphase.iox9.business.processing.Observable;
import pso.secondphase.iox9.business.processing.Observer;
import pso.secondphase.iox9.model.IORecord;

/**
 * Updates some kind of statistics.
 * 
 * @author vitorgreati
 * @param <StatisticsType>
 */
public abstract class StatisticsProcessor<StatisticsType> extends Observable<Observer<StatisticsType, StatisticsProcessor>> {
    
    /**
     * Update statistics.
     * 
     * @param ioRecord 
     */
    public abstract void process(IORecord ioRecord);
    
}
