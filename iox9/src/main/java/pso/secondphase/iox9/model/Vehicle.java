/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.model;

import java.awt.Image;
import java.util.Date;

/**
 * Implements Vehicle model.
 * 
 * @author vitorgreati
 */
public class Vehicle extends Entity {

    private String plate;
    private String brand;
    private String model;
    private String color;
    private double value;
    private Situation situation;
    private Image image;
    private long year;
    private Date sinespDate;
    
    public Vehicle(String identifier) {
        super(identifier);
        this.plate = "";
        this.brand = "";
        this.model = "";
        this.color = "";
        this.value = 0;
        this.image = null;
        this.year = 0;
        this.situation = Situation.REGULAR;
    }
    
    public Vehicle(String identifier, String plate) {
        super(identifier);
        this.plate = plate;
        this.situation = Situation.REGULAR;
        this.year = 0;      
        this.brand = "";
        this.model = "";
        this.color = "";
        this.value = 0;
        this.image = null;
    }
    
    /**
     * @return the plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * @param plate the plate to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }
    
    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * @return the year
     */
    public long getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(long year) {
        this.year = year;
    }

    /**
     * @return the sinespDate
     */
    public Date getSinespDate() {
        return sinespDate;
    }

    /**
     * @param sinespDate the sinespDate to set
     */
    public void setSinespDate(Date sinespDate) {
        this.sinespDate = sinespDate;
    }

    /**
     * @return the situation
     */
    public Situation getSituation() {
        return situation;
    }

    /**
     * @param situation the situation to set
     */
    public void setSituation(Situation situation) {
        this.situation = situation;
    }
    
}
