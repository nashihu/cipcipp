package com.cipcipp.main;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evrencoskun.tableview.TableView;
import com.cipcipp.main.Model.CellModel;
import com.cipcipp.main.Model.ColumnHeaderModel;
import com.cipcipp.main.Model.RowHeaderGenerator;
import com.cipcipp.main.TableEngine.MyTableViewAdapter;
import com.cipcipp.main.TableEngine.MyTableViewListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<ColumnHeaderModel> mColumnHeaderList = new ArrayList<>();
        int col_num = 10;
        int price = 5000;
        for(int i = 0; i<col_num; i++) {
            mColumnHeaderList.add(new ColumnHeaderModel("nominal "+(price)));
            price += 5000;
        }
        RowHeaderGenerator mRowHeaderLists = new RowHeaderGenerator();
        mRowHeaderLists.DataGenerator();
        List<List<CellModel>> mCellList = new ArrayList<>();

        for(Integer i =0; i<mRowHeaderLists.row_num;i++) {
            ArrayList<CellModel> cells = new ArrayList<>();
            int k = 5000;
            for(Integer j =0; j<col_num; j++) {
                cells.add(new CellModel(j.toString(),decimalDigits(2,k+(100*Math.random()))));
                k = k + 5000;
            }

            mCellList.add(cells);
        }
        TableView tableView = (TableView) findViewById(R.id.content_container);
        MyTableViewAdapter mTableViewAdapter = new MyTableViewAdapter(this);
        tableView.setAdapter(mTableViewAdapter);
        tableView.setTableViewListener(new MyTableViewListener());
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderLists.DataGeneratorForMain(), mCellList);
        MainActivity.context = getApplicationContext();
    }
    public static Context getAppContext() {
        return MainActivity.context;
    }
    public String decimalDigits(int decimaldigits, double x){
        final NumberFormat numFormat = NumberFormat.getNumberInstance();
        numFormat.setMaximumFractionDigits(decimaldigits);
        final String resultS = numFormat.format(x);
        return resultS;
    }
}