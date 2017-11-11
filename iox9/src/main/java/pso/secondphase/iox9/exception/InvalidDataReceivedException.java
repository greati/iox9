/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.exception;

/**
 * Thrown when the source sends invalid data.
 * 
 * @author vitorgreati
 */
public class InvalidDataReceivedException extends Exception {
    
    public InvalidDataReceivedException() {
        super("No data received from the source.");
    }
}
