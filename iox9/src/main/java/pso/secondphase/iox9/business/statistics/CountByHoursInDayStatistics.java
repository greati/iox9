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
import pso.secondphase.iox9.dao.EntityDAO;
import pso.secondphase.iox9.dao.IORecordDAO;
import pso.secondphase.iox9.dao.JDBCEntityDAO;
import pso.secondphase.iox9.dao.JDBCIORecordDAO;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.SimpleIORecordType;

/**
 *
 * @author vitorgreati
 */
public class CountByHoursInDayStatistics extends StatisticsProcessor {

    public CountByHoursInDayStatistics(StatisticsProcessor sucessor) {
        super(sucessor);
    }

    @Override
    public Object generateStatistics(IORecord ioRecord) {
        IORecordDAO iORecordDAO = new JDBCIORecordDAO();
        EntityDAO entityDAO = new JDBCEntityDAO();
        
        List<Entity> vehicles = entityDAO.list();
        
        Calendar c = Calendar.getInstance();
        List<Integer> hours = new ArrayList<>();
        for(int i = 0; i < 24; ++i){
            hours.add(0);
        }
        
        Calendar c1 = Calendar.getInstance(); // today
        Calendar c2 = Calendar.getInstance();
        for(Entity v : vehicles){
            List<IORecord> records = iORecordDAO.listByEntity(v);
            for(IORecord r : records){
                if(r.getType() != SimpleIORecordType.IN) continue;
                c2.setTime(new Date(r.getInstant().getTime()));
                if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)){
                    c.setTime(new Date(r.getInstant().getTime()));
                    int hour = c.get(Calendar.HOUR_OF_DAY) % 24;
                    hours.set(hour, hours.get(hour) + 1);
                }                
            }
        }
        
        return hours;
    }
    
}
