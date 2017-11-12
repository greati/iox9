package pso.secondphase.iox9.business.statistics;


import pso.secondphase.iox9.business.processing.Observable;
import pso.secondphase.iox9.model.IORecord;

/**
 * Updates some kind of statistics.
 * 
 * @author vitorgreati
 */
public abstract class StatisticsProcessor extends Observable {
    
    /**
     * Update statistics.
     * 
     * @param ioRecord 
     */
    public abstract void process(IORecord ioRecord);
    
}
