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
import org.hibernate.Transaction;
import ru.jcross.ispolnenie4.HibernateUtil;
import ru.jcross.ispolnenie4.inter.InterDataPlus;
import ru.jcross.ispolnenie4.models.DataPlus;

import java.util.Iterator;


/**
 *
 * @author Chernyshov
 */
public class ImplDataPlus implements InterDataPlus{

    private Session session = null;
    @Override
    public void insert(ObservableList<DataPlus> m) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null) session = sessionFactory.openSession();
        try {
            int count = 0;
            Transaction tx;
            Iterator itr  = m.iterator();  
            while ( itr.hasNext() ){
                session.flush();
                session.clear();
                tx =session.beginTransaction();
                session.save(itr.next());
//                 if(++count%30==0){
//                    session.flush();
//                    session.clear();                    
//                }
                 
                tx.commit();
                session.flush();
                session.clear();
            }    
            
            
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{

           // session.close();
           //sessionFactory.close();
        }
    }

    @Override
    public void edit(DataPlus m) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null)  session = sessionFactory.openSession();
        try {
            session.beginTransaction();            
            session.merge(m);            
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
    public void delete(DataPlus m) {
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
           // sessionFactory.close();
        }
    }

    @Override
    public ObservableList<DataPlus> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();        
        if (session==null){  session = sessionFactory.openSession();}
        
        ObservableList<DataPlus> datapluss = null;
        try {
            session.beginTransaction();
            datapluss =FXCollections.observableArrayList(
                    session.createCriteria(DataPlus.class).list()); 
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
        return datapluss;
    }
    
}
