/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.view;

import pso.secondphase.iox9.business.processing.Observable;
import pso.secondphase.iox9.business.processing.Observer;
import pso.secondphase.iox9.model.IORecord;

/**
 * Simple example of a Vehicle Panel.
 * 
 * @author vitorgreati
 */
public class VehicleOutPanel implements Observer<IORecord> {

    @Override
    public void update(IORecord ioRecord, Observable observable) {
        System.out.println(ioRecord.getEntity().getIdentifier());
    }
    
}
