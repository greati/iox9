/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.model;

/**
 * Simple implementation of notification types.
 * 
 * @author vitorgreati
 */
public enum SimpleNotificationType implements NotificationType {

    CAUTION("0"), WARNING("1"), INFO("2");

    String notificationType;
    
    SimpleNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
    
    @Override
    public String getNotificationType() {
        return this.notificationType;
    }
    
}
