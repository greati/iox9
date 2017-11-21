/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.model;

/**
 *
 * @author vinihcampos
 */
public class Report {
    private String plate;
    private String brand;
    private String model;
    private String color;
    private String price;
    private String situation;
    private Integer frequency;
    
    public Report(){
        this.plate = "";
        this.brand = "";
        this.model = "";
        this.color = "";
        this.price = "";
        this.situation = "";
        this.frequency = 0;
    }
    
    public Report(String plate, String brand, String model, String color, String price, String situation, Integer frequency){
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.price = price;
        this.situation = situation;
        this.frequency = frequency;
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
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the situation
     */
    public String getSituation() {
        return situation;
    }

    /**
     * @param situation the situation to set
     */
    public void setSituation(String situation) {
        this.situation = situation;
    }

    /**
     * @return the frequency
     */
    public Integer getFrequency() {
        return frequency;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }
        
}
