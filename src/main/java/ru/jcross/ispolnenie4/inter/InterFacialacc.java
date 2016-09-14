/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.inter;

import javafx.collections.ObservableList;
import ru.jcross.ispolnenie4.models.Facialacc;


/**
 *
 * @author Chernyshov
 */
public interface InterFacialacc {
    void insert(Facialacc m);
    void edit(Facialacc m);
    void delete(Facialacc m);
    Facialacc getById(Integer code);
    ObservableList<Facialacc> getAll();
}                 