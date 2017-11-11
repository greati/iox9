/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.control;

import pso.secondphase.iox9.business.processing.VehicleInProcessor;
import pso.secondphase.iox9.business.processing.VehicleOutProcessor;
import pso.secondphase.iox9.model.SimpleIORecordType;
import pso.secondphase.iox9.model.VehicleFactory;
import pso.secondphase.rapx9.view.VehicleInPanel;
import pso.secondphase.rapx9.view.VehicleOutPanel;

/**
 *
 * @author vitorgreati
 */
public class MainControl {
    
    // Instantiate view classes
    VehicleInPanel inPanel = new VehicleInPanel();
    VehicleOutPanel outPanel = new VehicleOutPanel();
        
    // Processors
    VehicleInProcessor inProcessor = 
            new VehicleInProcessor(SimpleIORecordType.IN, new VehicleFactory());
    VehicleOutProcessor outProcessor = 
            new VehicleOutProcessor(SimpleIORecordType.OUT, new VehicleFactory());
    
    // Create threads
    public static void main(String args[]) {
        
    }
    
}
