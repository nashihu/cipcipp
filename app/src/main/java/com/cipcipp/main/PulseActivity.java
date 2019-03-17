package com.cipcipp.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.cipcipp.main.Helper.CellListGenerator;
import com.evrencoskun.tableview.TableView;
import com.cipcipp.main.Model.CellModel;
import com.cipcipp.main.Model.ColumnHeaderModel;
import com.cipcipp.main.Helper.RowHeaderGenerator;
import com.cipcipp.main.TableEngine.MyTableViewAdapter;
import com.cipcipp.main.TableEngine.MyTableViewListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class PulseActivity extends AppCompatActivity {
    public static Context context;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Pulsa Telkomsel");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("Bukalapak").child("Nominal 10000").getValue(String.class);
                Toast.makeText(PulseActivity.this,value,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        textView = findViewById(R.id.pulsa_title);
        String title = getIntent().getStringExtra("title");
        textView.setText(textView.getText() + title);
        ArrayList<ColumnHeaderModel> mColumnHeaderList = getIntent().getParcelableArrayListExtra("columnList");
//        RowHeaderGenerator mRowHeaderLists = new RowHeaderGenerator();
//        mRowHeaderLists.DataGenerator();
        CellListGenerator mRowHeaderLists = new CellListGenerator();
        mRowHeaderLists.RowDataGenerator();
        CellListGenerator mCellLists = new CellListGenerator();
        mCellLists.DataGenerator();
        TableView tableView = findViewById(R.id.content_container);
        MyTableViewAdapter mTableViewAdapter = new MyTableViewAdapter(this);
        tableView.setAdapter(mTableViewAdapter);
        tableView.setTableViewListener(new MyTableViewListener());
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderLists.DataGeneratorForMain(), mCellLists.getCellsData());
        PulseActivity.context = getApplicationContext();
    }
    public static Context getAppContext() {
        return PulseActivity.context;
    }
}