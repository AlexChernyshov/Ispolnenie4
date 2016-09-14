package ru.jcross.ispolnenie4.ctrl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import ru.jcross.ispolnenie4.Factory;
import ru.jcross.ispolnenie4.MainApp;
import ru.jcross.ispolnenie4.inter.InterReportCaption;
import ru.jcross.ispolnenie4.inter.InterReportSQL;
import ru.jcross.ispolnenie4.models.ReportCaption;
import ru.jcross.ispolnenie4.models.ReportSQL;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * Created by Chernyshov on 26.04.2016.
 */
public class OptionReportController implements Initializable{
    //Поля таблицы ====================================
    @FXML
    private TableView<ReportCaption> TblCaption;
    @FXML
    private TableColumn<ReportCaption,String> columnNameCaption;
    @FXML
    private TableColumn<ReportCaption,String> columnPathTemplate;
    @FXML
    private TableColumn<ReportCaption,Integer> columnLock;

    //Текущая запись
    @FXML
    private TextArea txtNameCaption;

    @FXML
    private TextField txtPathTemplate;

    @FXML
    private ToggleButton btnLock;

    //Данные==============================================
    private ObservableList<ReportCaption> ReportCaptionData = FXCollections.observableArrayList();
    private ObservableList<ReportSQL> ReportSQLDATA = FXCollections.observableArrayList();
    private  ReportCaption tmpReportCaption = null;
    private  ReportSQL tmpReportSQL = null;
    private final Factory factory = Factory.getInstance();
    private final InterReportCaption reportCaptionDao = factory.getReportCaptionDao();
    private final InterReportSQL reportSQLDAO = factory.getReportSQLDao();
    //=====================================================

    //Детализация отчета========================================
    @FXML
    private TableView<ReportSQL> TblDetail;
    @FXML
    private TableColumn<ReportSQL,Integer> columnOrder;
    @FXML
    private TableColumn<ReportSQL,String> columnSubSQL;
    //==========================================================
    //Текущая запись======================
    @FXML
    private TextField txtorder;
    @FXML
    private TextArea txtsubSQL;
    //====================================


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnNameCaption.setCellValueFactory(new PropertyValueFactory<ReportCaption,String>("title"));
        columnNameCaption.prefWidthProperty().bind(TblCaption.widthProperty().multiply(0.60));
        columnPathTemplate.setCellValueFactory(new PropertyValueFactory<ReportCaption,String>("totemplate"));
        columnPathTemplate.prefWidthProperty().bind(TblCaption.widthProperty().multiply(0.30));
        columnLock.setCellValueFactory(new PropertyValueFactory<ReportCaption,Integer>("visible"));
        columnLock.prefWidthProperty().bind(TblCaption.widthProperty().multiply(0.10));
        TblCaption.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        refreshTableCaption();
        columnOrder.setCellValueFactory(new PropertyValueFactory<ReportSQL,Integer>("ordquery"));
        columnOrder.prefWidthProperty().bind(TblDetail.widthProperty().multiply(0.10));
        columnSubSQL.setCellValueFactory(new PropertyValueFactory<ReportSQL,String>("query"));
        columnSubSQL.prefWidthProperty().bind(TblDetail.widthProperty().multiply(0.90));
        TblDetail.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //refreshTableDetail();
    }

    private void refreshTableCaption() {
        TblCaption.getItems().clear();
        TblCaption.getItems().addAll(getDataCaption());
    }

    private ObservableList<ReportCaption> getDataCaption() {
        ReportCaptionData.removeAll(ReportCaptionData);
        ReportCaptionData.addAll(reportCaptionDao.getAll());
        return ReportCaptionData;
    }

    @FXML
    private void saveCaption(){
        tmpReportCaption.setTitle(txtNameCaption.getText());
        tmpReportCaption.setTotemplate(txtPathTemplate.getText());
        tmpReportCaption.setVisible(btnLock.isSelected()? 1 : 0);
        if(tmpReportCaption.getId()==null){
            reportCaptionDao.insert(tmpReportCaption);
        }else{
            reportCaptionDao.edit(tmpReportCaption);
        }
        refreshTableCaption();
    }

    @FXML
    private void saveDetail(){
        if(tmpReportCaption!=null){
            if(tmpReportSQL==null){
                tmpReportSQL = new ReportSQL();
                tmpReportSQL.setOrdquery(Integer.valueOf(txtorder.getText()));
                tmpReportSQL.setQuery(txtsubSQL.getText());
                reportSQLDAO.insert(tmpReportCaption, tmpReportSQL);
            }else{
                tmpReportSQL.setOrdquery(Integer.valueOf(txtorder.getText()));
                tmpReportSQL.setQuery(txtsubSQL.getText());
                reportSQLDAO.edit(tmpReportCaption, tmpReportSQL);
            }
        }

        refreshTableDetail();
    }

    @FXML
    private void cancelCaption(){
        tmpReportCaption= null;
        txtNameCaption.clear();
        txtPathTemplate.clear();
        btnLock.setSelected(false);
        refreshTableCaption();
    }
    @FXML
    private void contextMenuDeleteCaption(){
        if(!TblCaption.getSelectionModel().isEmpty())
            reportCaptionDao.del(TblCaption.getSelectionModel().getSelectedItem());
        refreshTableCaption();
    }

    @FXML
    private void cancelDetail(){
        tmpReportSQL= null;
        txtorder.clear();
        txtsubSQL.clear();
        btnLock.setSelected(false);
        refreshTableDetail();
    }

    private void refreshTableDetail() {
        TblDetail.setItems(null);
        TblDetail.setItems(getDataDetail());
    }

    private ObservableList<ReportSQL> getDataDetail() {
        ReportSQLDATA = null;
        ReportSQLDATA = reportSQLDAO.getAll(tmpReportCaption);
        return ReportSQLDATA;
    }

    @FXML
    private void clickTblCaption(){
        if(!TblCaption.getSelectionModel().isEmpty()){
            tmpReportCaption = TblCaption.getSelectionModel().getSelectedItem();
            txtNameCaption.setText(tmpReportCaption.getTitle());
            txtPathTemplate.setText(tmpReportCaption.getTotemplate());
            btnLock.setSelected(tmpReportCaption.getVisible()!=0);
            refreshTableDetail();
        }
    }

    @FXML
    private void clickTblDetail(){
        if(!TblDetail.getSelectionModel().isEmpty()){
            tmpReportSQL = TblDetail.getSelectionModel().getSelectedItem();
            txtorder.setText(String.valueOf(tmpReportSQL.getOrdquery()));
            txtsubSQL.setText(tmpReportSQL.getQuery());
        }
    }

    @FXML
    private void brouseFileTemplate() throws IOException {
        FileChooser fileChooser = new FileChooser();
        if(txtPathTemplate.getLength()>0){
            File currentPath =  new File(txtPathTemplate.getText());
            String absolutePath = currentPath.getAbsolutePath();
            if(currentPath.exists()){
                fileChooser.setInitialDirectory(new File(absolutePath.substring(0,absolutePath.lastIndexOf(File.separator))));
            }
        }else {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }
        fileChooser.setTitle("Выбор шаблона Excel");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Шаблон Excel", "*.xlsx"));
        File selectedFile = fileChooser.showOpenDialog(TblCaption.getScene().getWindow());
        if (selectedFile != null) {
           // Path file =  Paths.get(String.valueOf(selectedFile.getAbsoluteFile()));
          //  URL pathURL = MainApp.class.getProtectionDomain().getCodeSource().getLocation();
//
            //Path folder = Paths.get(pathURL.toString());
            //Path relativePath = folder.relativize(file);
            txtPathTemplate.setText(selectedFile.getAbsolutePath());
        }
    }
}
