/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.rapx9.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vitorgreati
 */
public class InMemoryVehicleDatabase {

    private volatile List<String> outsideCars;
    private volatile List<String> insideCars;

    public InMemoryVehicleDatabase() {
        outsideCars = new ArrayList<>();
        insideCars = new ArrayList<>();
        
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        
        //Populate outside cars
        for(int i = 1; i <= 29; ++i)
            outsideCars.add(path + "/src/main/resources/img/carro"+i+".jpg");

    }

    /**
     * @return the outsideCars
     */
    public List<String> getOutsideCars() {
        return outsideCars;
    }

    /**
     * @param outsideCars the outsideCars to set
     */
    public void setOutsideCars(List<String> outsideCars) {
        this.outsideCars = outsideCars;
    }

    /**
     * @return the insideCars
     */
    public List<String> getInsideCars() {
        return insideCars;
    }

    /**
     * @param insideCars the insideCars to set
     */
    public void setInsideCars(List<String> insideCars) {
        this.insideCars = insideCars;
    }

}
