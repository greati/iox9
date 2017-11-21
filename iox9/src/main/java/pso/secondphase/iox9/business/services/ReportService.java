/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.services;

import java.util.Date;
import java.util.List;
import pso.secondphase.iox9.dao.EntityDAO;
import pso.secondphase.iox9.model.Entity;

/**
 *
 * @author vinihcampos
 */
public class ReportService {
    private final EntityDAO entityDAO;
    
    public ReportService(EntityDAO entityDAO){
        this.entityDAO = entityDAO;
    }
    
    public List<Entity> getEntities(String id, Date initialDate, Date finalDate){
        return entityDAO.getByFilters(id, initialDate, finalDate);
    }
}
