package pso.secondphase.iox9.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;
import pso.secondphase.iox9.business.capture.IdentityDataReceiver;
import pso.secondphase.iox9.business.capture.IdentityDataSource;
import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.business.processing.EntityRecognizer;
import pso.secondphase.iox9.dao.EntityDAO;
import pso.secondphase.iox9.dao.IORecordDAO;
import pso.secondphase.iox9.model.IORecordType;
import pso.secondphase.iox9.model.ModelAbstractFactory;

/**
 * Load configuration from YAML file using the snakeyaml library.
 * 
 * @author vitorgreati
 */
public class SnakeYamlConfigurationLoader { //implements ConfigurationLoader {

    //@Override
    public static void load(String uri) {
        InputStream input = null;
        try {
            input = new FileInputStream(uri);
            Yaml yaml = new Yaml();
            
            // Load file
            Map data = (Map) yaml.load(input);

            // General attributes
            System.out.println("capacity="+data.get("capacity"));
            System.out.println("almost_full="+data.get("almost_full"));
            
            Class modelFactoryClass = null;
            Class entityDaoClass = null;
            Class ioRecordDaoClass = null;
            
            try {
                modelFactoryClass = Class.forName((String) data.get("model_factory_class"));
                entityDaoClass = Class.forName((String) data.get("entity_dao_class"));
                ioRecordDaoClass = Class.forName((String) data.get("iorecord_dao_class"));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SnakeYamlConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Sources
            for (Object source : (List<?>)data.get("sources")) {
                Map sourceDescription = (Map) source;
                
                String sourceId = (String) sourceDescription.get("id");
                String sourceClass = (String) sourceDescription.get("source_class");
                
                try {
                    // Instantiate
                    IdentityDataSource sourceInstance = (IdentityDataSource) Class.forName(sourceClass).getConstructor(String.class).newInstance(sourceId);
                    
                    Boolean sourceActive = (Boolean) sourceDescription.get("active");
                    sourceInstance.setActive(sourceActive);
                    
                    // Source parameters
                    Map parametersMap = (Map) sourceDescription.get("parameters");
                    for (Object parameter : parametersMap.entrySet()) {
                        Map.Entry<String,Object> entry = (Map.Entry<String,Object>) parameter;
                        sourceInstance.getParameters().put(entry.getKey(), entry.getValue());
                    }
                    
                    // Recognizer
                    EntityRecognizer recognizer = (EntityRecognizer) 
                            Class.forName((String)sourceDescription.get("recognizer_class")).newInstance();
                    
                    Long ioType = (Long) sourceDescription.get("io_type");
                    
                    // Entity Processor
                    String processorClass = (String) sourceDescription.get("processor_class");
                    EntityProcessor entityProcessor = (EntityProcessor) Class.forName("processorClass")
                            .getConstructor(IORecordType.class, ModelAbstractFactory.class, EntityRecognizer.class, 
                                    EntityDAO.class, IORecordDAO.class)
                            .newInstance(ioType, modelFactoryClass.newInstance(), recognizer, 
                                    entityDaoClass.newInstance(), ioRecordDaoClass.newInstance());
                
                    // Identity Receiver
                    Long sleepTime = (Long) sourceDescription.get("sleep_time");
                    
                    IdentityDataReceiver receiver = new IdentityDataReceiver(sourceInstance, 
                            entityProcessor, sleepTime);
                    
                    ApplicationConfiguration.getInstance().getSourceReceivers().put(sourceId, receiver);
                    
                } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException 
                        | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(SnakeYamlConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println(sourceDescription);
            }
            
            // Statistics processors
            for (Object statistic : (List<?>)data.get("statictics_processors")) {
                Map statisticProcessor = (Map) statistic;
                System.out.println(statisticProcessor);
            }
            
             // View processors
            for (Object view : (List<?>)data.get("views")) {
                Map viewDescription = (Map) view;
                System.out.println(view);
            }
            
            // Notification processors
            for (Object view : (List<?>)data.get("notification_agents")) {
                Map notificationDescription = (Map) view;
                System.out.println(notificationDescription);
            }
            
            
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

    public static void main(String[] args) {
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        load(path + "/src/main/resources/iox9config.yaml");
    }
    
}
