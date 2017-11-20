/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.processing;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pso.secondphase.iox9.model.Attribute;
import pso.secondphase.iox9.model.Entity;
import sinesp.SinespClient;

/**
 *
 * @author vitorgreati
 */
public class SinespInformationCollector implements InformationCollector {

    SinespClient sp;

    public SinespInformationCollector() {
        try {
            sp = new SinespClient();
        } catch (NoSuchAlgorithmException | KeyManagementException ex) {
            Logger.getLogger(SinespInformationCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void collect(Entity e) {
        try {
            String search = sp.search(e.getIdentifier());
            
            JSONParser parser = new JSONParser();
            
            Object obj = parser.parse(search);
            JSONObject jsonObj = (JSONObject) obj;            
            
            String brand = ((String) jsonObj.get("brand")).split("/")[0];
            String model = ((String) jsonObj.get("brand")).split("/")[1];

            if(brand.compareTo("I") == 0){
                brand = model.split(" ", 2)[0];
                model = model.split(" ", 2)[1];
            }
            e.getAttrs().put("model", new Attribute<>(model, "model"));
            e.getAttrs().put("brand", new Attribute<>(brand, "brand"));
            e.getAttrs().put("color", new Attribute<>((String) jsonObj.get("color"), "color"));
            e.getAttrs().put("situationCode", new Attribute<>((String) jsonObj.get("situationCode"), "situationCode"));
                        
            if(((String) jsonObj.get("situationCode")).compareTo("Sem restrição") == 0){
                e.getAttrs().put("situation", new Attribute<>(true, "situation"));
            }else{
                e.getAttrs().put("situation", new Attribute<>(false, "situation"));
            }

            e.getAttrs().put("yearModel", new Attribute<>((long) jsonObj.get("yearModel"), "yearModel"));
            e.getAttrs().put("sinespDate", new Attribute<>(new Date(), "sinespDate"));
                        
        } catch (NoSuchAlgorithmException | InvalidKeyException | IOException | JAXBException | ParseException ex) {
            Logger.getLogger(SinespInformationCollector.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
}
