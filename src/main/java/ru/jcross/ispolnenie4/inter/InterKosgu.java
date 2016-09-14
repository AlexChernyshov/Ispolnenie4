/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.inter;

import javafx.collections.ObservableList;
import ru.jcross.ispolnenie4.models.Kosgu;


/**
 *
 * @author Chernyshov
 */
public interface InterKosgu {
    void insert(Kosgu m);
    void edit(Kosgu m);
    void delete(Kosgu m);
    Kosgu getByCode(String code);
    ObservableList<Kosgu> getAll();    
}
