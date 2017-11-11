/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.configuration;

/**
 * Interface for classes used to load the application parameters.
 * 
 * @author vitorgreati
 */
public interface ConfigurationLoader {
 
    /**
     * Load the application parameters.
     * 
     * @param uri Where is the file located.
     */
    public void load(String uri);
    
}
