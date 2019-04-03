package com.cipcipp.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cipcipp.main.Helper.CellListGenerator;
import com.cipcipp.main.Helper.OpenApp;
import com.cipcipp.main.Model.AggModel;
import com.cipcipp.main.Model.CellModel;
import com.cipcipp.main.Model.RowCells;
import com.cipcipp.main.TableEngine.DatabaseHandler;
import com.cipcipp.main.TableEngine.ProviderAdapter;
import com.evrencoskun.tableview.TableView;
import com.cipcipp.main.TableEngine.MyTableViewAdapter;
import com.cipcipp.main.TableEngine.MyTableViewListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PulseActivity extends AppCompatActivity implements ProviderAdapter.ItemClickListener {
    private Button report;
    private Button check_report;
    private String title;
    private TextView textView;
    private TextView appopen;
    private TextView updatedAt;
    private ProviderAdapter adapterProv;
    private ArrayList<String> rowNumz = new ArrayList<>();
    private ArrayList<String> colVallz;
    private ArrayList<String> provider_title = new ArrayList<>();
    private ArrayList<Integer> provider_id = new ArrayList<>();
    private List<List<CellModel>> price_test = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pulse_activity);
        textView = findViewById(R.id.pulsa_title);
        appopen = findViewById(R.id.app_open_text);
        updatedAt = findViewById(R.id.last_update);
        report = findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveIntent = new Intent(PulseActivity.this, AggActivity.class);
                startActivity(moveIntent);
            }
        });
        check_report = findViewById(R.id.report_check);
        title = getIntent().getStringExtra("title");
        mAuth = FirebaseAuth.getInstance();
        signInListener();
        signOutListener();
        signUpListener();
        authStateListener();
        DatabaseHandler db = new DatabaseHandler(PulseActivity.this);
        db.reCreateTable();
        if(db.getAllContacts().size()!=0) {
            setupData(db);
            renderData();
        } else {
            FirebaseUser firebaseUser;
            firebaseUser = mAuth.getCurrentUser();
            if(firebaseUser!=null) {
                fetchData();
            } else {
                String noUser = "please sign in first";
                textView.setText(noUser);
                textView.setVisibility(View.VISIBLE);
                findViewById(R.id.reportGroup).setVisibility(View.GONE);
                AuthField(View.VISIBLE);
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DatabaseHandler db = new DatabaseHandler(PulseActivity.this);
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

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
        OpenApp.openApp(PulseActivity.this,packagename.get(adapterProv.getItem(position)));
    }

    private void renderData() {
        CellListGenerator mRowHeaderLists = new CellListGenerator(price_test.get(0).size(),(price_test.size()),rowNumz);
        mRowHeaderLists.RowDataGenerator();
        CellListGenerator mColumnHeaderList = new CellListGenerator(price_test.get(0).size(),(price_test.size()),rowNumz,colVallz);
        mColumnHeaderList.ColumnDataGenerator();
        TableView tableView = findViewById(R.id.content_container);
        MyTableViewAdapter mTableViewAdapter = new MyTableViewAdapter(PulseActivity.this);
        tableView.setAdapter(mTableViewAdapter);
        tableView.setTableViewListener(new MyTableViewListener());
        mTableViewAdapter.setAllItems(mColumnHeaderList.GetColumnData(), mRowHeaderLists.GetRowData(), price_test);

        RecyclerView providerRV = findViewById(R.id.rvProvider);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(PulseActivity.this, LinearLayoutManager.HORIZONTAL, false);
        providerRV.setLayoutManager(horizontalLayoutManager);
        adapterProv = new ProviderAdapter(PulseActivity.this,provider_title,provider_id);
        adapterProv.setClickListener(PulseActivity.this);
        providerRV.setAdapter(adapterProv);
        String titlee = "Harga Pulsa " + title;
        textView.setText(titlee);
        textView.setVisibility(View.VISIBLE);
        appopen.setVisibility(View.VISIBLE);
        updatedAt.setVisibility(View.VISIBLE);
        report.setVisibility(View.VISIBLE);
        check_report.setVisibility(View.VISIBLE);

    }

    private void setupData(DatabaseHandler db) {
        List<RowCells> rowCellsList = db.getAllContacts();
        for(RowCells rowCell : rowCellsList) {
            String logger = rowCell.logger(rowCell);
            Log.v("price_DB",""+logger);
            ArrayList<CellModel> pricez = rowCell.bulkGetter(rowCell);
            if(!rowCell.getC1().equals("updatedAt")) {
                if(!rowCell.getC1().equals("zeroField")) {
                    rowNumz.add(rowCell.getC1());
                    price_test.add(pricez);
                } else {
                    colVallz = rowCell.bulkStringGetter(rowCell);
                    Log.v("cols",""+colVallz);
                }
            } else {
                String updateText = updatedAt.getText().toString() + rowCell.getC2();
                updatedAt.setText(updateText);
            }
        }

        List<AggModel> cheapList = db.getAllCheapestValue();
        Log.v("cheapcheap",cheapList+" "+cheapList.size()+" "+cheapList.get(0));
        if(cheapList.size()==colVallz.size()){
            for(int i =0; i<colVallz.size(); i++) {
                cheapList.get(i).setNominal(colVallz.get(i));
            }
        } else {
            cheapList = new ArrayList<>();
        }
        for (AggModel agg : cheapList) {
            Log.v("aggmodel",agg.getNominal()+" "+agg.getPrice()+" "+agg.getProvider_name()+" "+agg.getProvider_id()+" "+R.mipmap.ic_launcher);
        }
        for(int i = 0; i<rowNumz.size();i++) {
            provider_id.add(R.mipmap.ic_launcher);
            provider_title.add(rowNumz.get(i));
        }
    }

    private void signInListener() {

        findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = findViewById(R.id.email);
                password = findViewById(R.id.pass);
                String emailString = email.getText().toString();
                String pwd = password.getText().toString();

                if(!emailString.equals("") && !pwd.equals("")) {
                    mAuth.signInWithEmailAndPassword(emailString,pwd)
                            .addOnCompleteListener(PulseActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(PulseActivity.this, "Failed sign in !",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(PulseActivity.this, "sign in Success!",Toast.LENGTH_SHORT).show();
                                        String successLogin = "Loading...";
                                        textView.setText(successLogin);
                                        fetchData();
                                        findViewById(R.id.contentGroup).setVisibility(View.VISIBLE);
                                        AuthField(View.GONE);
                                    }
                                }
                            });
                }
            }
        });
    }

    private void signOutListener() {
        findViewById(R.id.sign_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent backToHome = new Intent(PulseActivity.this,ActivityMain.class);
                startActivity(backToHome);
                Toast.makeText(PulseActivity.this, "sign out success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUpListener() {
        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = findViewById(R.id.email);
                password = findViewById(R.id.pass);
                String emailString = email.getText().toString();
                String pwd = password.getText().toString();
                if(!emailString.equals("") && !pwd.equals("")) {
                    mAuth.createUserWithEmailAndPassword(emailString,pwd).addOnCompleteListener(PulseActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String emailString = email.getText().toString();
                            String pwd = password.getText().toString();
                            if(!emailString.equals("") && !pwd.equals("")) {
                                mAuth.signInWithEmailAndPassword(emailString,pwd)
                                        .addOnCompleteListener(PulseActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (!task.isSuccessful()) {
                                                    Toast.makeText(PulseActivity.this, "Failed sign in..",Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(PulseActivity.this, "sign up Success!",Toast.LENGTH_SHORT).show();
                                                    String successLogin = "Loading...";
                                                    textView.setText(successLogin);
                                                    fetchData();
                                                    findViewById(R.id.contentGroup).setVisibility(View.VISIBLE);
                                                    AuthField(View.GONE);
                                                }
                                            }
                                        });
                            }
                        }
                    });
                }

            }
        });
    }

    private void fetchData() {
        FirebaseDatabase database;
        DatabaseReference databaseReference;
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().getReference().child(title);
        databaseReference = database.getReference("testing").child(title); //Telkomsel,etc
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DatabaseHandler db = new DatabaseHandler(PulseActivity.this);
                db.reCreateTable();
                RowCells rowCells = new RowCells();
                ArrayList<CellModel> colValz = new ArrayList<>();
                for (DataSnapshot child_i : dataSnapshot.getChildren()) {
                    String rownum = child_i.getKey();
                    ArrayList<CellModel> price_= new ArrayList<>();
                    for(int i =0;i<(child_i.getChildrenCount());i++) {
                        if(rownum!=null) {
                            price_.add(new CellModel(String.valueOf(i),dataSnapshot.child(rownum).child(String.valueOf(i)).getValue()));
                            if(rownum.equals("nominal")) {
                                colValz.add(new CellModel(String.valueOf(i),dataSnapshot.child(rownum).child(String.valueOf(i)).getValue()));
                            }
                        }
                    }
                    if(rownum!=null && !rownum.equals("updatedAt")) {
                        if(rownum.equals("nominal")){
                            rowCells.bulkSetter(rowCells,colValz);
                            db.addColVal(rowCells);
                        } else {
                            rowCells.bulkSetter(rowCells,price_);
                            db.addRowCells(rowCells,rownum);
                        }
                    } else {
                        if(rownum!=null) {
                            ArrayList<CellModel> rownumm = new ArrayList<>();
                            rownumm.add(new CellModel("0",dataSnapshot.child(rownum).getValue()));
                            rowCells.bulkSetter(rowCells,rownumm);
                            db.addRowCells(rowCells,"updatedAt");
                        }
                    }
                }
                setupData(db);
                renderData();
                findViewById(R.id.sign_out).setVisibility(View.VISIBLE);
                findViewById(R.id.reportGroup).setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAGGG","ga nemu coy");
            }
        });

    }

    private void authStateListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
                    Log.d("Firebase Auth","user signed in");
                } else {
                    //user is signed out
                    Log.d("Firebase Auth","user signed out");
                }
            }
        };
    }

    private void AuthField(int state) {
        findViewById(R.id.email).setVisibility(state);
        findViewById(R.id.pass).setVisibility(state);
        findViewById(R.id.sign_in).setVisibility(state);
        findViewById(R.id.sign_up).setVisibility(state);
    }
}