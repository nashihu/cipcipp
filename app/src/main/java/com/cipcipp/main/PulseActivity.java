package com.cipcipp.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cipcipp.main.Helper.CellListGenerator;
import com.cipcipp.main.Helper.OpenApp;
import com.cipcipp.main.Model.CellModel;
import com.cipcipp.main.TableEngine.ProviderAdapter;
import com.evrencoskun.tableview.TableView;
import com.cipcipp.main.TableEngine.MyTableViewAdapter;
import com.cipcipp.main.TableEngine.MyTableViewListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PulseActivity extends AppCompatActivity implements ProviderAdapter.ItemClickListener {
    public static Context context;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private TextView textView;
    private ArrayList<String> rowNames = new ArrayList<>();
    private List<List<CellModel>> prices = new ArrayList<>();
    private String title;
    private ProviderAdapter adapterProv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pulse_activity);
        title = getIntent().getStringExtra("title");
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().getReference().child(title);
        databaseReference = database.getReference("testing").child(title); //Telkomsel,etc
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(Integer m = 0;m<dataSnapshot.getChildrenCount();m++) {
////                    Log.v("TAGGG",""+dataSnapshot.child("BL").child("0").getValue());
//                    for(Integer n =0;n<(dataSnapshot.child(m.toString()).getChildrenCount()-2);n++) {
//                        Log.v("TAGGG",""+dataSnapshot.child(m.toString()).child(n.toString()));
//                    }
//                }
                for (DataSnapshot child_i : dataSnapshot.getChildren()) {
                    String rownum = child_i.getKey();
                    rowNames.add(rownum);
                    Log.v("TAGGG",""+rownum);
                    ArrayList<CellModel> price_= new ArrayList<>();
                    for(Integer i =0;i<(child_i.getChildrenCount());i++) {
//                        Log.v("TAGGG",""+dataSnapshot.child(rownum).child(i.toString()).getValue());
//                        String price = child_i.child(rownum).child(i.toString()).getValue(String.class);
//                        Log.v("TAGGG",price+"");
//                        Log.v("TAGGG",""+child_i.child(rownum).child(i.toString()));
//                        Log.v("TAGGG",""+child_i.child(child_i.getKey()));
//                        price_.add(new CellModel(i.toString()));
                        price_.add(new CellModel(i.toString(),dataSnapshot.child(rownum).child(i.toString()).getValue()));
                    }
                    if(!rownum.equals("nominal")) {
                        if(!rownum.equals("updatedAt")) {
                            prices.add(price_);
                        }
                    }
                    }
//                Log.v("TAGGG",""+prices.get(4).get(12).getData());
                Toast.makeText(PulseActivity.this,"updatedAt: "+dataSnapshot.child("updatedAt").getValue(),Toast.LENGTH_SHORT).show();
//                Log.v("TAGGG",""+prices.get(0).get(0).getData());
                int params = getIntent().getIntExtra("params",2);
                ArrayList<Integer> provider_id = new ArrayList<>();
                ArrayList<String> provider_title = new ArrayList<>();
                for(Integer i = 0; i<rowNames.size()-2;i++) {
                    provider_id.add(R.mipmap.ic_launcher);
                    provider_title.add(rowNames.get(i));
                }
                CellListGenerator mRowHeaderLists = new CellListGenerator(prices.get(0).size(),(prices.size()),rowNames);
                mRowHeaderLists.RowDataGenerator();
                CellListGenerator mColumnHeaderList = new CellListGenerator(prices.get(0).size(),(prices.size()));
                mColumnHeaderList.ColumnDataGenerator();
//                CellListGenerator mCellLists = new CellListGenerator(15,4);
//                mCellLists.DataGenerator();
                RecyclerView providerRV = findViewById(R.id.rvProvider);
                LinearLayoutManager horizontalLayoutManager
                        = new LinearLayoutManager(PulseActivity.this, LinearLayoutManager.HORIZONTAL, false);
                providerRV.setLayoutManager(horizontalLayoutManager);
                adapterProv = new ProviderAdapter(PulseActivity.this,provider_title,provider_id);
                adapterProv.setClickListener(PulseActivity.this);
                TableView tableView = findViewById(R.id.content_container);
                MyTableViewAdapter mTableViewAdapter = new MyTableViewAdapter(PulseActivity.this);
                tableView.setAdapter(mTableViewAdapter);
                tableView.setTableViewListener(new MyTableViewListener());
                mTableViewAdapter.setAllItems(mColumnHeaderList.GetColumnData(), mRowHeaderLists.GetRowData(), prices);
                providerRV.setAdapter(adapterProv);
                PulseActivity.context = getApplicationContext();
                textView = findViewById(R.id.pulsa_title);
                textView.setText(textView.getText() + title);
                Log.v("TAGGG",""+rowNames);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAGGG","ga nemu coy");

            }
        });


//        textView = findViewById(R.id.pulsa_title);
//        int params = getIntent().getIntExtra("params",2);
//        textView.setText(textView.getText() + title);
//        CellListGenerator mRowHeaderLists = new CellListGenerator(15,4);
//        mRowHeaderLists.RowDataGenerator();
//        CellListGenerator mColumnHeaderList = new CellListGenerator(15,4);
//        mColumnHeaderList.ColumnDataGenerator();
//        CellListGenerator mCellLists = new CellListGenerator(15,4);
//        mCellLists.DataGenerator();
//        TableView tableView = findViewById(R.id.content_container);
//        MyTableViewAdapter mTableViewAdapter = new MyTableViewAdapter(this);
//        tableView.setAdapter(mTableViewAdapter);
//        tableView.setTableViewListener(new MyTableViewListener());
//        mTableViewAdapter.setAllItems(mColumnHeaderList.GetColumnData(), mRowHeaderLists.GetRowData(), prices);
//        PulseActivity.context = getApplicationContext();

    }
    public static Context getAppContext() {
        return PulseActivity.context;
    }

    public PulseActivity() {
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("testing").child("Telkomsel"); //Telkomsel,etc

    }
    @Override
    public void onItemClick(View view, int position) {
        String[] row_name = {"Ovo","Blibli","Flip","Dana","BL","Tokped","Paytren","Payfazz","Lazada","Shopee","Gojek"};
        String[] row_package_name = {"ovo.id",
                "blibli.mobile.commerce","id.flip","id.dana","com.bukalapak.android","com.tokopedia.tkpd"
                ,"id.co.paytren.user","com.payfazz.android","com.lazada.android","com.shopee.id","com.gojek.app"};
        HashMap<String,String> packagename = new HashMap<>();
        for(int i =0; i<row_name.length;i++) {
            packagename.put(row_name[i],row_package_name[i]);
        }
        OpenApp.openApp(PulseActivity.getAppContext(),packagename.get(adapterProv.getItem(position)));
//        Toast.makeText(this,"km pencet "+ adapterProv.getItem(position) + "on item pos: " + position, Toast.LENGTH_SHORT).show();
    }
}