package com.cipcipp.main.model;


public class ColumnHeaderModel
//        implements Parcelable
{

    private String mData;


    public ColumnHeaderModel(String mData) {
        this.mData = mData;
    }
    public String getData() {
        return mData;
    }

//    private ColumnHeaderModel(Parcel in) {
//        String[] data = new String[1];
//        in.readStringArray(data);
//        this.mData = data[0];
//    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeStringArray(new String[] {
//                this.mData
//        });
//    }
//
//    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
//        public ColumnHeaderModel createFromParcel(Parcel in) {
//            return new ColumnHeaderModel(in);
//        }
//        public ColumnHeaderModel[] newArray(int size) {
//            return new ColumnHeaderModel[size];
//        }
//    };
}
