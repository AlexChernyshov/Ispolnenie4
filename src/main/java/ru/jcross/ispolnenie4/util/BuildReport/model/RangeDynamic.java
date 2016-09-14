package ru.jcross.ispolnenie4.util.BuildReport.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Chernyshov on 12.07.2016.
 */

@XmlRootElement
public class RangeDynamic {
    private int id;
    private String name;
    private String SSQL;
    private List<Cursor> Cursors;


    public int getId() {
        return id;
    }

    @XmlAttribute(name="ident", required = true)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name="Range_Name")
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="Ssql")
    public void setSsql(String SSQL) {
        this.SSQL = SSQL;
    }
    @XmlElement(name="Cursor")
    public void setCursors(List<Cursor> cursors) {
        this.Cursors = cursors;
    }

    public String getSsql() {
        return this.SSQL;
    }

    public List<Cursor> getCursors() {
        return Cursors;
    }
}