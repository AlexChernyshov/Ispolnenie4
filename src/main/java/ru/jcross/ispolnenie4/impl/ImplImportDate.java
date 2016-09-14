/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import ru.jcross.ispolnenie4.HibernateUtil;
import ru.jcross.ispolnenie4.inter.InterImportDate;
import ru.jcross.ispolnenie4.models.ImportDate;

/**
 *
 * @author Chernyshov
 */
public class ImplImportDate  implements InterImportDate{
   private Session session = null;
    @Override
    public void insert(ImportDate m) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null) session = sessionFactory.openSession();
        try {
            session.beginTransaction();            
            session.save(m);
            session.getTransaction().commit();
            session.flush();
            session.clear();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
           // session.close();
           //sessionFactory.close();
        }               
    }    

    @Override
    public void delete(ImportDate m) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null)   session = sessionFactory.openSession();
        try {
            session.beginTransaction();            
            session.delete(m);            
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
            //session.close();
            //sessionFactory.close();
        }
    }

    @Override
    public ObservableList<ImportDate> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();        
        if (session==null){  session = sessionFactory.openSession();}
        
        ObservableList<ImportDate> importdates = null;
        try {
            session.beginTransaction();
            importdates =FXCollections.observableArrayList(
                    session.createCriteria(ImportDate.class).addOrder(Order.asc("ondate")).list());
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
            //session.close();
           // sessionFactory.close();
        }
//        for(Facialacc obj: facialaccs){
//            System.out.println("facialacc : "+obj.toString());
//        }
        return importdates;
    }
    
}
