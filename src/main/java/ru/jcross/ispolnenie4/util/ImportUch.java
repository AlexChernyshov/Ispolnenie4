/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.util;

import ru.jcross.ispolnenie4.conn.DataSource;
import ru.jcross.ispolnenie4.models.Facialacc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Chernyshov
 */
public class ImportUch {
    private Facialacc buff = new Facialacc();

    private final DataSource ds = new DataSource() ;  
    private static Connection cPSQL;
    private static Connection cFB ;  
    private static Statement stmP;
    private static Statement stmFB;
    private static ResultSet rs;
   
    public void getCollectionURM(){
       String queryP = 
               "select "
               + "F.id, F.name as namep, F.name as namek, F.dateopen, F.dateclose "
               + "from FACIALACC_CLS F where  F.id  BETWEEN 825110000 AND 825119999;";
            try{
               // cPSQL = ds.connectPostgreSQL();
                cFB = ds.connectFireBird();
               // stmP = cPSQL.createStatement();
                stmFB = cFB.createStatement();
                rs  = stmFB.executeQuery(queryP);
                
                while (rs.next()){
                        buff.setId(rs.getInt("id"));
                        buff.setNamep(rs.getString("namep"));
                        buff.setNamek(rs.getString("namek"));
                        buff.setDateopen(rs.getDouble("dateopen"));
                        buff.setDateclose(rs.getDouble("dateclose"));                        
                        System.out.println(":> " + buff.toString());
                }
            } catch (SQLException sqlEx) {
               sqlEx.printStackTrace();
            }
            finally {
                try {ds.putConnectionFB(cFB);  } catch (Exception ignore) {}              
                try { stmFB.close(); } catch(SQLException se) { /*can't do anything */ }
                try { rs.close(); } catch(SQLException se) { /*can't do anything */ }             
            } 
   
   
   
   }
}
