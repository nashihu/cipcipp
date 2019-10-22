package com.cipcipp.main.ui.aggactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cipcipp.main.R;
import com.cipcipp.main.engine.AggAdapter;
import com.cipcipp.main.helper.FirebaseHelper;
import com.cipcipp.main.helper.OpenApp;
import com.cipcipp.main.model.AggModel;
import com.cipcipp.main.ui.activitymain.ActivityMain;
import com.cipcipp.main.ui.contribute.ContriActivity;
import com.cipcipp.main.ui.pulseactivity.PulseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.cipcipp.main.utils.Util.packagename;
import static com.cipcipp.main.utils.Util.row_name;
import static com.cipcipp.main.utils.Util.row_package_name;

public class AggActivity extends AppCompatActivity implements AggAdapter.ItemClickListener, MultiSpinner.MultiSpinnerListener {
    private ArrayList<String> provider_price = new ArrayList<>();
    private ArrayList<String> provider_nominal = new ArrayList<>();
    private ArrayList<String> provider_null_field = new ArrayList<>();
    private ArrayList<String> provider_url = new ArrayList<>();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private String titleparam;
    private HashMap<String, String> iconPair = new HashMap<>();
    //TODO bikin profil trus nampilin dia udah lapor apa aja dan dia bisa hapus yang udah dia laporin
    //TODO ini ikon ikon nya ganti sama yang relevan
    //TODO bikin logic maksimum 7 laporan (?)
    //TODO admin bisa ngasih tau user kalo laporannya sudah diterima dan sudah diupdate (mending di-automate sih)


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agg_activity);
        findViewById(R.id.agg_spinner1).setVisibility(View.GONE);
        findViewById(R.id.agg_spinner2).setVisibility(View.GONE);
        findViewById(R.id.agg_contribute).setVisibility(View.GONE);
        findViewById(R.id.agg_more).setVisibility(View.GONE);
        findViewById(R.id.agg_last_update).setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        authStateListener();
        final String title = getIntent().getStringExtra("title");
        titleparam = title;
        String title_ = getString(R.string.harga_pulsa) + " " + title;
        if (getActionBar() != null) {
            getActionBar().setTitle(title_);

        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title_);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            } else {
                Toast.makeText(this, "getActionBar null", Toast.LENGTH_SHORT).show();
            }
        }

        LinearLayout item = findViewById(R.id.aggGroup);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (title == null || titleparam == null) {
            startActivity(new Intent(AggActivity.this, ActivityMain.class));
            Toast.makeText(this, "titleparam null", Toast.LENGTH_SHORT).show();

        }
        if (layoutInflater != null) {
            item.addView(layoutInflater.inflate(R.layout.pulse_activity, item, false), 1);
            signInListener(item);
            signOutListener();
            signUpListener(item);
            if (firebaseUser == null) {
                Toast.makeText(this, "sign in as guest in process..", Toast.LENGTH_SHORT).show();
                mAuth.signInWithEmailAndPassword("guest@cipcipp.com", "uiuiui89")
                        .addOnCompleteListener(AggActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(AggActivity.this, "No internet connection..", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AggActivity.this, "Sign in as guest success", Toast.LENGTH_SHORT).show();
                                    fetchData(title);
                                }
                            }
                        });
                item.removeView(findViewById(R.id.pulse_activity));
            } else {
                item.removeView(findViewById(R.id.pulse_activity));
                fetchData(title);
            }
        } else {
            createAlert("Error ditemukan :(",
                    "Harap hubungi cipcipp untuk mengatasi ini.\n" +
                            " code: AggJava inflation\n",
                    "Skip ke tabel data",
                    "Kembali ke menu utama",
                    "coba di sini dulu",
                    null
            );
        }
        moveActivity(title);
        FirebaseDatabase database;
        DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();
        if(title!=null) database.getReference().child(title);
        databaseReference = database.getReference("testing").child("updatedAt");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) ((TextView) findViewById(R.id.agg_last_update)).setText(getString(R.string.last_update,dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchData(final String title) {
        final FirebaseHelper helper = new FirebaseHelper(AggActivity.this, title);
        helper.getProviders(new AggProvCallback() {

            @Override
            public void onCallback(final ArrayList<String> strings) {

                helper.readAggs(strings, new AggCallback() {
                    @Override
                    public void onCallback(List<AggModel> aggModels) {
                        attachData(aggModels);
                        attachSpinner(title, aggModels);
                    }
                });

            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(AggActivity.this, "welcome " + user.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    private void destroyData() {
        provider_url = new ArrayList<>();
        provider_price = new ArrayList<>();
        provider_nominal = new ArrayList<>();
        provider_null_field = new ArrayList<>();
    }

    private void attachSpinner(final String title, final List<AggModel> aggModels) {

        findViewById(R.id.agg_spinner1).setVisibility(View.VISIBLE);
        findViewById(R.id.agg_spinner2).setVisibility(View.VISIBLE);

        final MultiSpinner spinner1 = findViewById(R.id.agg_spinner1);
        spinner1.setItems(Arrays.asList(row_name), "pilih aplikasi (" + row_name.length + ")", this);
        spinner1.setTitle(title);
        spinner1.setAggModels(aggModels);


        String[] filter = {"semua aplikasi", "aplikasi saya"};
        final SpinnerTrigger spinner2 = findViewById(R.id.agg_spinner2);
        ArrayAdapter adapterspinner2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filter);
        adapterspinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapterspinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ((CardView) findViewById(R.id.background_spinner1)).setCardBackgroundColor(getColor(R.color.colorAccent));
                ((CardView) findViewById(R.id.background_spinner2)).setCardBackgroundColor(getColor(R.color.colorPrimaryDark));
                findViewById(R.id.agg_recycler_view).setVisibility(View.GONE);
                findViewById(R.id.agg_progressbar).setVisibility(View.VISIBLE);
                if (position == 1) {
                    ArrayList<String> packstrings = new ArrayList<>();
                    PackageManager manager = getPackageManager();
                    for (int j = 0; j < row_name.length; j++) {
                        if (manager.getLaunchIntentForPackage(row_package_name[j]) != null) {
                            packstrings.add(row_name[j]);
                        }
                    }

                    final FirebaseHelper helper = new FirebaseHelper(AggActivity.this, title);
                    helper.readAggs(packstrings, new AggCallback() {
                        @Override
                        public void onCallback(List<AggModel> aggModelz) {
                            for (int i = 0; i < aggModelz.size(); i++) {
                                aggModelz.get(i).setNominal(aggModels.get(i).getNominal());
                            }
                            attachData(aggModelz);
                        }
                    });
                } else {
                    final FirebaseHelper helper = new FirebaseHelper(AggActivity.this, title);
                    ArrayList<String> strings = new ArrayList<>();
                    Collections.addAll(strings, row_name);
                    helper.readAggs(strings, new AggCallback() {
                        @Override
                        public void onCallback(List<AggModel> aggModelz) {
                            for (int i = 0; i < aggModelz.size(); i++) {
                                aggModelz.get(i).setNominal(aggModels.get(i).getNominal());
                            }
                            attachData(aggModelz);
                        }
                    });

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AggActivity.this, "nothing selected", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void attachData(final List<AggModel> old_agg) {
        final List<AggModel> aggModels = new ArrayList<>();
        for (AggModel agg : old_agg) {
            if (!agg.getPrice().equals(String.valueOf(999999999))) {
                aggModels.add(agg);
            }
        }


        destroyData();
        for (int i = 0; i < aggModels.size(); i++) {
            provider_url.add(String.valueOf(aggModels.get(i).getProvider_url()));
            provider_price.add("Rp." + aggModels.get(i).getPrice() + ", by: ");
            provider_nominal.add("Nominal " + aggModels.get(i).getNominal());
            provider_null_field.add("");
            iconPair.put(String.valueOf(i), aggModels.get(i).getProvider_id());

//            Log.v("asdfasdf AggActivity", aggModels.get(i).getNominal() + "\r\n"
//                    + aggModels.get(i).getPrice() + "\r\n"
//                    + aggModels.get(i).getProvider_id() + "\r\n"
//                    + aggModels.get(i).getProvider_url() + "\r\n");
        }
        LinearLayoutManager verticalLayout
                = new LinearLayoutManager(AggActivity.this, LinearLayoutManager.VERTICAL, false);
        RecyclerView aggView = findViewById(R.id.agg_recycler_view);
        aggView.setLayoutManager(verticalLayout);
        AggAdapter adapterProv = new AggAdapter(AggActivity.this, provider_nominal, provider_null_field, provider_price, provider_url);
        adapterProv.setClickListener(AggActivity.this);
        aggView.setAdapter(adapterProv);
        findViewById(R.id.agg_contribute).setVisibility(View.VISIBLE);
        findViewById(R.id.agg_more).setVisibility(View.VISIBLE);
        findViewById(R.id.agg_last_update).setVisibility(View.VISIBLE);
        findViewById(R.id.agg_progressbar).setVisibility(View.GONE);
        findViewById(R.id.agg_recycler_view).setVisibility(View.VISIBLE);

    }


    private void moveActivity(final String title) {
        (findViewById(R.id.to_pulse_activity)).setOnClickListener(new View.OnClickListener() {
            Intent moveIntent;

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.to_pulse_activity) {
                    moveIntent = new Intent(AggActivity.this, PulseActivity.class);
                    moveIntent.putExtra("title", title);
                    startActivity(moveIntent);
                }
            }
        });
        (findViewById(R.id.agg_contribute_button)).setOnClickListener(new View.OnClickListener() {
            Intent moveIntent;

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.agg_contribute_button) {
                    moveIntent = new Intent(AggActivity.this, ContriActivity.class);
                    moveIntent.putExtra("title", title);
                    startActivity(moveIntent);
                }
            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {

        createAlert(
                "Anda akan menuju aplikasi " + iconPair.get(String.valueOf(position)),
                "apabila data yg kami tunjukkan beda (" +
                        String.valueOf(provider_nominal.get(position)).replace("-", "") +
                        " Harga: " + provider_price.get(position).replace(", by:", "") +
                        "), silakan laporkan di menu lapokan beda data",
                "ok",
                "",
                "cancel",
                iconPair.get(String.valueOf(position))
        );
    }

    @Override
    public void onLongClick(View view, int position) {
        String[] row_name = {"Ovo", "Blibli", "Flip", "Dana", "BL", "Tokped", "Paytren", "Payfazz", "Lazada", "Shopee", "Gojek"};
        String[] row_package_name = {"ovo.id",
                "blibli.mobile.commerce", "id.flip", "id.dana", "com.bukalapak.android", "com.tokopedia.tkpd"
                , "id.co.paytren.user", "com.payfazz.android", "com.lazada.android", "com.shopee.id", "com.gojek.app"};
        HashMap<String, String> packagename = new HashMap<>();
        for (int i = 0; i < row_name.length; i++) {
            packagename.put(row_name[i], row_package_name[i]);
        }
        OpenApp.openApp(AggActivity.this, packagename.get(iconPair.get(String.valueOf(position))));
        Toast.makeText(this, "you long click " + position, Toast.LENGTH_SHORT).show();
    }

    private void AuthFieldGONE() {
        int state = View.GONE;
        findViewById(R.id.email).setVisibility(state);
        findViewById(R.id.pass).setVisibility(state);
        findViewById(R.id.sign_in).setVisibility(state);
        findViewById(R.id.sign_up).setVisibility(state);
    }

    private void authStateListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
                    Log.d("Firebase Auth", "user signed in");
                } else {
                    //user is signed out
                    Log.d("Firebase Auth", "user signed out");
                }
            }
        };
    }

    private void signInListener(final LinearLayout item) {

        item.findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = findViewById(R.id.email);
                password = findViewById(R.id.pass);
                String emailString = email.getText().toString();
                String pwd = password.getText().toString();
                if (!emailString.equals("") && !pwd.equals("")) {
                    mAuth.signInWithEmailAndPassword(emailString, pwd)
                            .addOnCompleteListener(AggActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(AggActivity.this, "Failed sign in !", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AggActivity.this, "sign in Success!", Toast.LENGTH_SHORT).show();

                                        fetchData(titleparam);
                                        findViewById(R.id.contentGroup).setVisibility(View.VISIBLE);
                                        AuthFieldGONE();
                                        item.removeView(findViewById(R.id.pulse_activity));
                                    }
                                }
                            });
                } else {
                    Toast.makeText(AggActivity.this, "email / pass tidak boleh null", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void signUpListener(final LinearLayout item) {
        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = findViewById(R.id.email);
                password = findViewById(R.id.pass);
                String emailString = email.getText().toString();
                String pwd = password.getText().toString();
                if (!emailString.equals("") && !pwd.equals("")) {
                    mAuth.createUserWithEmailAndPassword(emailString, pwd).addOnCompleteListener(AggActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String emailString = email.getText().toString();
                            String pwd = password.getText().toString();
                            if (!emailString.equals("") && !pwd.equals("")) {
                                mAuth.signInWithEmailAndPassword(emailString, pwd)
                                        .addOnCompleteListener(AggActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (!task.isSuccessful()) {
                                                    Toast.makeText(AggActivity.this, "Failed sign up..", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(AggActivity.this, "sign up Success!", Toast.LENGTH_SHORT).show();

                                                    fetchData(titleparam);
                                                    findViewById(R.id.contentGroup).setVisibility(View.VISIBLE);
                                                    AuthFieldGONE();
                                                    item.removeView(findViewById(R.id.pulse_activity));
                                                }
                                            }
                                        });
                            }
                        }
                    });
                } else {
                    Toast.makeText(AggActivity.this, "email / pass tidak boleh null", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void signOutListener() {
        findViewById(R.id.sign_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent backToHome = new Intent(AggActivity.this, ActivityMain.class);
                startActivity(backToHome);
                Toast.makeText(AggActivity.this, "sign out success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createAlert(String msg_title, String msg_body, String msg_pos, String msg_neg,
                             String msg_neut, final String packagename) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AggActivity.this);
        alertDialogBuilder.setTitle(msg_title);
        alertDialogBuilder.setMessage(msg_body);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(msg_pos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (packagename == null) {
                    startActivity(new Intent(AggActivity.this, ActivityMain.class));
                    finish();
                } else {
                    OpenApp.openApp(AggActivity.this, packagename);
                }
            }
        });
        alertDialogBuilder.setNegativeButton(msg_neg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(AggActivity.this, ActivityMain.class));
                finish();
            }
        });
        alertDialogBuilder.setNeutralButton(msg_neut, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onItemsSelected(boolean[] selected, final String title, final List<AggModel> aggModels) {
        ((CardView) findViewById(R.id.background_spinner1)).setCardBackgroundColor(getColor(R.color.colorPrimaryDark));
        ((CardView) findViewById(R.id.background_spinner2)).setCardBackgroundColor(getColor(R.color.colorAccent));
        findViewById(R.id.agg_recycler_view).setVisibility(View.GONE);
        findViewById(R.id.agg_progressbar).setVisibility(View.VISIBLE);
        ArrayList<String> selectedprovs = new ArrayList<>();
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                selectedprovs.add(row_name[i]);
            }
        }

        final FirebaseHelper helper = new FirebaseHelper(AggActivity.this, title);
        helper.readAggs(selectedprovs, new AggCallback() {
            @Override
            public void onCallback(List<AggModel> aggModelz) {
                for (int i = 0; i < aggModelz.size(); i++) {
                    aggModelz.get(i).setNominal(aggModels.get(i).getNominal());
                }
                attachData(aggModelz);

            }
        });
    }
}

