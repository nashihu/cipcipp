package com.example.cipcipp.evenrocskuntesttable.Model;

public class RowHeaderModel {

    private String mData;
    private String key;

    public RowHeaderModel(String mData) {
        this.mData = mData;
    }
    public RowHeaderModel(String mData,String key) {
        this.mData = mData;
        this.key = key;
    }

    public String getData() {
        return mData;
    }

    public String getKey() {
        return key;
    }

}
