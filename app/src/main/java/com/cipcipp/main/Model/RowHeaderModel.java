package com.cipcipp.main.Model;

public class RowHeaderModel {

    private String mData;
    private String key;

    public RowHeaderModel(String mData,String key) {
        this.mData = mData;
        this.key = key;
    }

    public String getData() {
        return mData;
    }

}
