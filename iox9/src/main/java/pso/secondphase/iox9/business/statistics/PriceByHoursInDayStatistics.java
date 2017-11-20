/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.statistics;

import pso.secondphase.iox9.model.IORecord;

/**
 *
 * @author vitorgreati
 */
public class PriceByHoursInDayStatistics extends StatisticsProcessor {

    public PriceByHoursInDayStatistics(StatisticsProcessor sucessor) {
        super(sucessor);
    }

    @Override
    public Object generateStatistics(IORecord ioRecord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
