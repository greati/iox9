package pso.secondphase.iox9.business.statistics;


import pso.secondphase.iox9.business.processing.Observable;
import pso.secondphase.iox9.model.IORecord;

/**
 * Updates some kind of statistics.
 * 
 * @author vitorgreati
 */
public abstract class StatisticsProcessor extends Observable {
    
    private StatisticsProcessor sucessor;
    
    public StatisticsProcessor(StatisticsProcessor sucessor) {
        this.sucessor = sucessor;
    }
    
    /**
     * Update statistics.
     * 
     * @param ioRecord 
     */
    public void process(IORecord ioRecord) {
        Object o = generateStatistics(ioRecord);
        notifyObservers(o);
        if (sucessor != null)
            sucessor.process(ioRecord);
    }
    
    /**
     * Generate specific statistics.
     * 
     * @param ioRecord
     * @return 
     */
    public abstract Object generateStatistics(IORecord ioRecord);
    
}
