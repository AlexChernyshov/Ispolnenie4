package ru.jcross.ispolnenie4.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.jcross.ispolnenie4.HibernateUtil;
import ru.jcross.ispolnenie4.inter.InterReportCaption;
import ru.jcross.ispolnenie4.models.ReportCaption;

/**
 * Created by Chernyshov on 22.04.2016.
 */
public class ImplReportCaption implements InterReportCaption {

    private Session session = null;

    @Override
    public void insert(ReportCaption reportCaption) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null){  session = sessionFactory.openSession();}
        else {session = sessionFactory.getCurrentSession();}
        try {
            session.beginTransaction();
            session.save(reportCaption);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{

        }
    }

    @Override
    public void edit(ReportCaption reportCaption) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null){  session = sessionFactory.openSession();}
        else {session = sessionFactory.getCurrentSession();}
        try {
            session.beginTransaction();
            session.merge(reportCaption);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{

        }
    }

    @Override
    public void del(ReportCaption reportCaption) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (session==null){  session = sessionFactory.openSession();}
        else {session = sessionFactory.getCurrentSession();}
        try {
            session.beginTransaction();
            session.delete(reportCaption);
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
    public ObservableList<ReportCaption> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        if (session==null){  session = sessionFactory.openSession();}
        else {session = sessionFactory.getCurrentSession();}

        ObservableList<ReportCaption> reportCaptions = null;
        try {
            session.beginTransaction();
            reportCaptions = FXCollections.observableArrayList(
                    session.createCriteria(ReportCaption.class).list());
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally{

        }
        return reportCaptions;
    }
}
