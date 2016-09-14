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
import ru.jcross.ispolnenie4.HibernateUtil;
import ru.jcross.ispolnenie4.inter.InterSectionField;
import ru.jcross.ispolnenie4.models.SectionField;

/**
 *
 * @author Chernyshov
 */
public class ImplSectionField implements InterSectionField {
        private Session session = null;
    @Override
    public void insert(SectionField m) {
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
    public void edit(SectionField m) {
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
    public void delete(SectionField m) {
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
    public ObservableList<SectionField> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();        
        if (session==null){  session = sessionFactory.openSession();}
        
        ObservableList<SectionField> facialaccs = null;
        try {
            session.beginTransaction();
            facialaccs =FXCollections.observableArrayList(
                    session.createCriteria(SectionField.class).list()); 
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
            //session.close();
           // sessionFactory.close();
        }
//        for(SectionField obj: facialaccs){
//            System.out.println("facialacc : "+obj.toString());
//        }
        return facialaccs;
    }
}
