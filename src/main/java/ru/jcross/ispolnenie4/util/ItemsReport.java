package ru.jcross.ispolnenie4.util;

import java.math.BigDecimal;

/**
 * Created by Chernyshov on 18.04.2016.
 */
public class ItemsReport {
    private Short lvl;
    private String name;
    private int ls;
    private String subsidy;
    private String kvr;
    private BigDecimal insumma;
    private BigDecimal outsumma;

    public ItemsReport() {
    }

    public ItemsReport(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "ItemsReport{" +
                "lvl=" + lvl +
                ", name='" + name + '\'' +
                ", ls=" + ls +
                ", subsidy='" + subsidy + '\'' +
                ", kvr='" + kvr + '\'' +
                ", insumma=" + insumma +
                ", outsumma=" + outsumma +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(String subsidy) {
        this.subsidy = subsidy;
    }

    public String getKvr() {
        return kvr;
    }

    public void setKvr(String kvr) {
        this.kvr = kvr;
    }

    public BigDecimal getInsumma() {
        return insumma;
    }

    public void setInsumma(BigDecimal insumma) {
        this.insumma = insumma;
    }

    public BigDecimal getOutsumma() {
        return outsumma;
    }

    public void setOutsumma(BigDecimal outsumma) {
        this.outsumma = outsumma;
    }
}
