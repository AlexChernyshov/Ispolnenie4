package ru.jcross.ispolnenie4.ctrl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.jcross.ispolnenie4.Factory;
import ru.jcross.ispolnenie4.MainApp;
import ru.jcross.ispolnenie4.inter.InterImportDate;
import ru.jcross.ispolnenie4.inter.InterReportCaption;
import ru.jcross.ispolnenie4.inter.InterReportSQL;
import ru.jcross.ispolnenie4.models.ImportDate;
import ru.jcross.ispolnenie4.models.ReportCaption;
import ru.jcross.ispolnenie4.models.ReportSQL;
import ru.jcross.ispolnenie4.util.Buildreport;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
 * Created by Chernyshov on 12.04.2016.
 */
public class GenReportController implements Initializable {
    @FXML
    private ChoiceBox<ReportCaption> ViborOtcheta;
    @FXML
    private Button generateReport;
    @FXML
    private TableView<ImportDate> TImport;
    @FXML
    private TableColumn<ImportDate,Integer> C_id;
    @FXML
    private TableColumn<ImportDate,java.util.Date> C_importDate;

    @FXML
    private TableColumn<ImportDate,java.sql.Date> C_reportDate;

    @FXML
    private TableColumn<ImportDate,String> C_note512;

    @FXML
    private ToggleButton  tbKU;
    @FXML
    private ToggleButton  tbBU;
    @FXML
    private ToggleButton  tbAU;

    private  Session session =null;
    static final Logger rootLogger = LogManager.getRootLogger();

    private final Factory factory = Factory.getInstance();
    private final InterImportDate importdateDao = factory.getImportDao();
    private final InterReportCaption reportCaptionDao = factory.getReportCaptionDao();
    private final InterReportSQL reportSQL = factory.getReportSQLDao();
    private ObservableList<ReportCaption> choices = FXCollections.observableArrayList();

    private ImportDate klikdateImport = null;
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
        choices.clear();
        choices.addAll(reportCaptionDao.getAll());
        ViborOtcheta.getItems().addAll(choices);

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


    @FXML
    private void setKlikImport(){
        klikdateImport = TImport.getSelectionModel().getSelectedItem();
    }


    @FXML
    private void  genReport() throws IOException, ClassNotFoundException {
        URL pathURL = MainApp.class.getProtectionDomain().getCodeSource().getLocation();
        log("PATH>"+pathURL.toString());
        HashMap<String,String> property = new HashMap<>();
        //======================================================================
        //создать запрос по фильтрам
        //======================================================================
        List<String> listUch = new ArrayList<>();
        String SubSQL_typeUchregdenie ="";
        if(tbKU.isSelected()||tbBU.isSelected()||tbAU.isSelected()){
            if(tbKU.isSelected()) {
                listUch.add("(p.ls BETWEEN 825100000 AND 825199999)");
            }
            if(tbBU.isSelected()) {
                listUch.add("(p.ls BETWEEN 825500000 AND 825699999)");
            }
            if(tbAU.isSelected()) {
                listUch.add("(p.ls BETWEEN 825700000 AND 825899999)");
            }
           SubSQL_typeUchregdenie =" and(" +String.join(" or ",listUch)+") ";
        }
        System.out.println(SubSQL_typeUchregdenie);

        Buildreport br = new Buildreport();
        br.setConfigDB("jdbc:postgresql://192.168.0.7:5432/BigBase","arest","QWE005rd*9788960");
        List<String> preQuery = new ArrayList<>();
        preQuery = initQuery();

        property.put("typeuch",SubSQL_typeUchregdenie); // Фильтр тип учреждения
        property.put("ondateid",klikdateImport.getId().toString());      //Фильтр даты
        property.put("reportname",ViborOtcheta.getSelectionModel().getSelectedItem().getTitle());//фильтр Имя отчета

        for (String s : preQuery)
        {
            br.preLoadObject(s.toString());
            log(s);
        }

        br.setPropertyQuerys(property);
        br.getloadObject();
        Desktop.getDesktop().open(br.createReport(new File(ViborOtcheta.getSelectionModel().getSelectedItem().getTotemplate())));
    }

    //Инициализация запросов для получения данных
    private List<String> initQuery(){
        List<String> subquery = new ArrayList<>();
        List<ReportSQL> initsql = new ArrayList<>();
        initsql.addAll(reportSQL.getAll(ViborOtcheta.getSelectionModel().getSelectedItem()));
        Iterator itr = initsql.iterator();
        while(itr.hasNext()){
            ReportSQL itemRsql  = (ReportSQL) itr.next();
            subquery.add(itemRsql.getQuery());
        }
        return subquery;
    }

    private  void getFieldModel(Object obj){
        Class c = obj.getClass();
        Field[] publicFields = c.getDeclaredFields();
        for (Field field : publicFields) {
            Class fieldType = field.getType();
            log("Имя: " + field.getName());
            log("Тип: " + fieldType.getName());
        }
    }

    final public static void printResultSet(ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(" | ");
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }
    private void log(String s){
        System.out.println("# > "+s);
    }


}
