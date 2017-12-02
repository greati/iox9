/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.capture;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pso.secondphase.iox9.exception.FailedOpeningSourceException;
import pso.secondphase.iox9.exception.InvalidDataReceivedException;
import pso.secondphase.rapx9.util.InMemoryVehicleDatabase;

/**
 *
 * @author vinihcampos
 */
public class InDataSourceSavedImage extends IdentityDataSource<Image> {
    
    private final InMemoryVehicleDatabase database;
    
    public InDataSourceSavedImage(String id){
        super(id);
        this.database = InMemoryVehicleDatabase.getInstance();
    }
    
    public InDataSourceSavedImage(String id,InMemoryVehicleDatabase database){
        super(id);
        this.database = database;
    }
        
    @Override
    synchronized public Image _getData() throws InvalidDataReceivedException {
        BufferedImage img = null;

        if(database.getOutsideCars().isEmpty()) return null;

        int pos = (int)(Math.random() * database.getOutsideCars().size());
        String imgString = database.getOutsideCars().get(pos);
        try {
           img = ImageIO.read(new File(imgString));
           database.getOutsideCars().remove(pos);
           database.getInsideCars().add(imgString);
        } catch (IOException ex) {
           Logger.getLogger(InDataSourceSavedImage.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return img;
    }

    @Override
    protected void _setup() throws FailedOpeningSourceException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
