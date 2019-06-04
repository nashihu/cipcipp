package com.cipcipp.main.ui.pulseactivity;

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

import com.cipcipp.main.helper.CellListGenerator;
import com.cipcipp.main.helper.OpenApp;
import com.cipcipp.main.model.AggModel;
import com.cipcipp.main.model.CellModel;
import com.cipcipp.main.model.RowCells;
import com.cipcipp.main.R;
import com.cipcipp.main.engine.DatabaseHandler;
import com.cipcipp.main.engine.ProviderAdapter;
import com.cipcipp.main.ui.ActivityMain;
import com.cipcipp.main.ui.reportform.reportForm;
import com.cipcipp.main.ui.showresult.ShowResult;
import com.cipcipp.main.ui.signupactivity.SignUpActivity;
import com.evrencoskun.tableview.TableView;
import com.cipcipp.main.engine.MyTableViewAdapter;
import com.cipcipp.main.engine.MyTableViewListener;
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
    private TextView pulsaTitle;
    private TextView appopen;
    private TextView updatedAt;
    private ProviderAdapter adapterProv;
    private ArrayList<String> rowNumz = new ArrayList<>();
    private ArrayList<String> colVallz;
    private ArrayList<String> provider_title = new ArrayList<>();
    private ArrayList<String> provider_id = new ArrayList<>();
    private List<List<CellModel>> price_test = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText email;
    private EditText password;
    private final String TAG = PulseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pulse_activity);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null) {
            if(firebaseUser.getEmail()!=null) {
                if(firebaseUser.getEmail().equals("guest@cipcipp.com")) {
                    startActivity(new Intent(PulseActivity.this, SignUpActivity.class)
                            .putExtra("title",title));
                    finish();
                }
            }
        }
        pulsaTitle = findViewById(R.id.pulsa_title);
        appopen = findViewById(R.id.app_open_text);
        updatedAt = findViewById(R.id.last_update);
        report = findViewById(R.id.report);
        check_report = findViewById(R.id.report_check);
        title = getIntent().getStringExtra("title");
//        title = null;
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveIntent = new Intent(PulseActivity.this, reportForm.class);
                moveIntent.putExtra("title",title);
                startActivity(moveIntent);
            }
        });
        (findViewById(R.id.report_check)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveIntent = new Intent(PulseActivity.this, ShowResult.class);
                moveIntent.putExtra("title",title);
                startActivity(moveIntent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        signInListener();
        signOutListener();
        signUpListener();
        authStateListener();
        DatabaseHandler db = new DatabaseHandler(PulseActivity.this);
        db.reCreateTable();
        if(db.getAllContacts().size()!=0) {
            fetchData();
            Log.v(TAG,"ini lagi di sini");
        } else {
            firebaseUser = mAuth.getCurrentUser();
            if(firebaseUser!=null) {
                fetchData();
            } else {
                String noUser = "silakan sign in dulu";
                pulsaTitle.setText(noUser);
                pulsaTitle.setVisibility(View.VISIBLE);
                findViewById(R.id.reportGroup).setVisibility(View.GONE);
                AuthField(View.VISIBLE);
            }
        Log.e("TAG","ONCREATE CALLED");
        }
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                //Catch your exception
                // Without System.exit() this will not work.


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        OpenApp.openApp(PulseActivity.this,adapterProv.getItem(position));
    }


    private void renderData() {
        CellListGenerator mRowHeaderLists = new CellListGenerator(price_test.get(0).size(),(price_test.size()),rowNumz);
        for(int i = 0; i<price_test.size();i++) {
           for(int j = 0; j<price_test.get(i).size();j++) {
               Log.v("asdfasdf "+TAG,""+price_test.get(i).get(j).getData());
           }
        }

        mRowHeaderLists.RowDataGenerator();
        CellListGenerator mColumnHeaderList = new CellListGenerator(price_test.get(0).size(),(price_test.size()),rowNumz,colVallz);
        mColumnHeaderList.ColumnDataGenerator(true);
        TableView tableView = findViewById(R.id.content_container);
        MyTableViewAdapter mTableViewAdapter = new MyTableViewAdapter(PulseActivity.this);
        tableView.setAdapter(mTableViewAdapter);
        tableView.setTableViewListener(new MyTableViewListener(PulseActivity.this,false));
        mTableViewAdapter.setAllItems(mColumnHeaderList.GetColumnData(), mRowHeaderLists.GetRowData(), price_test);
        Log.v("asdfasdf"+TAG,"size: " + price_test.size() + "&" + price_test.size());
        RecyclerView providerRV = findViewById(R.id.rvProvider);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(PulseActivity.this, LinearLayoutManager.HORIZONTAL, false);
        providerRV.setLayoutManager(horizontalLayoutManager);
        adapterProv = new ProviderAdapter(PulseActivity.this,provider_title,provider_id);
        adapterProv.setClickListener(PulseActivity.this);
        providerRV.setAdapter(adapterProv);
        String titlee = "Harga Pulsa " + title;
        pulsaTitle.setText(titlee);
        pulsaTitle.setVisibility(View.VISIBLE);
        appopen.setVisibility(View.VISIBLE);
        updatedAt.setVisibility(View.VISIBLE);
        report.setVisibility(View.VISIBLE);
        check_report.setVisibility(View.VISIBLE);

    }

    private void setupData(DatabaseHandler db) {
        List<RowCells> rowCellsList = db.getAllContacts();
        HashMap<String,String> providerUrl = new HashMap<>();
        for(RowCells rowCell : rowCellsList) {
            String logger = rowCell.logger(rowCell);
            Log.v("price_DB",""+logger);
            ArrayList<CellModel> pricez = rowCell.bulkGetter(rowCell);
            Log.v("WADIDII",pricez.get(0).getData().toString());
            if(!rowCell.getC1().equals("updatedAt")) {
                if(!rowCell.getC1().equals("zeroField")) {
                    rowNumz.add(rowCell.getC1());
                    providerUrl.put(rowCell.getC1(),rowCell.getC2());
                    price_test.add(pricez);
                } else {
                    colVallz = rowCell.bulkStringGetter(rowCell);
                    Log.v("cols",""+colVallz);
                }
            } else {
                String updateText = updatedAt.getText().toString() +" " + rowCell.getC2();
                updatedAt.setText(updateText);
            }
        }
        Log.v("WADIDII","============ separator ======================");
        for(List<CellModel> pricez : price_test) {
            Log.v("WADIDII",pricez.get(0).getData().toString() + " " + pricez.get(1).getData().toString());
        }

        List<AggModel> cheapList = db.getAllCheapestValue();
        Log.v("cheapcheap",cheapList+" "+cheapList.size()+" "+cheapList.get(0));
        if(cheapList.size()==colVallz.size()){
            for(int i =0; i<colVallz.size(); i++) {
                cheapList.get(i).setNominal(colVallz.get(i));
            }
        }
//        for (AggModel agg : cheapList) {
//            Log.v(PulseActivity.class.getSimpleName(),agg.getNominal()+" "+agg.getPrice()+" "+agg.getProvider_url()+" "+agg.getProvider_id()+" "+R.mipmap.ic_launcher);
//        }
        for(int i = 0; i<rowNumz.size();i++) {
//            provider_id.add(R.mipmap.ic_launcher);
            provider_id.add(providerUrl.get(rowNumz.get(i)));
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
                String successLogin = "Loading...";
                pulsaTitle.setText(successLogin);
                pulsaTitle.setVisibility(View.VISIBLE);

                if(!emailString.equals("") && !pwd.equals("")) {
                    mAuth.signInWithEmailAndPassword(emailString,pwd)
                            .addOnCompleteListener(PulseActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(PulseActivity.this, "Failed sign in !",Toast.LENGTH_SHORT).show();
                                        String successLogin = "Failed sign in !";
                                        pulsaTitle.setText(successLogin);
                                        pulsaTitle.setVisibility(View.VISIBLE);
                                    } else {
                                        Toast.makeText(PulseActivity.this, "sign in Success!",Toast.LENGTH_SHORT).show();
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
                Intent backToHome = new Intent(PulseActivity.this, ActivityMain.class);
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
                                                    pulsaTitle.setText(successLogin);
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
                findViewById(R.id.sign_out).setVisibility(View.GONE);
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
