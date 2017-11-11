/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.control;

import pso.secondphase.iox9.business.capture.IdentityDataReceiver;
import pso.secondphase.iox9.business.capture.SarxosAddressCameraDataSource;
import pso.secondphase.iox9.business.processing.OpenCVUFRNLicensePlateReconizer;
import pso.secondphase.iox9.business.processing.VehicleInProcessor;
import pso.secondphase.iox9.business.processing.VehicleOutProcessor;
import pso.secondphase.iox9.dao.JDBCEntityDAO;
import pso.secondphase.iox9.dao.JDBCIORecordDAO;
import pso.secondphase.iox9.model.SimpleIORecordType;
import pso.secondphase.iox9.model.VehicleFactory;
import pso.secondphase.rapx9.view.VehicleInPanel;
import pso.secondphase.rapx9.view.VehicleOutPanel;

/**
 *
 * @author vitorgreati
 */
public class MainControl {
    
    // Instantiate view classes
    VehicleInPanel inPanel = new VehicleInPanel();
    VehicleOutPanel outPanel = new VehicleOutPanel();
        
    // Processors
    VehicleInProcessor inProcessor = 
            new VehicleInProcessor(SimpleIORecordType.IN, new VehicleFactory(),
            new OpenCVUFRNLicensePlateReconizer(), null, new JDBCEntityDAO(),
            new JDBCIORecordDAO());

    VehicleInProcessor outProcessor = 
            new VehicleInProcessor(SimpleIORecordType.OUT, new VehicleFactory(),
            new OpenCVUFRNLicensePlateReconizer(), null, new JDBCEntityDAO(),
            new JDBCIORecordDAO());
    
    // Sources
    //SarxosAddressCameraDataSource inCameraDs = new SarxosAddressCameraDataSource("entrance_camera", 
    //                                            "192.168.7.8:8080");
    //SarxosAddressCameraDataSource outCameraDs = new SarxosAddressCameraDataSource("entrance_camera", 
    //                                            "192.168.7.8:8080");
    
    // Threads
    //IdentityDataReceiver inDataReceiver = new IdentityDataReceiver(, inProcessor, Long.MIN_VALUE);
    
    // Create threads
    public static void main(String args[]) {
        
    }
    
}
