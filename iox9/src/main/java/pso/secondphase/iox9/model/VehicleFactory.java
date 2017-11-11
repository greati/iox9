/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.model;

import java.util.Date;

/**
 *
 * @author vitorgreati
 */
public class VehicleFactory implements ModelAbstractFactory {

    @Override
    public Entity createEntity(String identifier) {
        return new Vehicle(identifier);
    }

    @Override
    public IORecord createIORecord(Entity entity, Date instant, IORecordType type) {
        return new IORecord(entity, instant, type);
    }
    
}
