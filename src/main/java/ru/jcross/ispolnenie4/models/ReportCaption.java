package ru.jcross.ispolnenie4.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ReportCaption")
@SequenceGenerator(name="report_seq", sequenceName="report_seq")
public class ReportCaption implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "report_seq")
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "totemplate")
    private String totemplate;

    @Basic
    @Column(name = "lock")
    private Integer visible;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ReportCaptionID")
    private List<ReportSQL> reportsql =  new ArrayList<>();

    public ReportCaption(int id) {
        this.id = id;
    }

    public ReportCaption() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotemplate() {
        return totemplate;
    }

    public void setTotemplate(String totemplate) {
        this.totemplate = totemplate;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public List<ReportSQL> getReportsql() {
        return reportsql;
    }

    public void setReportsql(List<ReportSQL> reportsql) {
        this.reportsql = reportsql;
    }

    @Override
    public String toString() {
        return "# "+ title;
    }
}

