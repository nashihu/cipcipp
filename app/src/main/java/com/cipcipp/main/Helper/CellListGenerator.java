package com.cipcipp.main.Helper;
import android.util.Log;

import com.cipcipp.main.Model.CellModel;
import com.cipcipp.main.Model.ColumnHeaderModel;
import com.cipcipp.main.Model.RowHeaderModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CellListGenerator {
    private ArrayList<List<CellModel>> mCellList;
    private ArrayList<CellModel> mCells;
    private ArrayList<String> row_names;
    private int row_num;
    private int col_num;
    String[] row_name = {"Ovo","Blibli","Flip","Dana","BL","Tokped","Paytren","Payfazz","Lazada","Shopee","Gojek"};
    String[] row_package_name = {"ovo.id",
            "blibli.mobile.commerce","id.flip","id.dana","com.bukalapak.android","com.tokopedia.tkpd"
            ,"id.co.paytren.user","com.payfazz.android","com.lazada.android","com.shopee.id","com.gojek.app"};
    ArrayList<RowHeaderModel> mRowHeaderList;
    HashMap<String,String> rowHeaderData;
    private final HashMap<String,String> prov_id = new HashMap<>();


    public CellListGenerator(int col_num,int row_num) {
        this.mCellList = new ArrayList<>();
        this.mCells = new ArrayList<>();
        this.mRowHeaderList = new ArrayList<>();
        this.mColumnHeaderList = new ArrayList<>();
        this.rowHeaderData = new HashMap<>();
        this.row_num = row_num;
        this.col_num = col_num;
    }
    public CellListGenerator(int col_num,int row_num,ArrayList<String> row_names) {
        this.mCellList = new ArrayList<>();
        this.mCells = new ArrayList<>();
        this.mRowHeaderList = new ArrayList<>();
        this.mColumnHeaderList = new ArrayList<>();
        this.rowHeaderData = new HashMap<>();
        this.row_num = row_num;
        this.col_num = col_num;
        this.row_names = row_names;
    }
    private HashMap<String,String> prov_list() {
        for(int i=0;i<row_name.length;i++) {
            prov_id.put(row_name[i],row_package_name[i]);
        }
        return prov_id;
    }
    public void RowDataGenerator() {
        prov_list();
        for(int i=0;i<row_num;i++) {
            mRowHeaderList.add(new RowHeaderModel(row_names.get(i),prov_id.get(row_names.get(i))));
        }
    }

    public void RowDataGeneratorForListener() {
        prov_list();
        Log.v("TAGGG",row_names+"");
        for(int i=0;i<row_num;i++) {
            rowHeaderData.put(row_names.get(i),prov_id.get(row_names.get(i)));
            }
    }
    public ArrayList<RowHeaderModel> GetRowData() {
        return  mRowHeaderList;
    }

    public void DataGenerator() {
        for(int i = 0; i<row_num;i++) {
            int k = 5000;
            ArrayList<CellModel> mCells = new ArrayList<>();
            for(Integer j = 0; j<col_num;j++) {
                mCells.add(new CellModel(j.toString(),  decimalDigits(2,k+(1000*Math.random()))));
                k = k + 5000;
            }
        mCellList.add(mCells);
        }
    }

    public ArrayList<List<CellModel>> getCellsData() {
        return mCellList;
    }

    public String decimalDigits(int decimaldigits, double x){
        final NumberFormat numFormat = NumberFormat.getNumberInstance();
        numFormat.setMaximumFractionDigits(decimaldigits);
        final String resultS = numFormat.format(x);
        return resultS;
    }


    public String getRowData(String key) {
        return rowHeaderData.get(key);
    }

    ArrayList<ColumnHeaderModel> mColumnHeaderList;

    public void ColumnDataGenerator() {
        int price = 5000;
        for(int i = 0 ; i < col_num; i++) {
            mColumnHeaderList.add(new ColumnHeaderModel("nominal "+price ));
            price += 5000;
        }
    }

    public ArrayList<ColumnHeaderModel> GetColumnData() {
        return mColumnHeaderList;
    }
}
