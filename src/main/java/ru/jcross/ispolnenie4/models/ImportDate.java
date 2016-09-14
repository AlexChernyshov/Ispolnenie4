/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Chernyshov
 */
@Entity
@Table(name = "ImportDate")
@SequenceGenerator(name="impdate_seq", sequenceName="impdate_seq")
public class ImportDate implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "impdate_seq")
    @Column(name = "id")
    public Integer id;
    
    @Temporal(TemporalType.DATE)
    @Column(name="ondate", nullable = false, insertable = true)
    private Date ondate;
    
    @Basic
    @Column(name = "createdate")
    private Timestamp createdate;
    
    @Basic
    @Column(name = "note", nullable=false, insertable=true, updatable=true, length=150)
    private String note;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "impdate", cascade = CascadeType.REMOVE)
    private List<DataPlus> datapluss =  new ArrayList<>();

    public ImportDate() {
    }

    public ImportDate(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Date getOndate() {
        return ondate;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public String getNote() {
        return note;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOndate(Date ondate) {
        this.ondate = ondate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<DataPlus> getDatapluss() {
        return datapluss;
    }

    public void setDatapluss(List<DataPlus> datapluss) {
        this.datapluss = datapluss;
    }
}
