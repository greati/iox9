/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.notification;

import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.configuration.ApplicationConfiguration;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.Notification;
import pso.secondphase.iox9.model.SimpleNotificationType;

/**
 *
 * @author vitorgreati
 */
public class MaxCapacityNotificationAgent extends NotificationAgent {
    
    public MaxCapacityNotificationAgent(NotificationAgent successor) {
        super(successor);
    }

    @Override
    protected boolean test(IORecord ioRecord, EntityProcessor processor) {
        Object maxCapacity = ApplicationConfiguration.getInstance().getParameters().get("maxCapacity");
        Long current = ApplicationConfiguration.getInstance().getEntityCount();
        return (maxCapacity != null && current > ((Long) maxCapacity - 2));
    }

    @Override
    protected Notification action(IORecord ioRecord, EntityProcessor processor) {
        return new Notification("Maximum capacity almost reached!", SimpleNotificationType.CAUTION);
    }
    
}
