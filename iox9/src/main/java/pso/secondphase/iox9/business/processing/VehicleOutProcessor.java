/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.processing;

import pso.secondphase.iox9.business.notification.NotificationAgent;
import pso.secondphase.iox9.dao.EntityDAO;
import pso.secondphase.iox9.dao.IORecordDAO;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecordType;
import pso.secondphase.iox9.model.ModelAbstractFactory;

/**
 *
 * @author vitorgreati
 */
public class VehicleOutProcessor extends EntityProcessor {

    public VehicleOutProcessor(IORecordType ioType, ModelAbstractFactory modelAbstractFactory, EntityRecognizer entityRecognizer, NotificationAgent notificationAgentChain, EntityDAO entityDAO, IORecordDAO ioDAO) {
        super(ioType, modelAbstractFactory, entityRecognizer, notificationAgentChain, entityDAO, ioDAO);
    }

    @Override
    protected boolean validate(Entity e) {
        /* Check if that vehicle was inside */
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void collect(Entity e) {
        /* Empty */
    }
    
}
