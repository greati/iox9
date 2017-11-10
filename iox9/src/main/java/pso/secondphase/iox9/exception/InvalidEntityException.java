/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.exception;

/**
 * Thrown when an entity is invalid.
 * 
 * @author vitorgreati
 */
public class InvalidEntityException extends Exception {
    public InvalidEntityException() {
        super("Invalid entity.");
    }
}
