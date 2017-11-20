/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.statistics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import pso.secondphase.iox9.dao.JDBCEntityDAO;
import pso.secondphase.iox9.dao.JDBCIORecordDAO;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.SimpleIORecordType;

/**
 *
 * @author vinihcampos
 */
public class PriceByHoursInDayStatistics extends StatisticsProcessor {

    public PriceByHoursInDayStatistics(StatisticsProcessor sucessor) {
        super(sucessor);
    }

    @Override
    public Object generateStatistics(IORecord ioRecord) {
        JDBCIORecordDAO iORecordDAO = new JDBCIORecordDAO();
        JDBCEntityDAO vehicleDAO = new JDBCEntityDAO();
        
        List<Entity> vehicles = vehicleDAO.getByFilters(null,null, null);
        
        Calendar c = Calendar.getInstance();
        List<Integer> hours = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        for(int i = 0; i < 24; ++i){
            hours.add(0);
            values.add(0.0);
        }
        
        Calendar c1 = Calendar.getInstance(); // today
        Calendar c2 = Calendar.getInstance();
        for(Entity v : vehicles){
            List<IORecord> records = iORecordDAO.listByEntity(v);
            for(IORecord r : records){
                if(r.getType().getIORecordType() != SimpleIORecordType.IN.getIORecordType()) continue;
                c2.setTime(r.getInstant());
                if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)){
                    c.setTime(r.getInstant());
                    
                    int hour = c.get(Calendar.HOUR_OF_DAY) % 24;
                    hours.set(hour, hours.get(hour) + 1);
                    
                    if(v.getAttrs().get("price") != null)
                        values.set(hour, values.get(hour) + ( (BigDecimal) v.getAttrs().get("price").value ).doubleValue());
                    else
                        values.set(hour, values.get(hour) + 0);
                }                
            }
        }
        
        for(int i = 0; i < 24; ++i){
            if(hours.get(i) > 0){
                values.set(i, values.get(i) / hours.get(i) / 1000);
            }
        }
        
        return values;
    }
    
}
