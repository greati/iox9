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

/**
 *
 * @author vitorgreati
 */
public class DailyVisitorsGoalNotificationAgent extends NotificationAgent {

    public DailyVisitorsGoalNotificationAgent(NotificationAgent successor) {
        super(successor);
    }

    @Override
    protected boolean test(IORecord ioRecord, EntityProcessor processor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Notification createNotification() {
        return new Notification("Visitors goal achieved!", SimpleNotificationType.INFO);
    }
    
}
