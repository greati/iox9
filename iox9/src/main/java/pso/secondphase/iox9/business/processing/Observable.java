/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.processing;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for observables.
 * 
 * @author vitorgreati
 * @param <ObserverType>
 */
public abstract class Observable<ObserverType extends Observer> {
    
    private volatile List<ObserverType> observers = new ArrayList<>();
    
    /**
     * Register an observer.
     * 
     * @param o 
     */
    public synchronized void addObserver(ObserverType o) {
        observers.add(o);
    }
    
    /**
     * Remove an observer.
     * 
     * @param o
     */
    public synchronized void removeObserver(ObserverType o) {
        observers.remove(o);
    }
    
    /**
     * Notify every observers.
     * 
     * @param object 
     */
    public void notifyObservers(Object object) {
        observers.forEach((o) -> {
            o.update(object, this);
        });
    }
    
    
}
