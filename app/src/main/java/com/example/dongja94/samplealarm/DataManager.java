package com.example.dongja94.samplealarm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongja94 on 2016-02-24.
 */
public class DataManager {
    private static DataManager instance;
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    List<Integer> items = new ArrayList<>();
    private DataManager() {

    }

    public void add(Integer hour) {
        items.add(hour);
    }

    public List<Integer> getItems() {
        return items;
    }

    public List<String> getItemsText() {
        List<String> textList = new ArrayList<>();
        for (Integer i : items) {
            textList.add("hour : " + i);
        }
        return textList;
    }
}
