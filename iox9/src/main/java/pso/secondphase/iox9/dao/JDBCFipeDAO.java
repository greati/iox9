/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import pso.secondphase.iox9.model.Attribute;
import pso.secondphase.iox9.model.Entity;

/**
 *
 * @author vitorgreati
 */
public class JDBCFipeDAO {
    
    private static JDBCFipeDAO instance;
    
    public static JDBCFipeDAO getInstance() {
        if (instance == null)
            instance = new JDBCFipeDAO();
        return instance;
    }
    
    private JDBCFipeDAO() {}
    
    public void getPriceVehicle(Entity obj){
        Connection c = SimpleJDBCFipeConnectionManager.getConnection();
        if (c == null)
            return;
        try {
            
            String words [] = ((String) obj.getAttrs().get("model").value).split(" ");
            String query = "SELECT preco, ";
            
            for(int i = 0; i < words.length; ++i){
                query += "(CASE WHEN name ILIKE ? THEN 1 ELSE 0 END) ";
                if(i == words.length - 1) query += " AS ocurrencies ";
                else query += "+ ";
            }
            
            query += "FROM model WHERE (";
            for(int i = 0; i < words.length; ++i){
                query += "name ILIKE ? ";
                if(i == words.length - 1) query += ") ";
                else query += "OR ";
            }
            
            query += "AND ano_modelo = ? AND marca ILIKE ? ";
            query += "GROUP BY(name, preco) ORDER BY ocurrencies DESC;";
            
            PreparedStatement stm = c.prepareStatement(query);
            
            for(int i = 0; i < (words.length * 2); ++i){
                stm.setString(i+1, "%" + words[i % words.length] + "%");
            }
            
            stm.setLong(2 * words.length + 1, (Long) obj.getAttrs().get("yearModel").value);
            stm.setString(2 * words.length + 2, "%" + obj.getAttrs().get("brand").value + "%");
            ResultSet rs = stm.executeQuery();
            
            int count = 0;
            int max = -1;
            double sum = 0;
            while (rs.next()) {                
                if(max == -1){
                    max = rs.getInt("ocurrencies");
                    count += 1;
                    sum += rs.getDouble("preco");
                }else if(max == rs.getInt("ocurrencies")){
                    count += 1;
                    sum += rs.getDouble("preco");
                }else{
                    break;
                }
            }
            
            obj.getAttrs().put("fipeDate", new Attribute<>(new Date(), "fipeDate"));
            obj.getAttrs().put("price", new Attribute<Double>((Double) sum/count, "price"));
            
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCFipeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
