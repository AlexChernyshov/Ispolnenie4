/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import ru.jcross.ispolnenie4.HibernateUtil;
import ru.jcross.ispolnenie4.inter.InterKbk;
import ru.jcross.ispolnenie4.models.Kbk;

/**
 *
 * @author Chernyshov
 */
public class ImplKbk implements InterKbk{

    
    private Session session = null;
    @Override
    public void insert(Kbk m) {
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
    public void edit(Kbk m) {
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
    public void delete(Kbk m) {
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
    public Kbk getByCode(String code) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null){  session = sessionFactory.openSession();}
        Kbk kbk = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Kbk.class).add(Restrictions.eq("code", code)).setMaxResults(1);
            kbk =  (Kbk) criteria.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
        }
        return kbk;  
    }

    @Override
    public ObservableList<Kbk> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();        
        if (session==null){  session = sessionFactory.openSession();}
        
        ObservableList<Kbk> kbks = null;
        try {
            session.beginTransaction();
            kbks =FXCollections.observableArrayList(
                    session.createCriteria(Kbk.class).list()); 
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
            //session.close();
           // sessionFactory.close();
        }

        return kbks;
    }
    
}
