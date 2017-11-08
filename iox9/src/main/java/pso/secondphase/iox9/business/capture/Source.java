package pso.secondphase.iox9.business.capture;

/**
 * Class for capturing the raw output from a source.
 * 
 * Given a sensor, like a camera, it is necessary to
 * capture its data. This class allows dealing
 * with different data sources.
 * 
 * @author Vitor Greati
 * @param <DataType> Final data representation, e.g. Image.
 */
public abstract class Source<DataType> {
    
    private final String id;
    private boolean active;
    
    public Source(String id, Object parameters) {
        this.id = id;
        this.active = true;
        
        load(parameters);
    }
    
    private void load(Object parameters) {
        loadParameters(this, parameters);
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
     */
    public abstract DataType getOutput();
    
    /**
     * Load custom parameters.
     * 
     * @param s This object.
     * @param parameters Custom parameters from the configuration file.
     */
    protected abstract void loadParameters(Source s, Object parameters);
    
}
