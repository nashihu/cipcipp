package com.cipcipp.main.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.evrencoskun.tableview.sort.ISortableModel;

public class CellModel implements ISortableModel, Parcelable {
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

    public CellModel (Parcel in) {
        String[] data = new String[2];
        in.readStringArray(data);
        this.mId = data[0];
        this.mData = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeStringArray(new String[] {
                this.mId,
                this.mData.toString(),
                });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CellModel createFromParcel(Parcel in) {
            return new CellModel(in);
        }
        public CellModel[] newArray(int size) {
            return new CellModel[size];
        }
    };
}
