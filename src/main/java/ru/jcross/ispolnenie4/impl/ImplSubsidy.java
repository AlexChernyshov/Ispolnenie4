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
import ru.jcross.ispolnenie4.inter.InterSubsidy;
import ru.jcross.ispolnenie4.models.Subsidycls;

/**
 *
 * @author Chernyshov
 */
public class ImplSubsidy implements InterSubsidy{

    private Session session = null;
    @Override
    public void insert(Subsidycls m) {
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
    public void edit(Subsidycls m) {
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
    public void delete(Subsidycls m) {
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
    public Subsidycls getByCode(String code) {        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null){  session = sessionFactory.openSession();}
        Subsidycls subsidycls = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Subsidycls.class).add(Restrictions.eq("code", code)).setMaxResults(1);
            subsidycls =  (Subsidycls) criteria.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
        }
        return subsidycls;
    }

    @Override
    public ObservableList<Subsidycls> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();        
        if (session==null){  session = sessionFactory.openSession();}
        
        ObservableList<Subsidycls> subsidys = null;
        try {
            session.beginTransaction();
            subsidys =FXCollections.observableArrayList(
                    session.createCriteria(Subsidycls.class).list()); 
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{
            //session.close();
           // sessionFactory.close();
        }

        return subsidys;
    }
    
}
