/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.control;

import pso.secondphase.iox9.business.capture.IdentityDataReceiver;
import pso.secondphase.iox9.business.capture.InDataSourceSavedImage;
import pso.secondphase.iox9.business.capture.SarxosAddressCameraDataSource;
import pso.secondphase.iox9.business.processing.OpenCVUFRNLicensePlateReconizer;
import pso.secondphase.iox9.business.processing.VehicleInProcessor;
import pso.secondphase.iox9.business.processing.VehicleOutProcessor;
import pso.secondphase.iox9.dao.JDBCEntityDAO;
import pso.secondphase.iox9.dao.JDBCIORecordDAO;
import pso.secondphase.iox9.model.SimpleIORecordType;
import pso.secondphase.iox9.model.VehicleFactory;
import pso.secondphase.rapx9.util.InMemoryVehicleDatabase;
import pso.secondphase.rapx9.view.VehicleInPanel;
import pso.secondphase.rapx9.view.VehicleOutPanel;

/**
 *
 * @author vitorgreati
 */
public class MainControl {
    

    // Create threads
    public static void main(String args[]) {
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
        InMemoryVehicleDatabase database = new InMemoryVehicleDatabase();
        InDataSourceSavedImage inCameraDs = new InDataSourceSavedImage("entrance_camera", database);
        InDataSourceSavedImage outCameraDs = new InDataSourceSavedImage("exit_camera", database);

        // Threads
        IdentityDataReceiver inDataReceiver = new IdentityDataReceiver(inCameraDs, inProcessor, new Long(1000));
        IdentityDataReceiver outDataReceiver = new IdentityDataReceiver(outCameraDs, outProcessor, new Long(5000));
        
        // Registrar views
        inProcessor.addObserver(inPanel);
        outProcessor.addObserver(outPanel);
        
        // Start thread
        inDataReceiver.start();
        inDataReceiver.setDaemon(true);
        outDataReceiver.start();
        outDataReceiver.setDaemon(true);

    }
    
}
