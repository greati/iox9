package pso.secondphase.iox9.business.capture;

import pso.secondphase.iox9.exception.FailedOpeningSourceException;
import pso.secondphase.iox9.exception.InvalidDataReceivedException;

/**
 * Represent a source of identity data along with the data capture method.
 * 
 * Given a sensor, like a camera, it is necessary to
 * capture its data (images). This class allows dealing
 * with different data sources, identifying them and
 * allowing capturing their data.
 * 
 * @author Vitor Greati
 * @param <IdentityDataType> Final data representation.
 */
public abstract class IdentityDataSource<IdentityDataType> {
    
    protected final String id;
    protected boolean active;
    protected boolean ready;

    public IdentityDataSource(String id) {
        this.id = id;
        this.active = true;
        this.ready = false;
    }
    
    public String getId() {
        return this.id;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
     * Returns the data captured from the source.
     * 
     * @return The data.
     * @throws pso.secondphase.iox9.exception.InvalidDataReceivedException
     */
    public IdentityDataType getData() throws InvalidDataReceivedException {
        if (this.ready)
            return _getData();
        return null;
    }
    
    protected abstract IdentityDataType _getData() throws InvalidDataReceivedException;
    
    /**
     * Set the source connection up for getting the data.
     * 
     * @throws pso.secondphase.iox9.exception.FailedOpeningSourceException
     */
    public void setup() throws FailedOpeningSourceException {
        try {
            _setup();
            this.ready = true;
        } catch (FailedOpeningSourceException e) {
            this.ready = false;
            throw e;
        }
    }
    
    /**
     * Set the source connection up for getting the data.
     * 
     * @throws pso.secondphase.iox9.exception.FailedOpeningSourceException
     */
    protected abstract void _setup() throws FailedOpeningSourceException;
    
}
