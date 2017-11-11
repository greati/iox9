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
public abstract class StatisticsProcessor<StatisticsType> extends Observable<Observer<StatisticsType>> {
    
    /**
     * Update statistics.
     * 
     * @param ioRecord 
     */
    public abstract void process(IORecord ioRecord);
    
}
