package ru.jcross.ispolnenie4.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Chernyshov on 22.09.2016.
 */

@Entity
@Table(name = "MeansType")
public class MeansType implements Serializable {
    private static final long serialVersionUID = 5237435665120809151L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;


    @Basic
    @Column(name = "code", nullable=false, insertable= true, updatable=true, length=6, unique=true)
    private String code;

    @Basic
    @Column(name = "namek", length = 1024)
    private String namek;

    public MeansType() {
    }

    public MeansType(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNamek() {
        return namek;
    }

    public void setNamek(String namek) {
        this.namek = namek;
    }
}
