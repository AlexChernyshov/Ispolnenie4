/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.impl;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chernyshov
 */
public class LoadingBar implements Runnable{
    private ProgressBar PBar;
    final int MAX = 100;
   
    public LoadingBar(ProgressBar Bar) {
       PBar =  Bar;
    }    
    
    @Override
    public void run() {
        for (int i = 1; i <= MAX; i++) {                 
                final double update_i = i;                
                Platform.runLater(new Runnable(){ 
                    @Override
                    public void run() {
                        PBar.setProgress(update_i/MAX);
                    }
                });                 
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LoadingBar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }       
    }
}
