/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Chernyshov
 */
public class Ispolnenie extends Application { 
    private SessionFactory sessionFactory;
    private Session session;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root;        
        root = FXMLLoader.load(getClass().getResource("/fxml/FXMLDocument.fxml"));
        Scene scene = new Scene(root); 
        stage.setTitle("Исполнение бюджета учреждений образования, науки и молодежной политики Краснодарского края");
        stage.getIcons().add(
            new Image(Ispolnenie.class.getResourceAsStream("/img/database.png")));
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void init(){
        sessionFactory=   HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
    }
    
    @Override
    public void stop(){       
        session.close();
        sessionFactory.close();
    }
}