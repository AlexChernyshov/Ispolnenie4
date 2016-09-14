package ru.jcross.ispolnenie4.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.jcross.ispolnenie4.HibernateUtil;
import ru.jcross.ispolnenie4.inter.InterReportSQL;
import ru.jcross.ispolnenie4.models.ReportCaption;
import ru.jcross.ispolnenie4.models.ReportSQL;

public class ImplReportSQL implements InterReportSQL{
    private Session session = null;
    private Transaction tx = null;
    @Override
    public void insert(ReportCaption reportCaption, ReportSQL reportSQL) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null){  session = sessionFactory.openSession();}
        else {session = sessionFactory.getCurrentSession();}
        try {
            tx = session.beginTransaction();
            reportSQL.setReportCaptionID(reportCaption);
            session.save(reportSQL);
            session.flush();
            session.clear();
            tx.commit();
        } catch (HibernateException e) {
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{

        }
    }

    @Override
    public void edit(ReportCaption reportCaption,ReportSQL reportSQL) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null){  session = sessionFactory.openSession();}
        else {session = sessionFactory.getCurrentSession();}
        try {
            session.beginTransaction();
            session.saveOrUpdate(reportSQL);
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
    public void del(ReportSQL reportSQL) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null){  session = sessionFactory.openSession();}
        else {session = sessionFactory.getCurrentSession();}
        try {
            session.beginTransaction();
            session.delete(reportSQL);
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
    public ObservableList<ReportSQL> getAll(ReportCaption reportCaption) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        if (session==null){  session = sessionFactory.getCurrentSession();}
        else {session = sessionFactory.getCurrentSession();}
        ObservableList<ReportSQL> reportSQLs = null;
        try {
            tx = session.beginTransaction();
            Criteria rSQLcriteria = session.createCriteria(ReportSQL.class);
            rSQLcriteria.add(Restrictions.eq("ReportCaptionID",reportCaption));
            rSQLcriteria.addOrder(Order.asc("ordquery"));
            reportSQLs = FXCollections.observableArrayList(rSQLcriteria.list());
            session.getTransaction().commit();
        } catch (HibernateException e) {
           // if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{

        }
        return reportSQLs;
    }
}