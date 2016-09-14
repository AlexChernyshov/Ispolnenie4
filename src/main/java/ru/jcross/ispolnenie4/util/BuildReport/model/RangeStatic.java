package ru.jcross.ispolnenie4.util.BuildReport.model;

import java.util.Set;

/**
 * Created by Chernyshov on 15.08.2016.
 */
public class RangeStatic {
    private int id;
    private String name;
    private String SSQL;
    private Set<TargetCell> setTargetCell;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSSQL() {
        return SSQL;
    }

    public void setSSQL(String SSQL) {
        this.SSQL = SSQL;
    }

    public Set<TargetCell> getSetTargetCell() {
        return setTargetCell;
    }

    public void setSetTargetCell(Set<TargetCell> setTargetCell) {
        this.setTargetCell = setTargetCell;
    }
}
