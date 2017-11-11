/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.model;

/**
 * Represents a vehicle situation.
 * 
 * @author vitorgreati
 */
public enum Situation {
    REGULAR("Regular"),
    IRREGULAR("Irregular");
    
    Situation(String situationText) {
        this.situationText = situationText;
    }
    
    String situationText;

    public String getSituationText() {
        return this.situationText;
    }
    
}
