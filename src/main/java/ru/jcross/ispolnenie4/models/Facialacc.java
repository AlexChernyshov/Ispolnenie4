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
@Table(name = "facialacc")
public class Facialacc implements Serializable{
    private static final long serialVersionUID = 5432935465120709184L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    @Basic
    @Column(name = "namep",length = 1024)
    private String namep;
    
    @Basic
    @Column(name = "namek",length = 1024)
    private String namek;
    
    @Basic
    @Column(name = "dateopen",nullable = true)
    private Double  dateopen;
    
    @Basic
    @Column(name = "dateclose",nullable = true)
    private Double  dateclose;
    
   
    
    @ManyToMany
    @JoinTable(name = "gruch_facialacc" , 
            joinColumns ={@JoinColumn(name = "facialacc_id")},
            inverseJoinColumns = {@JoinColumn(name = "gruch_id")} )
    private Set<Gruch> gruchs =new HashSet<Gruch>();
    
    public Facialacc() {
        
    }

    public Facialacc(int id) {
        this.id = id;       
    }

  

    public void setId(int id) {
        this.id = id;
    }

    public void setNamep(String namep) {
        this.namep = namep;
    }

    public void setNamek(String namek) {
        this.namek = namek;
    }

    public void setDateopen(Double dateopen) {
        this.dateopen = dateopen;
    }

    public void setDateclose(Double dateclose) {
        this.dateclose = dateclose;
    }

    

    public Set<Gruch> getGruchs() {
        return gruchs;
    }

    public void setGruchs(Set<Gruch> gruchs) {
        this.gruchs = gruchs;
    }

   /* @Override
    public String toString(){
        String sb = "Facialacc{" + "id=" +  String.valueOf(id) + ", namep=" + 
                 String.valueOf(namep) + ", namek=" +  String.valueOf(namek) +
                ", dateopen= " + dateopen.toString() + ", dateclose= " + 
                 dateclose.toString() +  '}';
        return sb;
    }
*/
    public int getId() {
        return id;
    }

    public String getNamep() {
        return namep;
    }

    public String getNamek() {
        return namek;
    }

    public Double getDateopen() {
        return dateopen;
    }

    public Double getDateclose() {
        return dateclose;
    }
            
            
}
