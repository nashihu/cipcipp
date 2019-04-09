package com.cipcipp.main.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RowHeaderModel implements Parcelable {

    private String mData;
    private String key;

    public RowHeaderModel(String mData,String key) {
        this.mData = mData;
        this.key = key;
    }

    public String getData() {
        return mData;
    }

    private RowHeaderModel(Parcel in) {
        String[] data = new String[2];
        in.readStringArray(data);
        this.mData = data[0];
        this.key = data[1];
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {
                this.mData,
                this.key});

    }

        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
            public RowHeaderModel createFromParcel(Parcel in) {
                return new RowHeaderModel(in);
            }
            public RowHeaderModel[] newArray(int size) {
                return new RowHeaderModel[size];
            }
        };
}
