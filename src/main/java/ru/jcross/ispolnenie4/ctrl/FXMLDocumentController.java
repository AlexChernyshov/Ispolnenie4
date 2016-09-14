/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.ctrl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import ru.jcross.ispolnenie4.Ispolnenie;
import ru.jcross.ispolnenie4.conn.DataSource;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 *
 * @author Chernyshov
 */
public class FXMLDocumentController implements Initializable {
    private final DataSource ds = new DataSource() ;  
    private static Connection cPSQL;
    private static Connection cFB ;  
    private static Statement stmP;
    private static ResultSet rs;
    
    @FXML
    private AnchorPane pane;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    @FXML
    private void menuImport(ActionEvent event) throws IOException {
        StackPane panechild = FXMLLoader.load(Ispolnenie.class.getResource("/fxml/DataURM.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(panechild);  
    
    }

    @FXML
    private void menuReportGenerate(ActionEvent event) throws IOException {
        AnchorPane panechild = FXMLLoader.load(Ispolnenie.class.getResource("/fxml/genreport.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(panechild);
    }


    @FXML
    private void menuReportOptions(ActionEvent event) throws IOException {
        AnchorPane panechild = FXMLLoader.load(Ispolnenie.class.getResource("/fxml/optreport.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(panechild);
    }




    @FXML
    private void menuUchrejdenie(ActionEvent event) throws IOException {
        AnchorPane panechild = FXMLLoader.load(Ispolnenie.class.getResource("/fxml/Uchrejdenie.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(panechild);
    }
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {           
            String queryP = "select F.id, F.name from dir.FACIALACC_CLS F where  F.id  BETWEEN 825110000 AND 825119999;";
            try{
                cPSQL = ds.connectPostgreSQL();
                cFB = ds.connectFireBird();
                stmP = cPSQL.createStatement();
                rs  = stmP.executeQuery(queryP);
                while (rs.next()) {
                        String count = rs.getString(2);
                        System.out.println(":> " + count);
                    }
            } catch (SQLException sqlEx) {
               sqlEx.printStackTrace();
            }
            finally {
                try { ds.putConnectionPSQL(cPSQL); } catch(SQLException se) { /*can't do anything */ }
                try { stmP.close(); } catch(SQLException se) { /*can't do anything */ }
                try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
                try {ds.putConnectionFB(cFB);  } catch (Exception ignore) {}
            } 
    }   
}
