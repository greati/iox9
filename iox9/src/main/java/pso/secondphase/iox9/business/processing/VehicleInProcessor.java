/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.processing;

import java.awt.Image;
import pso.secondphase.iox9.business.notification.NotificationAgent;
import pso.secondphase.iox9.configuration.ApplicationConfiguration;
import pso.secondphase.iox9.dao.EntityDAO;
import pso.secondphase.iox9.dao.IORecordDAO;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecordType;
import pso.secondphase.iox9.model.ModelAbstractFactory;
import pso.secondphase.iox9.model.Vehicle;

/**
 *
 * @author vitorgreati
 */
public class VehicleInProcessor extends EntityProcessor<Image> {

    public VehicleInProcessor(IORecordType ioType, ModelAbstractFactory modelAbstractFactory, EntityRecognizer entityRecognizer, EntityDAO entityDAO, IORecordDAO ioDAO) {
        super(ioType, modelAbstractFactory, entityRecognizer, entityDAO, ioDAO);
    }

    @Override
    protected boolean validate(Entity e) {
        // Check if the vehicle is there already. Must throw InvalidEntityException otherwise.
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ApplicationConfiguration.getInstance().incrementEntityCount();
        return true;
    }

    @Override
    protected void collect(Entity e) {
        // Send to the thread of collect
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected void populateSpecificValues(Image identityData, Entity e) {
        Vehicle vehicle = ((Vehicle)e);
        vehicle.setRegistrationDate(e.getRegistrationDate());
        vehicle.setAttrs(e.getAttrs());
        
        vehicle.setImage(identityData);
        vehicle.setPlate(e.getIdentifier());
    }
    
}
