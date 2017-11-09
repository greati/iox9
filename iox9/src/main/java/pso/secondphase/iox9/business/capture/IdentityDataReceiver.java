package pso.secondphase.iox9.business.capture;

import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.exception.FailedOpeningSourceException;

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

    public IdentityDataReceiver(IdentityDataSource<IdentityDataType> identityDataSource, Long sleepTime) {
        this.active = true;
        this.sleepTime = sleepTime;
        this.identityDataSource = identityDataSource;
        
        try {
            this.identityDataSource.setup();
        } catch (FailedOpeningSourceException ex) {
            Logger.getLogger(IdentityDataReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
        
    @Override
    public void run() {
        while (isActive()) {

            IdentityDataType data = identityDataSource.getData();
            
            //TODO
            //updater.update(ds.getSourceOutput());
 
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
