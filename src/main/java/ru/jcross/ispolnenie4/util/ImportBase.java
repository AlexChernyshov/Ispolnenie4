/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.jcross.ispolnenie4.Factory;
import ru.jcross.ispolnenie4.HibernateUtil;
import ru.jcross.ispolnenie4.conn.DataSource;
import ru.jcross.ispolnenie4.inter.*;
import ru.jcross.ispolnenie4.models.DataPlus;
import ru.jcross.ispolnenie4.models.ImportDate;
import ru.jcross.ispolnenie4.models.SectionField;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Chernyshov
 */
public class ImportBase extends Task {
    private float statusImport;
    
    private final DataSource ds = new DataSource() ;  
    private static Connection cPSQL;     
    private static Statement stmP;   
    private static ImportDate impd;
    private final Factory factory = Factory.getInstance();
    private final InterSectionField sectionDao = factory.getSectionDao();
    private final InterImportDate importDao = factory.getImportDao();
    private final InterDataPlus dataplusDao = factory.getDataplusDao();
    private final InterSubsidy subsidyDao = factory.getSubsidyDao();
    private final InterKbk kbkDao = factory.getKbkDao();
    private final InterKosgu kosguDao = factory.getKosguDao();
    private final InterFacialacc facialaccDao = factory.getFacialaccDao();
    private ObservableList<SectionField> SectionFieldData = FXCollections.observableArrayList();
    private final List<DataPlus> DataPlusDataFull = new ArrayList<>();


    public ImportBase(ImportDate impd) {
        this.impd=impd;
    }

    public void setSQLtoData(SectionField next, ImportDate impd){
            PreparedStatement pstmFB = null;
            ResultSet rs = null;
            Connection cFB =null;
            try{
                cFB = ds.connectFireBird();               
                pstmFB = cFB.prepareStatement(next.getTextSQL());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                pstmFB.setInt(1,Integer.valueOf(dateFormat.format(impd.getOndate())));
                rs  = pstmFB.executeQuery();   
                ResultSetMetaData metaData = rs.getMetaData();    
                int count = metaData.getColumnCount(); 
                DataPlus buff = null;
                int k = 0;
                String mes = "Импорт из УРМ АС Бюджет ";
                while (rs.next()){
                    if (k>1000)
                    {mes = "Импорт из УРМ АС Бюджет ";}
                    else{
                       if(k % 30 == 0 ||k==0) {
                           mes = mes +"|";
                       }
                    }
                    k++;
                    updateMessage(mes);
                    buff = new DataPlus();
                    buff.setImpdate(impd);
                    buff.setSecfield(next);
                    buff.setInSumma(BigDecimal.ZERO);
                    buff.setOutSumma(BigDecimal.ZERO);
                    buff.setFacialaccid(facialaccDao.getById(825000000));
                    buff.setKbkid(kbkDao.getByCode("0"));
                    buff.setKosguid(kosguDao.getByCode("0"));
                    buff.setSubsidyid(subsidyDao.getByCode("000000000"));
                    for (int i = 1; i <= count; i++)
                    {
                        if (null != metaData.getColumnLabel(i))
                            switch (metaData.getColumnLabel(i)) {
                            case "FACIALACC":
                                buff.setFacialaccid(facialaccDao.getById(rs.getInt(i)));
                                break;
                            case "SUBSIDYCODE":
                                if (rs.getString(i)==null) {
                                    buff.setSubsidyid(subsidyDao.getByCode("000000000"));
                                }else{
                                    buff.setSubsidyid(subsidyDao.getByCode(rs.getString(i)));
                                }                                
                                break;
                            case "KVR":
                                if (rs.getString(i)==null||rs.getString(i).equals("000")) {
                                   buff.setKbkid(kbkDao.getByCode("0"));
                                }else{
                                   buff.setKbkid(kbkDao.getByCode(rs.getString(i)));
                                }
                                break;
                            case "KESR":
                                if (rs.getString(i)==null) {
                                   buff.setKosguid(kosguDao.getByCode("0"));
                                }else{
                                   buff.setKosguid(kosguDao.getByCode(rs.getString(i)));
                                }
                                break;
                            case "INSUMMA":
                                buff.setInSumma(rs.getBigDecimal(i));
                                break;
                            case "OUTSUMMA":
                                buff.setOutSumma(rs.getBigDecimal(i));
                                break;
                        } 
                    }
                    DataPlusDataFull.add(buff);
                }
            } catch (SQLException sqlEx) {
               sqlEx.printStackTrace();
            }
            finally {
                try { if (rs!=null) rs.close(); } catch(SQLException se) { /*can't do anything */ } 
                try {ds.putConnectionFB(cFB);  } catch (Exception ignore) {}              
                try { pstmFB.close(); } catch(SQLException se) { /*can't do anything */ }
                            
            }
    }

    @Override
    protected Object call() throws Exception {
        updateMessage("Подготовка данных");
       // updateProgress(1,100000);
        Session session = null;
        if (session==null) session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        int max = 0;
        try {
            DataPlusDataFull.clear();             
            SectionFieldData = sectionDao.getAll();
            updateMessage("Импорт из УРМ АС Бюджет ");
            for(SectionField next : SectionFieldData) {
                setSQLtoData( next, impd);
            }
            updateMessage("Импорт из УРМ АС Бюджет(завершено)");
            max = DataPlusDataFull.size();
            updateProgress(1, max);
            int count = 0;
            updateMessage("Сохранение импорта в базу данных Исполнение 4.0 ...");
            tx = session.beginTransaction();
            session.save(impd);
            for(DataPlus itr:DataPlusDataFull){
                itr.setImpdate(impd);
                session.save(itr);
                if(++count%40==0){
                    session.flush();
                    session.clear();
                    Thread.sleep(10);
                }
                updateProgress(count+1, max);
            }
            impd.setDatapluss(DataPlusDataFull);
            tx.commit();
        } catch (Exception e) {
            if(tx!=null){ tx.rollback();}
            e.printStackTrace();
        }finally{
            session.close();           
        }
        updateMessage("Импорт: ГОТОВО!");
        updateProgress(max, max);

        return null;
    }
}
