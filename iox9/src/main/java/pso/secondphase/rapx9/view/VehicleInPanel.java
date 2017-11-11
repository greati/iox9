/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.view;

import pso.secondphase.iox9.business.processing.Observable;
import pso.secondphase.iox9.business.processing.Observer;
import pso.secondphase.iox9.business.processing.VehicleInProcessor;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.Vehicle;

/**
 *
 * Simple example of a Vehicle Panel.
 * 
 * @author vitorgreati
 */
public class VehicleInPanel implements Observer<IORecord> {

    @Override
    public void update(IORecord ioRecord, Observable observable) {
        System.out.print(ioRecord.getEntity().getIdentifier());
    }
    
}
