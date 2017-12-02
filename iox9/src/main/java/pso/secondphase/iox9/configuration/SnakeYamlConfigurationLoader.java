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
import pso.secondphase.iox9.business.notification.NotificationAgent;
import pso.secondphase.iox9.business.notification.NotifierChainSingleton;
import pso.secondphase.iox9.business.processing.EntityProcessor;
import pso.secondphase.iox9.business.processing.EntityRecognizer;
import pso.secondphase.iox9.business.processing.InformationCollector;
import pso.secondphase.iox9.business.processing.InformationCollectorThread;
import pso.secondphase.iox9.business.processing.Observer;
import pso.secondphase.iox9.business.statistics.StatisticsChainSingleton;
import pso.secondphase.iox9.business.statistics.StatisticsProcessor;
import pso.secondphase.iox9.dao.EntityDAO;
import pso.secondphase.iox9.dao.IORecordDAO;
import pso.secondphase.iox9.model.IORecordType;
import pso.secondphase.iox9.model.ModelAbstractFactory;
import pso.secondphase.iox9.model.SimpleIORecordType;

/**
 * Load configuration from YAML file using the snakeyaml library.
 * 
 * @author vitorgreati
 */
public class SnakeYamlConfigurationLoader implements ConfigurationLoader { //implements ConfigurationLoader {

    @Override
    public void load(String uri) {
        InputStream input = null;
        try {
            input = new FileInputStream(uri);
            Yaml yaml = new Yaml();
            
            // Load file
            Map data = (Map) yaml.load(input);

            // General attributes
            ApplicationConfiguration.getInstance().getParameters().put("capacity", data.get("capacity"));
            ApplicationConfiguration.getInstance().getParameters().put("almost_full", data.get("almost_full"));
            
            if (data.get("start_class") != null) {
                ApplicationConfiguration.getInstance().setInitialView(
                      (StartableView) Class.forName((String)data.get("start_class")).newInstance()
                );
            }
            
            Class modelFactoryClass = null;
            Class entityDaoClass = null;
            Class ioRecordDaoClass = null;
            
            Class collectorClass = null;
            
            try {
                modelFactoryClass = Class.forName((String) data.get("model_factory_class"));
                entityDaoClass = Class.forName((String) data.get("entity_dao_class"));
                ioRecordDaoClass = Class.forName((String) data.get("iorecord_dao_class"));
                
                if (data.get("collector") != null) {
                    Map collectorParams = (Map) data.get("collector");
                    collectorClass = Class.forName((String) collectorParams.get("class"));
                    Long sleepTime = ((Integer)collectorParams.get("sleep_time")).longValue();
                    InformationCollectorThread.getInstance().setWaitingTime(sleepTime);
                    InformationCollectorThread.getInstance().setCollector((InformationCollector) collectorClass.newInstance());
                    InformationCollectorThread.getInstance().setEntityDAO((EntityDAO) entityDaoClass.newInstance());
                }
                
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
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
                    if (parametersMap != null) {
                        for (Object parameter : parametersMap.entrySet()) {
                            Map.Entry<String,Object> entry = (Map.Entry<String,Object>) parameter;
                            sourceInstance.getParameters().put(entry.getKey(), entry.getValue());
                        }
                    }
                    
                    // Recognizer
                    EntityRecognizer recognizer = (EntityRecognizer) 
                            Class.forName((String)sourceDescription.get("recognizer_class")).newInstance();
                    
                    Long ioType = ((Integer) sourceDescription.get("io_type")).longValue();
                    
                    // Entity Processor
                    String processorClass = (String) sourceDescription.get("processor_class");
                    EntityProcessor entityProcessor = (EntityProcessor) Class.forName(processorClass)
                            .getConstructor(IORecordType.class, ModelAbstractFactory.class, EntityRecognizer.class, 
                                    EntityDAO.class, IORecordDAO.class)
                            .newInstance(SimpleIORecordType.getEnumEntry(ioType), modelFactoryClass.newInstance(), recognizer, 
                                    entityDaoClass.newInstance(), ioRecordDaoClass.newInstance());
                
                    ApplicationConfiguration.getInstance().getIdentityProcessors().put(sourceId, entityProcessor);
                    
                    // Identity Receiver
                    Long sleepTime = ((Integer) sourceDescription.get("sleep_time")).longValue();
                    
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
            StatisticsProcessor last = null;
            for (Object statistic : (List<?>)data.get("statictics_processors")) {
                Map statisticProcessor = (Map) statistic;
                String id = (String) statisticProcessor.get("id");
                String statClass = (String) statisticProcessor.get("class");
                
                try {
                    StatisticsProcessor statisticsProcessor = (StatisticsProcessor) Class.forName(statClass)
                            .getConstructor(StatisticsProcessor.class).newInstance(last);
                    statisticsProcessor.setId(id);
                    
                    ApplicationConfiguration.getInstance().getStatisticsProcessors()
                            .put(statisticsProcessor.getId(), statisticsProcessor);
                    
                    last = statisticsProcessor;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
                        IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                    Logger.getLogger(SnakeYamlConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            StatisticsChainSingleton.getInstance().setStatisticsHead(last);
            
            // Notification processors
            NotificationAgent lastNot = null;
            for (Object view : (List<?>)data.get("notification_agents")) {
                Map notificationDescription = (Map) view;
                String id = (String) notificationDescription.get("id");
                String notClass = (String) notificationDescription.get("class");
                try {
                    NotificationAgent notAgent = (NotificationAgent) Class.forName(notClass)
                            .getConstructor(NotificationAgent.class).newInstance(lastNot);
                    lastNot = notAgent;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
                        IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                    Logger.getLogger(SnakeYamlConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            NotifierChainSingleton.getInstance().setNotifierHead(lastNot);
            
             // Views
            for (Object view : (List<?>)data.get("views")) {
                Map viewDescription = (Map) view;
                
                String className = (String) viewDescription.get("class");
                String id = (String) viewDescription.get("id");
                Boolean notifiable = (Boolean) viewDescription.get("notifiable");
                Boolean initial = viewDescription.get("initial") != null ? 
                        (Boolean) viewDescription.get("initial") : false;
                
                System.out.println(className);
                
                try {
                    Observer objView = (Observer) Class.forName(className).newInstance();
                    
                    objView.setId(id);
                    
                    ApplicationConfiguration.getInstance().getViews().put(id, objView);
                    
                    if (initial)
                        ApplicationConfiguration.getInstance().setInitialView((StartableView) objView);
                    
                    if (notifiable)
                        NotifierChainSingleton.getInstance().addObserver(objView);
                    
                    // Register as processors observer
                    if (viewDescription.get("processors_of_interest") != null) {
                        for (Object poi : (List<?>) viewDescription.get("processors_of_interest")) {
                            String procId = (String) poi;
                            ApplicationConfiguration.getInstance().getIdentityProcessors().get(procId)
                                    .addObserver(objView);
                        }
                    }
                    
                    // Register as statistics observer
                    if (viewDescription.get("statistics_of_interest") != null) {
                        for (Object stat : (List<?>) viewDescription.get("statistics_of_interest")) {
                            String statId = (String) stat;
                            ApplicationConfiguration.getInstance().getIdentityProcessors().get(statId)
                                .addObserver(objView);
                        }                                        
                    }
                    
                    
                    
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(SnakeYamlConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SnakeYamlConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(SnakeYamlConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SnakeYamlConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
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
