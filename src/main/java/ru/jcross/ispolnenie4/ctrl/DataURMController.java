/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.ctrl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.jcross.ispolnenie4.Factory;
import ru.jcross.ispolnenie4.Ispolnenie;
import ru.jcross.ispolnenie4.inter.InterImportDate;
import ru.jcross.ispolnenie4.models.ImportDate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Chernyshov
 */
public class DataURMController implements Initializable {

    @FXML 
    private TableView<ImportDate> TImport; //таблица импорта список того что уже есть 
    @FXML
    private TableColumn<ImportDate,Integer> C_id;
    @FXML
    private TableColumn<ImportDate,java.util.Date> C_importDate;
    
    @FXML
    private TableColumn<ImportDate,java.sql.Date> C_reportDate;
    
    @FXML
    private TableColumn<ImportDate,String> C_note512;
    
    
    private final Factory factory = Factory.getInstance();
    private final InterImportDate importdateDao = factory.getImportDao();
    
    private ObservableList<ImportDate> ImportDateData = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        C_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        C_importDate.setCellValueFactory(new PropertyValueFactory<>("createdate"));
        C_reportDate.setCellValueFactory(new PropertyValueFactory<>("ondate"));
        C_note512.setCellValueFactory(new PropertyValueFactory<>("note"));
        refreshTable();
    }
    
    @FXML
    private void ActDialogImport(ActionEvent event) throws IOException{
        StackPane root = FXMLLoader.load(Ispolnenie.class.getResource("/fxml/ImportURM.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNIFIED);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.setResizable(false);
        stage.setTitle("Импорт из базы данных УРМ АС Бюджет");
        stage.showAndWait();
        refreshTable();
    }
     /*
        Контекстное меню Удалить
    */
    @FXML
    private void contextMenuDelete(){
       if(!TImport.getSelectionModel().isEmpty())
        importdateDao.delete(TImport.getSelectionModel().getSelectedItem());
       refreshTable();
    }
    
    private void refreshTable(){
        TImport.setItems(null);
        TImport.setItems(getData());
    }
    
    private ObservableList<ImportDate>  getData(){
        ImportDateData = null;
        ImportDateData = importdateDao.getAll();
        return ImportDateData;
    }
    
}
