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
import ru.jcross.ispolnenie4.inter.InterFacialacc;
import ru.jcross.ispolnenie4.models.Facialacc;

/**
 *
 * @author Chernyshov
 */
public class ImplFacialacc implements InterFacialacc{
    private Session session = null;
    @Override
    public void insert(Facialacc m) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null) session = sessionFactory.openSession();
        try {
            session.beginTransaction();            
            session.save(m);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
           // session.close();
           //sessionFactory.close();
        }               
    }

    @Override
    public void edit(Facialacc m) {
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
    public void delete(Facialacc m) {
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
    public ObservableList<Facialacc> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();        
        if (session==null){  session = sessionFactory.openSession();}
        
        ObservableList<Facialacc> facialaccs = null;
        try {
            session.beginTransaction();
            facialaccs =FXCollections.observableArrayList(
                    session.createCriteria(Facialacc.class).addOrder(Order.asc("id")).list());
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
        return facialaccs;
    }

    @Override
    public Facialacc getById(Integer code) {               
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null){  session = sessionFactory.openSession();}         
        Facialacc facialacc = (Facialacc) session.load(Facialacc.class, code);         
        return facialacc;    
    }
    
}
