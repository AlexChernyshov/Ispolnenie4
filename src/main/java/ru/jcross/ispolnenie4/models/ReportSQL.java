package ru.jcross.ispolnenie4.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Chernyshov on 21.04.2016.
 */
@Entity
@Table(name = "ReportSQL")
@SequenceGenerator(name="rsql_seq", sequenceName="rsql_seq")
public class ReportSQL implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "rsql_seq")
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "ordquery")
    private int ordquery;

    @Basic
    @Column(name = "query_report",length = 8192)
    private String query;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ReportCaptionID")
    private ReportCaption ReportCaptionID;


    public ReportSQL(int id) {
        this.id = id;
    }

    public ReportSQL() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrdquery() {
        return ordquery;
    }

    public void setOrdquery(int ordquery) {
        this.ordquery = ordquery;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public ReportCaption getReportCaptionID() {
        return ReportCaptionID;
    }

    public void setReportCaptionID(ReportCaption reportCaptionID) {
        ReportCaptionID = reportCaptionID;
    }
}
