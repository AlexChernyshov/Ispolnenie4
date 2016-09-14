package ru.jcross.ispolnenie4.util.BuildReport.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Chernyshov on 12.07.2016.
 */
@XmlRootElement(name="TargetCell")
public class TargetCell implements Serializable{
    private int id;
    private int style;
    private int format;
    private String from;
    private String toCell;
    private String ValString;
    @XmlAttribute(name="ident", required = true)
    public void setId(int id) {
        this.id = id;
    }
    @XmlElement( name = "Style")
    public void setStyle(int style) {
        this.style = style;
    }
    @XmlElement( name = "Format")
    public void setFormat(int format) {
        this.format = format;
    }
    @XmlElement( name = "From")
    public void setFrom(String from) {
        this.from = from;
    }
    @XmlElement( name = "ToCell")
    public void setTocell(String toCell) {
        this.toCell = toCell;
    }
    @XmlElement( name = "Val")
    public void setValstring(String valString) {
        this.ValString = valString;
    }

    public int getId() {
        return id;
    }

    public int getStyle() {
        return style;
    }

    public int getFormat() {
        return format;
    }

    public String getFrom() {
        return from;
    }

    public String getTocell() {
        return toCell;
    }

    public String getValstring() {
        return ValString;
    }
}