package ru.jcross.ispolnenie4.inter;

import javafx.collections.ObservableList;
import ru.jcross.ispolnenie4.models.ReportCaption;
import ru.jcross.ispolnenie4.models.ReportSQL;

/**
 * Created by Chernyshov on 22.04.2016.
 */
public interface InterReportSQL {
    void insert(ReportCaption reportCaption,ReportSQL reportSQL);
    void edit(ReportCaption reportCaption, ReportSQL reportSQL);
    void del(ReportSQL reportSQL);
    ObservableList<ReportSQL> getAll(ReportCaption reportCaption);
}
