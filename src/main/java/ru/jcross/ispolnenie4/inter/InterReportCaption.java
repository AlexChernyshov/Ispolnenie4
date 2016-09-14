package ru.jcross.ispolnenie4.inter;

import javafx.collections.ObservableList;
import ru.jcross.ispolnenie4.models.ReportCaption;

/**
 * Created by Chernyshov on 22.04.2016.
 */
public interface InterReportCaption {
    void insert(ReportCaption reportCaption);
    void edit(ReportCaption reportCaption);
    void del(ReportCaption reportCaption);
    ObservableList<ReportCaption> getAll();
}
