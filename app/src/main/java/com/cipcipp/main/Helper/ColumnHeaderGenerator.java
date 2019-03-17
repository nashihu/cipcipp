package com.cipcipp.main.Helper;

import com.cipcipp.main.Model.ColumnHeaderModel;

import java.util.ArrayList;

public class ColumnHeaderGenerator {
    ArrayList<ColumnHeaderModel> mColumnHeaderList;
    public int col_num;
    public ColumnHeaderGenerator() {
        this.mColumnHeaderList = new ArrayList<>();
        this.col_num = 10;
    }

    public void DataGenerator() {
        int price = 5000;
        for(int i = 0 ; i < col_num; i++) {
            mColumnHeaderList.add(new ColumnHeaderModel("nominal "+price ));
            price += 5000;
        }
    }

    public ArrayList<ColumnHeaderModel> GetColumnData() {
        return mColumnHeaderList;
    }

}
