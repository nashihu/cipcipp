package com.example.cipcipp.evenrocskuntesttable;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evrencoskun.tableview.TableView;
import com.example.cipcipp.evenrocskuntesttable.Model.CellModel;
import com.example.cipcipp.evenrocskuntesttable.Model.ColumnHeaderModel;
import com.example.cipcipp.evenrocskuntesttable.Model.RowHeaderModel;
import com.example.cipcipp.evenrocskuntesttable.TableEngine.MyTableViewAdapter;
import com.example.cipcipp.evenrocskuntesttable.TableEngine.MyTableViewListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<RowHeaderModel> mRowHeaderList = new ArrayList<>();
    private List<ColumnHeaderModel> mColumnHeaderList = new ArrayList<>();
    private List<List<CellModel>> mCellList = new ArrayList<>();
    private List<CellModel> anak = new ArrayList<>();
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create Column Header list
        ArrayList<ColumnHeaderModel> mColumnHeaderList = new ArrayList<>();
        int col_num = 10;
        int row_num = 30;
        for(int i = 0; i<col_num; i++) {
            mColumnHeaderList.add(new ColumnHeaderModel("column "+i));
        }

        // Create Row Header list
        ArrayList<RowHeaderModel> mRowHeaderList = new ArrayList<>();

        for(int i = 0; i<row_num; i++) {
            mRowHeaderList.add(new RowHeaderModel("row "+i));
        }
        List<List<CellModel>> mCellList = new ArrayList<>();
        for(Integer i =0; i<row_num;i++) {
            ArrayList<CellModel> cells = new ArrayList<>();
            for(Integer j =0; j<col_num; j++) {
                cells.add(new CellModel(j.toString(),"Cell "+i+"-"+j));
            }
            mCellList.add(cells);
        }

        TableView tableView = (TableView) findViewById(R.id.content_container);
        MyTableViewAdapter mTableViewAdapter = new MyTableViewAdapter(this);
        tableView.setAdapter(mTableViewAdapter);
        tableView.setTableViewListener(new MyTableViewListener());
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
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
