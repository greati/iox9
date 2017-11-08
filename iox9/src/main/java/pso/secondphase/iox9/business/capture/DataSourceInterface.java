package pso.secondphase.iox9.business.capture;

/**
 *
 * Interface for capturing the raw output from a sensor.
 * 
 * Given a sensor, like a camera, it is necessary to
 * capture its data. This interface allows dealing
 * with different data sources (sensors).
 * 
 * @author Vitor Greati
 * @param <DataType> Final data representation, e.g. Image.
 */
public interface DataSourceInterface <DataType> {
    
    /**
     * Returns the data captured from the source.
     * 
     * @return The data.
     */
    public DataType getSourceOutput();
    
}
