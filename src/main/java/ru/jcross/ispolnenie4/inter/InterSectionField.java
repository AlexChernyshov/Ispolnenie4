/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.inter;

import javafx.collections.ObservableList;
import ru.jcross.ispolnenie4.models.SectionField;

/**
 *
 * @author Chernyshov
 */
public interface InterSectionField {
    void insert(SectionField m);
    void edit(SectionField m);
    void delete(SectionField m);    
    ObservableList<SectionField> getAll();
}
