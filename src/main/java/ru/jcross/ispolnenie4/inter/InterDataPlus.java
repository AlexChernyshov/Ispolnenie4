/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.inter;

import javafx.collections.ObservableList;
import ru.jcross.ispolnenie4.models.DataPlus;

/**
 *
 * @author Chernyshov
 */
public interface InterDataPlus {
    public void insert(ObservableList<DataPlus> m);
    public void edit(DataPlus m);
    public void delete(DataPlus m );
    ObservableList<DataPlus> getAll();
}
