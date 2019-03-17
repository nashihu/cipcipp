package com.cipcipp.main.Helper;
import com.cipcipp.main.Model.CellModel;
import com.cipcipp.main.Model.ColumnHeaderModel;
import com.cipcipp.main.Model.RowHeaderModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CellListGenerator {
    private List<List<CellModel>> mCellList;
    ArrayList<CellModel> mCells;
    private int row_num;
    private int col_num;

    public CellListGenerator() {
        this.mCellList = new ArrayList<>();
        this.mCells = new ArrayList<>();
        this.mRowHeaderList = new ArrayList<>();
        this.mColumnHeaderList = new ArrayList<>();
        this.rowHeaderData = new HashMap<>();
        this.row_num = row_name.length;
        this.col_num = 10;
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

    public List<List<CellModel>> getCellsData() {
        return mCellList;
    }

    public String decimalDigits(int decimaldigits, double x){
        final NumberFormat numFormat = NumberFormat.getNumberInstance();
        numFormat.setMaximumFractionDigits(decimaldigits);
        final String resultS = numFormat.format(x);
        return resultS;
    }


    String[] row_name = {"Ovo","Flip","Dana","BL","Tokped","Paytren","Payfazz","Lazada","Shopee","Gojek","Blibli"};
    String[] row_package_name = {"ovo.id","id.flip","id.dana","com.bukalapak.android","com.tokopedia.tkpd"
            ,"id.co.paytren.user","com.payfazz.android","com.lazada.android","com.shopee.id","com.gojek.app",
            "blibli.mobile.commerce"};
    ArrayList<RowHeaderModel> mRowHeaderList;
    HashMap<String,String> rowHeaderData;

    public void RowDataGenerator() {
        for(int i=0;i<row_num;i++) {
            mRowHeaderList.add(new RowHeaderModel(row_name[i],row_package_name[i]));
        }
    }

    public void RowDataGeneratorForListener() {
        for(int i=0;i<row_num;i++) {
            rowHeaderData.put(row_name[i],row_package_name[i]);
        }
    }
    public ArrayList<RowHeaderModel> DataGeneratorForMain() {
        return  mRowHeaderList;
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
