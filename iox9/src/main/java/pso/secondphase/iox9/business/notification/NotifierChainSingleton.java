/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.notification;

import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.business.processing.Observable;
import pso.secondphase.iox9.model.IORecord;

/**
 * Start notification process and receive broadcast requests.
 * 
 * @author vitorgreati
 */
public class NotifierChainSingleton extends Observable {
    
    private NotificationAgent notifierHead;
    
    private static NotifierChainSingleton instance;

    public static NotifierChainSingleton getInstance() {
        if (instance == null)
            instance = new NotifierChainSingleton();
        return instance;
    }
    
    public void setNotifierHead(NotificationAgent notifierHead) {
        this.notifierHead = notifierHead;
    }
    
    public void notify(IORecord io, EntityProcessor processor) {
        if(notifierHead != null)
            notifierHead.handle(io, processor);
    }
    
}
