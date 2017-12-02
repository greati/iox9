/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import pso.secondphase.iox9.business.capture.IdentityDataReceiver;
import pso.secondphase.iox9.business.processing.InformationCollectorThread;

/**
 *
 * @author vitorgreati
 */
public class IOx9Start {
    
    public static void main(String[] args) throws InterruptedException {
        
        ConfigurationLoader configLoader = new SnakeYamlConfigurationLoader();;
        
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        configLoader.load(path + "/src/main/resources/iox9config.yaml");
        
        System.out.println("Identity processors: " + ApplicationConfiguration.getInstance().getIdentityProcessors().size());
        
        for (IdentityDataReceiver idr : ApplicationConfiguration.getInstance().getSourceReceivers().values()) {
            idr.setDaemon(true);
            idr.start();
        }
        
        if (InformationCollectorThread.getInstance().getCollector() != null) {
            InformationCollectorThread.getInstance().setDaemon(true);
            InformationCollectorThread.getInstance().start();
        }
        
        if(ApplicationConfiguration.getInstance().getInitialView() != null)
            ApplicationConfiguration.getInstance().getInitialView().start();
        
        while(true)
            Thread.sleep(10);
    }
    
}
