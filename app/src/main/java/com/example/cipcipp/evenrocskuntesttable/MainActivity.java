package com.example.cipcipp.evenrocskuntesttable;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evrencoskun.tableview.TableView;
import com.example.cipcipp.evenrocskuntesttable.Model.CellModel;
import com.example.cipcipp.evenrocskuntesttable.Model.ColumnHeaderModel;
import com.example.cipcipp.evenrocskuntesttable.Model.RowHeaderGenerator;
import com.example.cipcipp.evenrocskuntesttable.Model.RowHeaderModel;
import com.example.cipcipp.evenrocskuntesttable.TableEngine.MyTableViewAdapter;
import com.example.cipcipp.evenrocskuntesttable.TableEngine.MyTableViewListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Context context;

    public ArrayList<RowHeaderModel> mRowHeaderList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create Column Header list
        ArrayList<ColumnHeaderModel> mColumnHeaderList = new ArrayList<>();
        int col_num = 10;
        int row_num = 30;
        for(int i = 0; i<col_num; i++) {
            mColumnHeaderList.add(new ColumnHeaderModel("column "+(i+1)));
        }

        // Create Row Header list
        RowHeaderGenerator mRowHeaderLists = new RowHeaderGenerator();
        mRowHeaderLists.DataGenerator();
        List<List<CellModel>> mCellList = new ArrayList<>();
        int k = 1;
        for(Integer i =0; i<mRowHeaderLists.row_num;i++) {
            ArrayList<CellModel> cells = new ArrayList<>();
            for(Integer j =0; j<col_num; j++) {
                cells.add(new CellModel(j.toString(),k*Math.random()));

            }
            k = k + 1;
            mCellList.add(cells);

        }

        TableView tableView = (TableView) findViewById(R.id.content_container);
        MyTableViewAdapter mTableViewAdapter = new MyTableViewAdapter(this);
        tableView.setAdapter(mTableViewAdapter);
        tableView.setTableViewListener(new MyTableViewListener());
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderLists.DataGeneratorForMain(), mCellList);
        MainActivity.context = getApplicationContext();
    }
    private List<List<CellModel>> createDummyData(int row_num, int col_num) {
        List<List<CellModel>> mCellList = new ArrayList<>();
        for(int i =0; i<row_num; i++) {
            ArrayList<CellModel> cells = new ArrayList<>();
            for(Integer j = 0;j<col_num; j++) {
                cells.add(new CellModel(j.toString(),"Cell 1 - " +j));
            }
        mCellList.add(cells);
        }
        return mCellList;
    }
    public static Context getAppContext() {
        return MainActivity.context;
    }
}
