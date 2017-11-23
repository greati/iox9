/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.control;

import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.business.capture.IdentityDataReceiver;
import pso.secondphase.iox9.business.capture.InDataSourceSavedImage;
import pso.secondphase.iox9.business.capture.OutDataSourceSavedImage;
import pso.secondphase.iox9.business.notification.MaxCapacityNotificationAgent;
import pso.secondphase.iox9.business.notification.NotifierChainSingleton;
import pso.secondphase.iox9.business.processing.OpenCVUFRNLicensePlateReconizer;
import pso.secondphase.iox9.business.processing.InformationCollector;
import pso.secondphase.iox9.business.processing.InformationCollectorThread;
import pso.secondphase.iox9.business.processing.SinespInformationCollector;
import pso.secondphase.iox9.business.processing.VehicleInProcessor;
import pso.secondphase.iox9.business.processing.VehicleOutProcessor;
import pso.secondphase.iox9.business.statistics.CountByHoursInDayStatistics;
import pso.secondphase.iox9.business.statistics.CountByWeekDaysStatistics;
import pso.secondphase.iox9.business.statistics.PriceByHoursInDayStatistics;
import pso.secondphase.iox9.business.statistics.StatisticsChainSingleton;
import pso.secondphase.iox9.dao.JDBCEntityDAO;
import pso.secondphase.iox9.dao.JDBCIORecordDAO;
import pso.secondphase.iox9.model.SimpleIORecordType;
import pso.secondphase.iox9.model.GeneralModelFactory;
import pso.secondphase.rapx9.util.InMemoryVehicleDatabase;
import pso.secondphase.rapx9.view.ChartsPanel;
import pso.secondphase.rapx9.view.NotificationPanel;
import pso.secondphase.rapx9.view.VehicleInPanel;
import pso.secondphase.rapx9.view.VehicleOutPanel;

/**
 *
 * @author vitorgreati
 */
public class MainControl {
    // Instantiate view classes
    //private VehicleInPanel inPanel;
    //private VehicleOutPanel outPanel;
    
    // Processors
    private VehicleInProcessor inProcessor;
    private VehicleOutProcessor outProcessor;
    
    // Sources
    private InMemoryVehicleDatabase database;
    private InDataSourceSavedImage inCameraDs;
    private OutDataSourceSavedImage outCameraDs;
    
    // Notifiers
    MaxCapacityNotificationAgent maxNot;
    
    //Statistics
    CountByHoursInDayStatistics countHours;
    CountByWeekDaysStatistics countStat;
    PriceByHoursInDayStatistics priceHours;
    
    // Threads
    IdentityDataReceiver inDataReceiver;
    IdentityDataReceiver outDataReceiver;
    
    // Information collector
    InformationCollector collector;
    

    public MainControl(VehicleInPanel inPanel, VehicleOutPanel outPanel, ChartsPanel chartsPanel, NotificationPanel notificationPanel){
                
        // Start Information collector thread
        collector = new SinespInformationCollector();
        InformationCollectorThread.getInstance(collector, new JDBCEntityDAO(), 30000).setDaemon(true);
        
        try {
            InformationCollectorThread.getInstance().start();
        } catch (Exception ex) {
            Logger.getLogger(MainControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.inProcessor = 
                new VehicleInProcessor(SimpleIORecordType.IN, new GeneralModelFactory(),
                new OpenCVUFRNLicensePlateReconizer(), new JDBCEntityDAO(),
                new JDBCIORecordDAO());
        
        this.outProcessor = 
                new VehicleOutProcessor(SimpleIORecordType.OUT, new GeneralModelFactory(),
                new OpenCVUFRNLicensePlateReconizer(), new JDBCEntityDAO(),
                new JDBCIORecordDAO());
        
        database = InMemoryVehicleDatabase.getInstance();
        inCameraDs = new InDataSourceSavedImage("entrance_camera", database);
        outCameraDs = new OutDataSourceSavedImage("exit_camera", database);
        
        maxNot = new MaxCapacityNotificationAgent(null);
        
        // Set chain of notifiers
        NotifierChainSingleton.getInstance().setNotifierHead(maxNot);
        
        inDataReceiver = new IdentityDataReceiver(inCameraDs, inProcessor, new Long(1000));
        outDataReceiver = new IdentityDataReceiver(outCameraDs, outProcessor, new Long(5000));
        
        priceHours = new PriceByHoursInDayStatistics(null);
        countHours = new CountByHoursInDayStatistics(priceHours);
        countStat = new CountByWeekDaysStatistics(countHours);        
        
        // Registrar views
        inProcessor.addObserver(inPanel);
        outProcessor.addObserver(outPanel);
        NotifierChainSingleton.getInstance().addObserver(notificationPanel);
              
        countStat.addObserver(inPanel);
        priceHours.addObserver(chartsPanel);
        countHours.addObserver(chartsPanel);
        inProcessor.addObserver(chartsPanel);
        outProcessor.addObserver(chartsPanel);
        StatisticsChainSingleton.getInstance().setStatisticsHead(countStat);
        
        run();
    }
    
    private void run(){
        // Start thread
        inDataReceiver.setDaemon(true);
        inDataReceiver.start();
        outDataReceiver.setDaemon(true);
        outDataReceiver.start();
    }

    /*// Create threads
    public static void main(String args[]) {
        // Instantiate view classes
        VehicleInPanel inPanel = new VehicleInPanel();
        VehicleOutPanel outPanel = new VehicleOutPanel();

        // Processors
        VehicleInProcessor inProcessor = 
                new VehicleInProcessor(SimpleIORecordType.IN, new GeneralModelFactory(),
                new OpenCVUFRNLicensePlateReconizer(), new JDBCEntityDAO(),
                new JDBCIORecordDAO());

        VehicleOutProcessor outProcessor = 
                new VehicleOutProcessor(SimpleIORecordType.OUT, new GeneralModelFactory(),
                new OpenCVUFRNLicensePlateReconizer(), new JDBCEntityDAO(),
                new JDBCIORecordDAO());

        // Sources
        InMemoryVehicleDatabase database = new InMemoryVehicleDatabase();
        InDataSourceSavedImage inCameraDs = new InDataSourceSavedImage("entrance_camera", database);
        OutDataSourceSavedImage outCameraDs = new OutDataSourceSavedImage("exit_camera", database);

        // Notifiers
        MaxCapacityNotificationAgent maxNot = new MaxCapacityNotificationAgent(null);
        
        // Set chain of notifiers
        NotifierChainSingleton.getInstance().setNotifierHead(maxNot);
        
        // Statistics
        CountByHoursInDayStatistics countHours = new CountByHoursInDayStatistics(null);
        CountByWeekDaysStatistics countStat = new CountByWeekDaysStatistics(countHours);
        countStat.addObserver(inPanel);
        countHours.addObserver(inPanel);
        StatisticsChainSingleton.getInstance().setStatisticsHead(countStat);
        
        // Threads
        IdentityDataReceiver inDataReceiver = new IdentityDataReceiver(inCameraDs, inProcessor, new Long(1000));
        IdentityDataReceiver outDataReceiver = new IdentityDataReceiver(outCameraDs, outProcessor, new Long(5000));
        
        // Registrar views
        inProcessor.addObserver(inPanel);
        outProcessor.addObserver(inPanel);
        outProcessor.addObserver(outPanel);
        NotifierChainSingleton.getInstance().addObserver(outPanel);
        
        // Start thread
        inDataReceiver.setDaemon(true);
        inDataReceiver.start();
        outDataReceiver.setDaemon(true);
        outDataReceiver.start();

        while (true) 
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    */
}
