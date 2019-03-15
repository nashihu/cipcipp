package com.example.cipcipp.evenrocskuntesttable.Model;

public class ColumnHeaderModel {

    private String mData;
    private String data2;


    public ColumnHeaderModel(String mData) {
        this.mData = mData;
    }
    public ColumnHeaderModel(String mData,String data2) {
        this.mData = mData;
        this.data2 = data2;
    }
    public String getData() {
        return mData;
    }
}
