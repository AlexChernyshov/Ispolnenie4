/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.inter;

import javafx.collections.ObservableList;
import ru.jcross.ispolnenie4.models.Kbk;

/**
 *
 * @author Chernyshov
 */
public interface InterKbk {
    void insert(Kbk m);
    void edit(Kbk m);
    void delete(Kbk m);
    Kbk getByCode(String code);
    ObservableList<Kbk> getAll();
}
