/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.model;

/**
 *
 * @author vinihcampos
 * @param <T>
 */
public class Attribute<T> {
    
    public T value;
    public Class type;
    public String description;
    public Boolean persistible;
    
    public Attribute(T value, String description, Boolean persistible){
        this.value = value;
        this.type = value.getClass();
        this.description = description;
        this.persistible = persistible;
    }
    
    public Attribute(T value, String description){
        this.value = value;
        this.type = value.getClass();
        this.description = description;
        this.persistible = true;
    }    
}
