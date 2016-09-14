/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4;

import ru.jcross.ispolnenie4.impl.*;
import ru.jcross.ispolnenie4.inter.*;


/**
 *
 * @author Chernyshov
 */
public class Factory {
    public static Factory instance = new Factory();
    public InterFacialacc facialaccDao;
    public InterSectionField sectionDao;
    public InterImportDate  importDao;
    public InterDataPlus dataplusDao;
    public InterSubsidy subsidyDao;
    public InterKbk kbkDao;
    public InterKosgu kosguDao;
    public InterReportCaption reportCaptionDao;
    public InterReportSQL reportSQLDao;

    private Factory(){ 
    
    }
    
    public static  Factory getInstance(){   
        return  Factory.instance;    
    }

    public InterReportSQL getReportSQLDao(){
        if(reportSQLDao == null) reportSQLDao = new ImplReportSQL();
        return  reportSQLDao;
    }

    public InterReportCaption getReportCaptionDao(){
        if(reportCaptionDao == null) reportCaptionDao = new ImplReportCaption();
        return  reportCaptionDao;
    }
    
    public InterSubsidy getSubsidyDao(){
        if(subsidyDao == null) subsidyDao = new ImplSubsidy();
        return subsidyDao;
    }
    
    public InterKosgu getKosguDao(){
        if(kosguDao == null) kosguDao = new ImplKosgu();
        return kosguDao;
    }
    
    public InterKbk getKbkDao(){
        if(kbkDao == null) kbkDao = new ImplKbk();
        return kbkDao;
    }    
    
    public InterDataPlus getDataplusDao(){
        if(dataplusDao == null) dataplusDao = new ImplDataPlus();
        return dataplusDao;
    }    
    
    public InterImportDate getImportDao(){
        if(importDao == null) importDao = new ImplImportDate();
        return importDao;
    }
    
    public InterSectionField getSectionDao(){
        if(sectionDao == null) sectionDao = new ImplSectionField();
        return sectionDao;
    }    
    
    public InterFacialacc getFacialaccDao(){
        if(facialaccDao == null) facialaccDao = new ImplFacialacc();
        return facialaccDao;
    }

}
