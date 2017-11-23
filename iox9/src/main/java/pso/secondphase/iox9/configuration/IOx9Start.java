/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import pso.secondphase.iox9.business.capture.IdentityDataReceiver;

/**
 *
 * @author vitorgreati
 */
public class IOx9Start {
    
    private static ConfigurationLoader configLoader;
    
    public IOx9Start() {
        IOx9Start.configLoader = new SnakeYamlConfigurationLoader();
    }
    
    public static void main(String[] args) {
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        configLoader.load(path + "/src/main/resources/iox9config.yaml");
        
        for (IdentityDataReceiver idr : ApplicationConfiguration.getInstance().getSourceReceivers().values()) {
            idr.setDaemon(true);
            idr.start();
        }
    }
    
}
