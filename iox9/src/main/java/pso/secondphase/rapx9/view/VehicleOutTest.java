/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.view;

import pso.secondphase.iox9.business.processing.Observer;
import pso.secondphase.iox9.business.processing.VehicleInProcessor;
import pso.secondphase.iox9.business.processing.VehicleOutProcessor;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;

/**
 *
 * @author vitorgreati
 */
public class VehicleOutTest extends Observer {
    
    public void update(VehicleOutProcessor observable, Object o) {
        
        Entity e = ((IORecord)o).getEntity();
        System.out.println("Saiu (POUT): " + ((IORecord)o).getEntity().getIdentifier());
        
    }
    
}

