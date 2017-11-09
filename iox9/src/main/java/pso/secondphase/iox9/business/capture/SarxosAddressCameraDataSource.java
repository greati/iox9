package pso.secondphase.iox9.business.capture;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import java.awt.Image;
import java.net.MalformedURLException;
import pso.secondphase.iox9.exception.FailedOpeningSourceException;

/**
 * Represents a camera accessible by some kind of address, like IP cameras.
 * 
 * Requires the address of the source, by which its data is accessible.
 * 
 * @author vitorgreati
 */
public class SarxosAddressCameraDataSource extends IdentityDataSource<Image> {

    private final String address;
    private Webcam webcam;
    
    static {
        Webcam.setDriver(new IpCamDriver());
    }
    
    public SarxosAddressCameraDataSource(String id, String address) throws FailedOpeningSourceException {
        super(id);
        this.address = address;
    }
    
    @Override
    protected void _setup() throws FailedOpeningSourceException {
        try {
            IpCamDeviceRegistry.register(this.id, this.address, IpCamMode.PUSH);
            
            for(Webcam c : Webcam.getWebcams()) {
                if (c.getName().equals(this.id)) {
                    webcam = c;
                    break;
                }
            }
            
            if (webcam != null) {
                webcam.open();
                if (!webcam.isOpen()) {
                    throw new FailedOpeningSourceException();
                }
            } else {
                throw new FailedOpeningSourceException();
            }
            
        } catch (MalformedURLException ex) {
            throw new FailedOpeningSourceException();
        }  
    }
    
    @Override
    protected Image _getData() {
        return webcam.getImage();
    }
    
}
