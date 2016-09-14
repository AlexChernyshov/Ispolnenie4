/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jcross.ispolnenie4.models;
import java.io.Serializable;
/**
 *
 * @author Chernyshov
 */
public class ImportID implements Serializable{
    private String Section;
    private String Status;

    public ImportID(String Section, String Status) {
        this.Section = Section;
        this.Status = Status;
    }

    public String getSection() {
        return Section;
    }

    public String getStatus() {
        return Status;
    }

    public void setSection(String Section) {
        this.Section = Section;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
}
