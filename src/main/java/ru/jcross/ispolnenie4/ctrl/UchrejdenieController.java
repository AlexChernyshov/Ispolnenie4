/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.ctrl;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.jcross.ispolnenie4.Factory;
import ru.jcross.ispolnenie4.inter.InterFacialacc;
import ru.jcross.ispolnenie4.models.Facialacc;
import ru.jcross.ispolnenie4.util.ImportUch;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Chernyshov
 */
public class UchrejdenieController implements Initializable {

 
    
    @FXML
    private TableView<Facialacc> TblUchrejdenie;
    @FXML
    private TableColumn<Facialacc, Integer> LS;
    @FXML
    private TableColumn<Facialacc, String> nameP;
    @FXML
    private TableColumn<Facialacc, String> nameK;
    @FXML
    private TableColumn<Facialacc, Double> dateOpen;
    @FXML
    private TableColumn<Facialacc, Double> dateClose;
    
    @FXML
    private TextField txtLS;
    @FXML
    private TextArea txtNameP;
    @FXML
    private TextArea txtNameK;
    @FXML
    private TextField txtOpen;
    @FXML
    private TextField txtClose;

    
    private final Factory factory = Factory.getInstance();
    private final InterFacialacc facialaccDao = factory.getFacialaccDao();
    
    private ObservableList<Facialacc> FacialaccData = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        LS.setCellValueFactory(new PropertyValueFactory<Facialacc,Integer>("id"));
        nameP.setCellValueFactory(new PropertyValueFactory<Facialacc,String>("namep"));
        nameK.setCellValueFactory(new PropertyValueFactory<Facialacc,String>("namek"));
        dateOpen.setCellValueFactory(new PropertyValueFactory<Facialacc,Double>("dateopen"));
        dateClose.setCellValueFactory(new PropertyValueFactory<Facialacc,Double>("dateclose"));
        refreshTable();
    }
    
    /*
        КНОПКА СОХРАНИТЬ
    */
    @FXML
    private void  btnSave() throws Exception{  
        int id = TblUchrejdenie.getSelectionModel().getSelectedItem().getId();
        int selID = TblUchrejdenie.getSelectionModel().getSelectedIndex();
        if (id>0){
            Facialacc  f = new Facialacc();
            f.setId(id);
            f.setNamep(txtNameP.getText());
            f.setNamek(txtNameK.getText());
            if(txtOpen.getText()!=null&&"".equals(txtOpen.getText())) f.setDateopen(Double.valueOf(txtOpen.getText()));        
            if(txtClose.getText()!=null&&"".equals(txtClose.getText())) f.setDateclose(Double.valueOf(txtClose.getText()));        
            facialaccDao.edit(f);
            refreshTable();
            TblUchrejdenie.refresh();
            TblUchrejdenie.requestFocus();
            TblUchrejdenie.getSelectionModel().select(selID);
            TblUchrejdenie.getFocusModel().focus(f.getId());
           /* Platform.runLater(new Runnable() {
                public void run() {

                }
            });
*/

           // TblUchrejdenie.getSelectionModel().select(FacialaccData.get(id));
        }

    } 
    
    @FXML
    private void importUpdate(){
        ImportUch IUch = new ImportUch();
        IUch.getCollectionURM();
    
    
    
    }
    
    private void refreshTable(){
        TblUchrejdenie.setItems(null);
        TblUchrejdenie.setItems(getData());
    }
    /*
        Контекстное меню Удалить
    */
    @FXML
    private void contextMenuDelete(){
       if(!TblUchrejdenie.getSelectionModel().isEmpty())
        facialaccDao.delete(TblUchrejdenie.getSelectionModel().getSelectedItem());
       refreshTable();
    }
    /*
        КНОПКА СОХРАНИТЬ
    */
    @FXML
    private void  btnCancel(){
        txtLS.clear();
        txtNameP.clear();
        txtNameK.clear();
        txtOpen.clear();
        txtClose.clear();
        TblUchrejdenie.getSelectionModel().select(null);
        refreshTable();
        TblUchrejdenie.refresh();
    }
    
    @FXML
    private void clickTable(){
        if(!TblUchrejdenie.getSelectionModel().isEmpty()){
        txtLS.setText(String.valueOf(TblUchrejdenie.getSelectionModel().getSelectedItem().getId()));
        txtNameP.setText(TblUchrejdenie.getSelectionModel().getSelectedItem().getNamep());
        txtNameK.setText(TblUchrejdenie.getSelectionModel().getSelectedItem().getNamek());
        txtOpen.setText(String.valueOf(TblUchrejdenie.getSelectionModel().getSelectedItem().getDateopen()));
        txtClose.setText(String.valueOf(TblUchrejdenie.getSelectionModel().getSelectedItem().getDateclose()));
        }
    }
    
    
    private ObservableList<Facialacc>  getData(){
        FacialaccData.removeAll(FacialaccData);
        FacialaccData = facialaccDao.getAll();
        return FacialaccData;
    }
    
    
    
}
