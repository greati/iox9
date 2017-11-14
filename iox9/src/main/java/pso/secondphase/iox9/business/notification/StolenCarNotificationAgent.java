/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.notification;

import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.Notification;
import pso.secondphase.iox9.model.SimpleNotificationType;
import pso.secondphase.iox9.model.Situation;
import pso.secondphase.iox9.model.Vehicle;

/**
 *
 * @author vitorgreati
 */
public class StolenCarNotificationAgent extends NotificationAgent {

    public StolenCarNotificationAgent(NotificationAgent successor) {
        super(successor);
    }

    @Override
    protected boolean test(IORecord ioRecord, EntityProcessor processor) {
        return (((Vehicle)ioRecord.getEntity()).getSituation() == Situation.IRREGULAR);
    }

    @Override
    protected Notification createNotification() {
        return new Notification("Stolen car entered!", SimpleNotificationType.CAUTION);
    }
    
}
