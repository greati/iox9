package pso.secondphase.iox9.business.capture;

import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.exception.FailedOpeningSourceException;
import pso.secondphase.iox9.exception.InvalidEntityException;

/**
 * Thread for getting raw identity data from identity data sources.
 * 
 * Has an identity data source and a processor in order to
 * obtain the raw identity data and send it to the
 * right processing method.
 * 
 * @author vitorgreati
 * @param <IdentityDataType> Identity data type.
 */
public class IdentityDataReceiver<IdentityDataType> extends Thread {
    
    private volatile boolean active;
    private final Long sleepTime;
    private final IdentityDataSource<IdentityDataType> identityDataSource;
    private final EntityProcessor<IdentityDataType> processor;

    public IdentityDataReceiver(IdentityDataSource<IdentityDataType> identityDataSource,
            EntityProcessor processor, Long sleepTime) {
        this.active = true;
        this.sleepTime = sleepTime;
        this.identityDataSource = identityDataSource;
        this.processor = processor;
        
        try {
            this.identityDataSource.setup();
        } catch (FailedOpeningSourceException ex) {
            Logger.getLogger(IdentityDataReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
        
    @Override
    public void run() {
        while (isActive()) {

            processor.process(identityDataSource.getData());

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(IdentityDataReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    
}
