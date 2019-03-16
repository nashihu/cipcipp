package com.example.cipcipp.evenrocskuntesttable.Model;

import java.util.ArrayList;

public class RowHeaderGenerator {
    String[] row_name = {"Ovo","Flip","Dana","BL","Tokped","Paytren","Payfazz","Lazada","Shopee","Gojek","Blibli"};
    String[] row_package_name = {"ovo.id","id.flip","id.dana","com.bukalapak.android","com.tokopedia.tkpd"
    ,"id.co.paytren.user","com.payfazz.android","com.lazada.android","com.shopee.id","com.gojek.app",
    "blibli.mobile.commerce"};
    public int row_num;
    ArrayList<RowHeaderModel> mRowHeaderList;

    public RowHeaderGenerator() {
        this.row_num = row_name.length;
        this.mRowHeaderList = new ArrayList<>();
    }

    public RowHeaderGenerator(int row_num, ArrayList<RowHeaderModel> mRowHeaderList) {
        this.row_num = row_num;
        this.mRowHeaderList = mRowHeaderList;
    }

    public void DataGenerator() {
        for(int i=0;i<row_num;i++) {
            mRowHeaderList.add(new RowHeaderModel(row_name[i],row_package_name[i]));
        }
    }

    public ArrayList<RowHeaderModel> DataGeneratorForMain() {
        return  mRowHeaderList;
    }

    public String getData(int num) {
        return mRowHeaderList.get(num).getKey();
    }
}
