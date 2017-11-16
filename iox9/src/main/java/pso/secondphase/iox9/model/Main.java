/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author vinihcampos
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Attribute<?>> array = new ArrayList<>();
        
        array.add( new Attribute<>("V", "Name") );
        array.add( new Attribute<>(10, "idade"));
        array.add( new Attribute<>(10.5, "nota"));
        array.add( new Attribute<>(new Date(), "data"));
        
        for(int i = 0; i < array.size(); ++i){
            System.out.print(array.get(i).type);
            System.out.print(" - " + array.get(i).value);
            if( array.get(i).value instanceof String){
                System.out.println(" - String");
            }else if( array.get(i).value instanceof Integer){
                System.out.println(" - Integer");
            }else if( array.get(i).value instanceof Double){
                System.out.println(" - Double");
            }else if( array.get(i).value instanceof Date){
                System.out.println(" - Date");
            }
        }
    }
}
