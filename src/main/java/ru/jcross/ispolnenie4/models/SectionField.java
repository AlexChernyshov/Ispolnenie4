/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Chernyshov
 */

@Entity
@Table(name = "sectionfield")
public class SectionField implements Serializable{  
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    @Basic
    @Column(name = "name_s", length = 1024)
    private String name_s;
    
    @Basic
    @Column(name = "textSQL",length = 4096)
    private String textSQL;
    
    @Basic
    @Column(name = "typesection")
    private String typeSection;   
   
 
    public SectionField() {
    }

    public SectionField(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName_s() {
        return name_s;
    }

    public String getTextSQL() {
        return textSQL;
    }

    public String getTypeSection() {
        return typeSection;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName_s(String name_s) {
        this.name_s = name_s;
    }

    public void setTextSQL(String textSQL) {
        this.textSQL = textSQL;
    }

    public void setTypeSection(String typeSection) {
        this.typeSection = typeSection;
    }  
    
    
}
