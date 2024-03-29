/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.model;

import java.util.Objects;

/**
 * The simplest types of IO records, IN and OUT.
 * 
 * @author vitorgreati
 */
public enum SimpleIORecordType implements IORecordType {

    IN(new Long(0)),
    OUT(new Long(1));

    Long ioRecordType;

    SimpleIORecordType(Long ioRecordType) {
        this.ioRecordType = ioRecordType;
    }
    
    @Override
    public Long getIORecordType() {
        return this.ioRecordType;
    }
    
    public static SimpleIORecordType getEnumEntry(Long value){
        for(SimpleIORecordType e: SimpleIORecordType.values()) {
          if(Objects.equals(value, e.getIORecordType())) {
            return e;
          }
        }
        return null;// not found
    }
    
}
