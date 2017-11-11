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
public class OutDataSourceSavedImage extends IdentityDataSource<Image> {
    
    private final InMemoryVehicleDatabase database;
    
    public OutDataSourceSavedImage(String id, InMemoryVehicleDatabase database){
        super(id);
        this.database = database;
    }

    @Override
    synchronized public Image _getData() throws InvalidDataReceivedException {
        BufferedImage img = null;
        
        if(database.getInsideCars().isEmpty()) return null;

        int pos = (int)(Math.random() * database.getInsideCars().size());
        String imgString = database.getInsideCars().get(pos);
        try {
            img = ImageIO.read(new File(imgString));
            database.getInsideCars().remove(pos);
            database.getOutsideCars().add(imgString);
        } catch (IOException ex) {
            Logger.getLogger(OutDataSourceSavedImage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return img;
    }

    @Override
    protected void _setup() throws FailedOpeningSourceException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
