/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.statistics;

import pso.secondphase.iox9.business.processing.Observable;
import pso.secondphase.iox9.model.IORecord;

/**
 *
 * @author vitorgreati
 */
public class StatisticsChainSingleton {
    
    private StatisticsProcessor statisticsHead;
    
    private static StatisticsChainSingleton instance;
    
    public static StatisticsChainSingleton getInstance() {
        if (instance == null)
            instance = new StatisticsChainSingleton();
        return instance;
    }
    
    public void setStatisticsHead(StatisticsProcessor statisticsHead) {
        this.statisticsHead = statisticsHead;
    }
    
    public void process(IORecord ioRecord) {
        if (statisticsHead != null)
            statisticsHead.process(ioRecord);
    }
    
}
