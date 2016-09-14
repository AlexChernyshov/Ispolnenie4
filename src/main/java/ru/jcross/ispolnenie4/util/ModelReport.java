package ru.jcross.ispolnenie4.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chernyshov on 18.04.2016.
 */
public class ModelReport {
    private  Map<String,String> attribute = new HashMap<>();
    private  List<ItemsReport> data = new ArrayList<>();

    public ModelReport() {
    }

    public ModelReport(Map<String, String> attribute, List<ItemsReport> data) {
        this.attribute = attribute;
        this.data = data;
    }

    public Map<String, String> getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Map<String, String> attribute) {
        this.attribute = attribute;
    }

    public List<ItemsReport> getData() {
        return data;
    }

    public void setData(List<ItemsReport> data) {
        this.data = data;
    }
}
