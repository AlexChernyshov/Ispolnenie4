package ru.jcross.ispolnenie4.util.BuildReport.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Chernyshov on 12.07.2016.
 */
@XmlRootElement( name = "Cursor")
public class Cursor implements Serializable{
    private  int id;
    private  int style;
    private  List<TargetCell> ListCells;


    @XmlAttribute(name="ident", required = true)
    public void setId(int id) {
        this.id = id;
    }

    @XmlElement( name = "Style")
    public void setStyle(int style) {
        this.style = style;
    }
    @XmlElement( name = "TargetCell")
    public void setListCells(List<TargetCell> ListCells) {
        this.ListCells = ListCells;
    }

    public int getId() {
        return id;
    }

    public int getStyle() {
        return style;
    }

    public List<TargetCell> getListCells() {
        return ListCells;
    }

    @Override
    public String toString() {
        return "Cursor{" +
                "id=" + id +
                ", style=" + style +
                ", ListCells=" + "<...>" +
                '}';
    }
}