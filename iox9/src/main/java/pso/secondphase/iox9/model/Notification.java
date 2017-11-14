/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.model;

/**
 * Represent notifications.
 * 
 * @author vitorgreati
 */
public class Notification {
    
    private String message;
    private NotificationType type;
    
    public Notification(String message, NotificationType type) {
        this.message = message;
        this.type = type;
    }
    
    /**
     * @return the type
     */
    public NotificationType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(NotificationType type) {
        this.type = type;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}
