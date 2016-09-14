/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.ctrl;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.jcross.ispolnenie4.models.ImportDate;
import ru.jcross.ispolnenie4.models.ImportID;
import ru.jcross.ispolnenie4.util.ImportBase;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FXML Controller class
 *
 * @author Chernyshov
 */
public class ImportURMController implements Initializable {
    @FXML 
    private ProgressBar PBar;
    @FXML
    private Label CreateImport;
    @FXML
    private DatePicker DateImport;
    @FXML
    private TableView<ImportID> ProcessImport;
    @FXML
    private TableColumn<ImportID,String> colSection;
    @FXML    
    private TableColumn<ImportID,String> colStatus;
    @FXML
    private Label labelstatus;

    @FXML
    private Button OkClose;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtNote;

    private boolean flagAction;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        flagAction=true;
        OkClose.setText("ОК");
        this.PBar.setProgress(0.00); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.YYYY");
        CreateImport.setText((String) dateFormat.format(new Date()));
        DateImport.setValue(LocalDate.now());      
    }    

    @FXML
    private void ImportBegin(){
        Runnable OffButton=()->  {
            btnCancel.setDisable(true);
            OkClose.setDisable(true);
        };
        Runnable OnButton=()->  {
            btnCancel.setDisable(false);
            OkClose.setDisable(false);
        };
        if (flagAction) {

            ImportDate impd = new ImportDate();
            impd.setCreatedate(Timestamp.valueOf(LocalDateTime.now()));
            impd.setOndate(java.sql.Date.valueOf(DateImport.getValue()));
            impd.setNote(txtNote.getText());
            ImportBase ib = new ImportBase(impd);
            labelstatus.textProperty().bind(ib.messageProperty());
            PBar.setProgress(0);
            PBar.progressProperty().unbind();
            PBar.progressProperty().bind(ib.progressProperty());

            ExecutorService service = Executors.newFixedThreadPool(1);
            service.execute(OffButton);
            service.execute(ib);
            service.execute(OnButton);

            OkClose.setText("Закрыть");
            flagAction=false;
            service.shutdown();
        }else{
            Stage stage;
            stage = (Stage) this.labelstatus.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void Cancel(){
        Stage stage;
        stage = (Stage) this.labelstatus.getScene().getWindow();
        stage.close();
    }
    private boolean dateValidate(){
        return true;
    }
    
}