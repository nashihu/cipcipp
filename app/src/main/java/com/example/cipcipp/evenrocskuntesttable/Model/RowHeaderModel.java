package com.example.cipcipp.evenrocskuntesttable.Model;

public class RowHeaderModel {

    private String mData;
    private String data2;
    public RowHeaderModel() {
    }

    public RowHeaderModel(String mData) {
        this.mData = mData;
    }
    public RowHeaderModel(String mData, String data2) {
        this.mData = mData;
        this.data2 = data2;
    }

    public String getData() {
        return mData;
    }

    public void setData(String mData) {
        this.mData = mData;
    }

}
