/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Chernyshov
 */
@Entity
@Table(name = "dataplus")
@SequenceGenerator(name = "data_seq", sequenceName = "data_seq" )
public class DataPlus implements Serializable{
    private static final long serialVersionUID = 7521704886502026809L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "data_seq")
    @Column(name = "id")    
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "impdate", referencedColumnName = "ID")
    public ImportDate impdate;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "secfield", referencedColumnName = "ID")
    private SectionField secfield;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "facialaccid", referencedColumnName = "ID")
    private Facialacc facialaccid;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subsidyid", referencedColumnName = "ID")
    private Subsidycls subsidyid;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "kbkid", referencedColumnName = "ID")
    private Kbk kbkid;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "kosguid", referencedColumnName = "ID")
    private Kosgu kosguid;
    
    @Basic
    @Column(name = "insumma")
    private BigDecimal InSumma;

    @Basic
    @Column(name = "outsumma")
    private BigDecimal OutSumma;

    public DataPlus() {
    }

    public DataPlus(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public ImportDate getImpdate() {
        return impdate;
    }

    public SectionField getSecfield() {
        return secfield;
    }

    public Facialacc getFacialaccid() {
        return facialaccid;
    }

    public Subsidycls getSubsidyid() {
        return subsidyid;
    }

    public Kbk getKbkid() {
        return kbkid;
    }

    public Kosgu getKosguid() {
        return kosguid;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setImpdate(ImportDate impdate) {
        this.impdate = impdate;
    }

    public void setSecfield(SectionField secfield) {
        this.secfield = secfield;
    }

    public void setFacialaccid(Facialacc facialaccid) {
        this.facialaccid = facialaccid;
    }

    public void setSubsidyid(Subsidycls subsidyid) {
        this.subsidyid = subsidyid;
    }

    public void setKbkid(Kbk kbkid) {
        this.kbkid = kbkid;
    }

    public void setKosguid(Kosgu kosguid) {
        this.kosguid = kosguid;
    }

    public BigDecimal getInSumma() {
        return InSumma;
    }

    public void setInSumma(BigDecimal inSumma) {
        InSumma = inSumma;
    }

    public BigDecimal getOutSumma() {
        return OutSumma;
    }

    public void setOutSumma(BigDecimal outSumma) {
        OutSumma = outSumma;
    }

    @Override
    public String toString() {
        String st = "DataPlus{" + "id=" +String.valueOf(id) 
                + ", impdate=" + String.valueOf(impdate.getId())
                + ", secfield=" + String.valueOf(secfield.getId()) 
                + ", facialaccid=" + String.valueOf(facialaccid.getId())
                + ", subsidyid=" + String.valueOf(subsidyid.getId()) 
                + ", kbkid=" + String.valueOf(kbkid.getId()) 
                + ", kosguid=" + String.valueOf(kosguid.getId()) 
                + ", insumma=" + String.valueOf(InSumma)
                + ", outsumma=" + String.valueOf(OutSumma)
                +'}';
        return st;
    }

    
    
}
