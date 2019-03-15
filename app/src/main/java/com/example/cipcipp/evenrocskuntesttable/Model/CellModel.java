package com.example.cipcipp.evenrocskuntesttable.Model;

import com.evrencoskun.tableview.sort.ISortableModel;

public class CellModel implements ISortableModel {
    private String mId;
    private Object mData;

    public CellModel(String id, Object mData) {
        this.mId = id;
        this.mData = mData;
    }

    public Object getData() {return mData;}

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public Object getContent() {
        return mData;
    }
}
