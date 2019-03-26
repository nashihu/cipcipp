package com.cipcipp.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cipcipp.main.Helper.CellListGenerator;
import com.cipcipp.main.Helper.OpenApp;
import com.cipcipp.main.Model.CellModel;
import com.cipcipp.main.Model.Contact;
import com.cipcipp.main.TableEngine.DatabaseHandler;
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
    private TextView appopen;
    private ArrayList<String> rowNames = new ArrayList<>();
    private ArrayList<String> colVals = new ArrayList<>();
    private List<List<CellModel>> prices = new ArrayList<>();
    private String title;
    private ProviderAdapter adapterProv;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pulse_activity);

        DatabaseHandler db = new DatabaseHandler(this);
        Log.d("Insert: ", "Inserting..");
        db.addContact(new Contact("maya","87878787"));
        db.addContact(new Contact("dewi","89898989"));
        db.addContact(new Contact("doni","90909090"));
        db.addContact(new Contact("nani","76767676"));

        Log.d("Reading..","Reading all contacts....");
        List<Contact> contactList = db.getAllContacts();
        for (Contact c : contactList) {
            String log = "ID: "+c.getId()+" ,Name: "+c.getName()+" ,Phone, " +c.getPhoneNumber();
            Log.d("Name: ",log);
        }
        title = getIntent().getStringExtra("title");
        progressBar = findViewById(R.id.pb_loading_indicator);
        textView = findViewById(R.id.pulsa_title);
        appopen = findViewById(R.id.app_open_text);
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().getReference().child(title);
        databaseReference = database.getReference("testing").child(title); //Telkomsel,etc
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rowNames = new ArrayList<>();

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
                        if(rownum.equals("nominal")) {
                            colVals.add(dataSnapshot.child(rownum).child(i.toString()).getValue().toString());
                        }
                    }
                    if(!rownum.equals("updatedAt")) {
                        if(!rownum.equals("nominal")) {
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
                    if(rowNames.get(i).equals("nominal") | rowNames.get(i).equals("updatedAt")) {
                        continue;
                    }
                    provider_id.add(R.mipmap.ic_launcher);
                    provider_title.add(rowNames.get(i));
                }
                CellListGenerator mRowHeaderLists = new CellListGenerator(prices.get(0).size(),(prices.size()),rowNames);
                mRowHeaderLists.RowDataGenerator();
                CellListGenerator mColumnHeaderList = new CellListGenerator(prices.get(0).size(),(prices.size()),rowNames,colVals);
                mColumnHeaderList.ColumnDataGenerator();
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
                textView.setText("Harga Pulsa " + title);
                Log.v("TAGGG",""+rowNames);
                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                appopen.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAGGG","ga nemu coy");

            }
        }); // end of value event listener


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