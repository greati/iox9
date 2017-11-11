package pso.secondphase.iox9.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 * Load configuration from YAML file using the snakeyaml library.
 * 
 * @author vitorgreati
 */
public class SnakeYamlConfigurationLoader implements ConfigurationLoader {

    @Override
    public void load(String uri) {
        InputStream input = null;
        try {
            input = new FileInputStream(uri);
            Yaml yaml = new Yaml();
            
            Object data = yaml.load(input);
            
            ApplicationConfiguration appConfig = new ApplicationConfiguration(); 
                        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SnakeYamlConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(SnakeYamlConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
    
}
