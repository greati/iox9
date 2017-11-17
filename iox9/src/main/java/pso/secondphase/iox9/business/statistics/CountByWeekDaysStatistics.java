/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.statistics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import pso.secondphase.iox9.dao.IORecordDAO;
import pso.secondphase.iox9.dao.JDBCIORecordDAO;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.SimpleIORecordType;

/**
 * Given an IORecord, count the number of
 * records per week day.
 * 
 * @author vitorgreati
 */
public class CountByWeekDaysStatistics extends StatisticsProcessor {

    @Override
    public void process(IORecord ioRecord) {
        IORecordDAO iodao = new JDBCIORecordDAO();
        
        List<IORecord> records = iodao.listByEntity(ioRecord.getEntity());
        
        if(records != null){

            List<Integer> week = new ArrayList<>();
            for(int i = 0; i < 7; ++i){
                week.add(0);
            }
        
            Calendar c = Calendar.getInstance();
            for(IORecord r : records){
                if(r.getType() != SimpleIORecordType.IN) continue;
                c.setTime(new Date(r.getInstant().getTime()));
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                week.set(dayOfWeek-1, week.get(dayOfWeek-1) + 1);
            }
            
            notifyObservers(week);
        }
        
    }
    
}
