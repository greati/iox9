/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.model;

/**
 * The simplest types of IO records.
 * 
 * @author vitorgreati
 */
public enum SimpleIORecordType implements IORecordType {

    IN("0"), OUT("1");

    String ioRecordType;

    SimpleIORecordType(String ioRecordType) {
        this.ioRecordType = ioRecordType;
    }
    
    @Override
    public String getIORecordType() {
        return this.ioRecordType;
    }
    
}
