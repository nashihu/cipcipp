package com.cipcipp.main.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class RowHeaderGenerator {
    String[] row_name = {"Ovo","Flip","Dana","BL","Tokped","Paytren","Payfazz","Lazada","Shopee","Gojek","Blibli"};
    String[] row_package_name = {"ovo.id","id.flip","id.dana","com.bukalapak.android","com.tokopedia.tkpd"
    ,"id.co.paytren.user","com.payfazz.android","com.lazada.android","com.shopee.id","com.gojek.app",
    "blibli.mobile.commerce"};
    public int row_num;
    ArrayList<RowHeaderModel> mRowHeaderList;
    HashMap<String,String> rowHeaderData;

    public RowHeaderGenerator() {
        this.row_num = row_name.length;
        this.mRowHeaderList = new ArrayList<>();
        this.rowHeaderData = new HashMap<>();
    }

    public void DataGenerator() {
        for(int i=0;i<row_num;i++) {
            mRowHeaderList.add(new RowHeaderModel(row_name[i],row_package_name[i]));
        }
    }

    public void DataGeneratorForListener() {
        for(int i=0;i<row_num;i++) {
            rowHeaderData.put(row_name[i],row_package_name[i]);
        }

    }

    public ArrayList<RowHeaderModel> DataGeneratorForMain() {
        return  mRowHeaderList;
    }

    public String getData(String key) {
        return rowHeaderData.get(key);
    }
}
