/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.inter;

import javafx.collections.ObservableList;
import ru.jcross.ispolnenie4.models.Subsidycls;

/**
 *
 * @author Chernyshov
 */
public interface InterSubsidy {
    void insert(Subsidycls m);
    void edit(Subsidycls m);
    void delete(Subsidycls m);
    Subsidycls getByCode(String code);
    ObservableList<Subsidycls> getAll();
}
