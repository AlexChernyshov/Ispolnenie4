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
@Table(name = "kosgu")
public class Kosgu implements Serializable{
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

    public Kosgu() {
    }

    public Kosgu(int id) {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNamek(String namek) {
        this.namek = namek;
    }

    @Override
    public String toString() {
        return "Kosgu{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", namek='" + namek + '\'' +
                '}';
    }
}
