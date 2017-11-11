/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.processing;

import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.business.notification.NotificationAgent;
import pso.secondphase.iox9.dao.EntityDAO;
import pso.secondphase.iox9.dao.IORecordDAO;
import pso.secondphase.iox9.exception.EntityNotFoundPersistedException;
import pso.secondphase.iox9.exception.FailAtPersistingException;
import pso.secondphase.iox9.model.Entity;
import pso.secondphase.iox9.model.IORecord;
import pso.secondphase.iox9.model.IORecordType;
import pso.secondphase.iox9.model.ModelAbstractFactory;
import pso.secondphase.iox9.model.SimpleIORecordType;

/**
 *
 * @author vitorgreati
 * @param <IdentityDataType>
 */
public abstract class OutEntityProcessor<IdentityDataType> extends EntityProcessor<IdentityDataType> {
    
    public OutEntityProcessor(ModelAbstractFactory modelAbstractFactory, EntityRecognizer entityRecognizer, 
            NotificationAgent notificationAgentChain, EntityDAO entityDAO, IORecordDAO ioDAO) {
        super(modelAbstractFactory, entityRecognizer, notificationAgentChain, entityDAO, ioDAO);
    }
    
    @Override
    protected IORecordType createRecordType() {
        return SimpleIORecordType.OUT;
    }
    
}
