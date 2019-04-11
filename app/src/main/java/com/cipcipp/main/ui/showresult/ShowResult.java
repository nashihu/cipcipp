package com.cipcipp.main.ui.showresult;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cipcipp.main.R;
import com.cipcipp.main.engine.MyTableViewAdapter;
import com.cipcipp.main.engine.MyTableViewListener;
import com.cipcipp.main.helper.CellListGenerator;
import com.cipcipp.main.helper.FirebaseHelper;
import com.cipcipp.main.model.CellModel;
import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowResult extends AppCompatActivity {
    private final String TAG = "ShowResult";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        final String title = getIntent().getStringExtra("title");
        TextView tvTitle = findViewById(R.id.result_title);
        String title2 = tvTitle.getText().toString() + " " + title;
        tvTitle.setText(title2);


        FirebaseHelper helper = new FirebaseHelper(ShowResult.this,title);
        helper.readReports(new showresultint() {
            @Override
            public void onCallBack(ArrayList<String> strings, List<List<CellModel>> cellModels, ArrayList<String> rowNum) {
                String[] filter = Arrays.copyOf(strings.toArray(),strings.toArray().length,String[].class);
                filterSpinner(filter,cellModels,rowNum);
            }
        });
    }

    private void filterSpinner(String[] values, final List<List<CellModel>> cellModels, final ArrayList<String> rowNum) {
        final Spinner spinner2 = findViewById(R.id.result_spinner);
        ArrayAdapter adapterspinner2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,values);
        adapterspinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapterspinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ArrayList<String> colVallz = new ArrayList<>(Arrays.asList("Nominal", "Harga","Report date","ss image", "issuer"));
                Log.v(TAG,"selected position "+String.valueOf(position));
                CellListGenerator mRowHeaderLists = new CellListGenerator(cellModels.get(0).size(),(cellModels.size()),rowNum);
                mRowHeaderLists.RowDataGenerator();
                CellListGenerator mColumnHeaderList = new CellListGenerator(colVallz.size(),(cellModels.size()),rowNum,colVallz);
                mColumnHeaderList.ColumnDataGenerator(false);
                TableView tableView = findViewById(R.id.result_table);
                tableView.setHorizontalScrollBarEnabled(true);
                MyTableViewAdapter mTableViewAdapter = new MyTableViewAdapter(ShowResult.this);
                tableView.setAdapter(mTableViewAdapter);
                tableView.setTableViewListener(new MyTableViewListener(ShowResult.this,true,cellModels));

                if(position==1000) {
                    List<List<CellModel>> hider =  looper(parent,true);
                    mTableViewAdapter.setAllItems(mColumnHeaderList.GetColumnData(), mRowHeaderLists.GetRowData(), hider);

                } else {
                    List<List<CellModel>> hider =  looper(parent,false);
                    mTableViewAdapter.setAllItems(mColumnHeaderList.GetColumnData(), mRowHeaderLists.GetRowData(), hider);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
            private List<List<CellModel>> looper(AdapterView<?> parent, boolean isFiltered) {
                List<List<CellModel>> hider = new ArrayList<>();
                for (List<CellModel> rows : cellModels) {
                    List<CellModel> rowz = new ArrayList<>();
                    if(rows.get(4).getData().toString().equals(parent.getSelectedItem().toString()) && isFiltered) {
                        looperz(rows,rowz);
                    } else {
                        looperz(rows,rowz);
                    }
                    hider.add(rowz);
                }return hider;
            }

            private void looperz(List<CellModel> rows, List<CellModel> rowz) {
                for (int i = 0; i< rows.size();i++) {
                    if(i==3){
                        rowz.add(new CellModel("o","klik di sini"));
                    } else {
                        rowz.add(rows.get(i));
                    }
                }
            }
        });
    }
}
