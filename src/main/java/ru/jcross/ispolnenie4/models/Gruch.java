/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chernyshov
 */
@Entity
@Table(name = "gruch")
@SequenceGenerator(name = "gruch_seq", sequenceName = "gruch_seq" )
public class Gruch implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gruch_seq")
    @Column(name = "id")
    private int id;
    
    @Basic
    @Column(name = "title", length = 80)
    private String title;
    
    @ManyToMany(mappedBy = "gruchs")    
    private Set<Facialacc> facialaccs = new HashSet<Facialacc>();

    public Gruch() {
    }

    public Gruch(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Set<Facialacc> getFacialaccs() {
        return facialaccs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFacialaccs(Set<Facialacc> facialaccs) {
        this.facialaccs = facialaccs;
    }
    
    
    
}
