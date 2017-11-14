/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.notification;

import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.Notification;

/**
 *
 * @author vitorgreati
 */
public class MaxCapacityNotificationAgent extends NotificationAgent {
    
    public MaxCapacityNotificationAgent(NotificationAgent successor) {
        super(successor);
    }

    @Override
    protected boolean test(IORecord ioRecord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Notification action(IORecord ioRecord, EntityProcessor processor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
