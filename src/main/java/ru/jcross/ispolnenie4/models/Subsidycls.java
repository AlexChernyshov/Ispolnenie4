/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Chernyshov
 */
@Entity
@Table(name = "subsidycls")
public class Subsidycls implements Serializable{
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "namek", length = 1024)
    private String namek;

    @Basic
    @Column(name = "startdate")
    private Timestamp startdate;

    @Basic
    @Column(name = "deletedate")
    private Timestamp deletedate;
    
   
    public Subsidycls() {
    }

    public Subsidycls(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getNamek() {
        return namek;
    }

    public Timestamp getStartdate() {
        return startdate;
    }

    public Timestamp getDeletedate() {
        return deletedate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNamek(String namek) {
        this.namek = namek;
    }

    public void setStartdate(Timestamp startdate) {
        this.startdate = startdate;
    }

    public void setDeletedate(Timestamp deletedate) {
        this.deletedate = deletedate;
    }

    
    
    
    
}
