package ru.jcross.ispolnenie4.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataSource {
   
    static final String DB_URL_PSQL = "jdbc:postgresql://192.168.0.7:5432/BigBase";
    static final String DB_URL_FB = "jdbc:firebirdsql:192.168.0.13/3050:C:\\Program Files\\KRISTA\\urm\\DataBase\\Budgetrm2016.gdb";
   
    static final String USER_PSQL = "arest";
    static final String PASS_PSQL = "QWE005rd*9788960";
    static final String USER_FB = "SYSDBA";
    static final String PASS_FB = "masterkey";
    
    
    public Connection connectPostgreSQL() throws SQLException{        
      return DriverManager.getConnection(DB_URL_PSQL,USER_PSQL,PASS_PSQL);        
    }    
    
    public Connection connectFireBird() throws SQLException{        
         return  DriverManager.getConnection(DB_URL_FB,USER_FB,PASS_FB);
    }
    
    public void putConnectionFB(Connection connection) throws SQLException {
            connection.close();
    }
    
    public void putConnectionPSQL(Connection connection) throws SQLException {
            connection.close();
    }
}

