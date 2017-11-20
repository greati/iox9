/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.processing;

import java.awt.Image;
import java.util.Date;
import java.util.List;
import pso.secondphase.iox9.configuration.ApplicationConfiguration;
import pso.secondphase.iox9.dao.EntityDAO;
import pso.secondphase.iox9.dao.IORecordDAO;
import pso.secondphase.iox9.model.Attribute;
import pso.secondphase.iox9.business.notification.NotificationAgent;
import pso.secondphase.iox9.configuration.ApplicationConfiguration;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecordType;
import pso.secondphase.iox9.model.ModelAbstractFactory;

/**
 *
 * @author vitorgreati
 */
public class VehicleOutProcessor extends EntityProcessor<Image> {

    public VehicleOutProcessor(IORecordType ioType, ModelAbstractFactory modelAbstractFactory, EntityRecognizer entityRecognizer, EntityDAO entityDAO, IORecordDAO ioDAO) {
        super(ioType, modelAbstractFactory, entityRecognizer, entityDAO, ioDAO);
    }

    @Override
    protected boolean validate(Entity e) {
        /* Check if that vehicle was inside. Must throw InvalidEntityException otherwise. */
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ApplicationConfiguration.getInstance().decrementEntityCount();
        return true;
    }

    @Override
    protected void collect(Entity e) {
        /* Empty */
    }

    @Override
    protected void populateSpecificValues(Image identityData, Entity e) {        
        if(e.getIdentifier() != null){
            Entity newEntity = entityDAO.getByIdentifier(e.getIdentifier());
            e.setAttrs( newEntity.getAttrs() );
        }
        
        if(identityData != null)
            e.getAttrs().put("image", new Attribute<>( identityData, "image" ));
        
        List<Date> instants = ioDAO.getLastVisit(e);
        
        e.getAttrs().put( "instants" , new Attribute<>(instants, "instants"));
    }
}
