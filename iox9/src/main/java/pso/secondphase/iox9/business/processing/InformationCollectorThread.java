/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.processing;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.dao.EntityDAO;
import pso.secondphase.iox9.model.Entity;

/**
 * Thread which allows the query for information
 * in a given frequency. 
 * 
 * This class has a String queue for vehicle
 * plates and an abstract information collector.
 * It updates the vehicle database with the gathered information.
 * 
 * @author vitorgreati
 */
public class InformationCollectorThread extends Thread {
    
    private volatile Queue<Entity> entitiesQueue;
    private boolean active;
    private long waitingTime;
    private final InformationCollector collector;
    private final EntityDAO entityDAO;

    private static InformationCollectorThread instance;
    
    public static InformationCollectorThread getInstance() throws Exception {
        if (instance == null)
            throw new Exception("This Singleton needs initialization parameters.");
        return instance;
    }
    
    public static InformationCollectorThread getInstance(InformationCollector collector, EntityDAO entityDAO, long waitingTime) {
        if (instance == null)
            instance = new InformationCollectorThread(collector, entityDAO, waitingTime);
        return instance;
    }
    
    private InformationCollectorThread(InformationCollector collector, EntityDAO entityDAO, long waitingTime) {
        this.entitiesQueue = new LinkedList<>();
        this.waitingTime = waitingTime;
        this.collector = collector;
        this.entityDAO = entityDAO;
    }
    
    @Override
    public void run(){
        this.active = true;
        while(isActive()) {
            try {
                if (!entitiesQueue.isEmpty()) {
                    Entity e = getEntitiesQueue().remove();
                    collector.collect(e);
                    entityDAO.update(e);
                    Thread.sleep(getWaitingTime());
                } 
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(InformationCollectorThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the waitingTime
     */
    public long getWaitingTime() {
        return waitingTime;
    }

    /**
     * @param waitingTime the waitingTime to set
     */
    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    /**
     * @return the entitiesQueue
     */
    public Queue<Entity> getEntitiesQueue() {
        return entitiesQueue;
    }

    /**
     * @param entitiesQueue the entitiesQueue to set
     */
    public void setEntitiesQueue(Queue<Entity> entitiesQueue) {
        this.entitiesQueue = entitiesQueue;
    }

}
