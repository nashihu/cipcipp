package com.cipcipp.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cipcipp.main.Helper.FirebaseDatabase;
import com.cipcipp.main.Model.AggModel;
import com.cipcipp.main.TableEngine.AggAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AggActivity extends AppCompatActivity implements AggAdapter.ItemClickListener {
    private ArrayList<String> provider_name = new ArrayList<>();
    private ArrayList<String> provider_nominal = new ArrayList<>();
    private ArrayList<String> provider_price = new ArrayList<>();
    private ArrayList<Integer> provider_img_id = new ArrayList<>();
    private AggAdapter adapterProv;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private EditText email;
    private EditText password;
    private TextView pulsaTitle;
    private String titleparam;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agg_activity);
        mAuth = FirebaseAuth.getInstance();
        authStateListener();
        firebaseUser = mAuth.getCurrentUser();
        final String title = getIntent().getStringExtra("title");
        titleparam = getIntent().getStringExtra("title");
        TextView agg_title = findViewById(R.id.agg_title);
        String title_ = "Loading..";
        agg_title.setText(title_);
        LinearLayout item = findViewById(R.id.aggGroup);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        item.addView(layoutInflater.inflate(R.layout.pulse_activity, item,false),1);
        signInListener(item);
        signOutListener();
        signUpListener();
        pulsaTitle = findViewById(R.id.pulsa_title);
        if(firebaseUser!=null) {
            item.removeView(findViewById(R.id.pulse_activity));
            fetchData(title);

        } else {
            String noUser = "silakan sign in dulu";
            ((TextView) (findViewById(R.id.agg_title))).setText(noUser);
            ((findViewById(R.id.pulsa_title))).setVisibility(View.GONE);
            AuthField(View.VISIBLE);

        }
        moveActivity(title);
    }

    private void fetchData(final String title) {
        FirebaseDatabase helper = new FirebaseDatabase(AggActivity.this,title);
        helper.readData(new MyCallback() {
            @Override
            public void onCallback(List<AggModel> aggModels) {
                attachData(title,aggModels);
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
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    private void destroyData() {
        provider_img_id = new ArrayList<>();
        provider_name = new ArrayList<>();
        provider_nominal = new ArrayList<>();
        provider_price = new ArrayList<>();
    }

    private void attachData(final String title,List<AggModel> aggModels) {
        String title_ = "Harga Termurah " + title ;
        ((TextView) findViewById(R.id.agg_title)).setText(title_);
        destroyData();
        for(AggModel agg : aggModels) {
            provider_img_id.add(agg.getProvider_id());
            provider_name.add("Rp."+agg.getPrice()+", by: ");
            provider_nominal.add("Nominal " + agg.getNominal());
            provider_price.add("");
            LinearLayoutManager verticalLayout
                    = new LinearLayoutManager(AggActivity.this, LinearLayoutManager.VERTICAL, false);
            RecyclerView aggView = findViewById(R.id.agg_recycler_view);
            aggView.setLayoutManager(verticalLayout);
            adapterProv = new AggAdapter(AggActivity.this,provider_nominal,provider_price,provider_name,provider_img_id);
            adapterProv.setClickListener(AggActivity.this);
            aggView.setAdapter(adapterProv);
        }
    }

    private void moveActivity(final String title) {
        (findViewById(R.id.to_pulse_activity)).setOnClickListener(new View.OnClickListener() {
            Intent moveIntent;
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.to_pulse_activity) {
                    moveIntent = new Intent(AggActivity.this, PulseActivity.class);
                    moveIntent.putExtra("title",title);
                    startActivity(moveIntent);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(AggActivity.this, "you click Bukalapak", Toast.LENGTH_SHORT).show();
    }

    private void AuthField(int state) {
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
                    Log.d("Firebase Auth","user signed in");
                } else {
                    //user is signed out
                    Log.d("Firebase Auth","user signed out");
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
                if(!emailString.equals("") && !pwd.equals("")) {
                    mAuth.signInWithEmailAndPassword(emailString,pwd)
                            .addOnCompleteListener(AggActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(AggActivity.this, "Failed sign in !",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AggActivity.this, "sign in Success!",Toast.LENGTH_SHORT).show();
                                        String successLogin = "Loading...";
                                        pulsaTitle.setText(successLogin);
                                        fetchData(titleparam);
                                        findViewById(R.id.contentGroup).setVisibility(View.VISIBLE);
                                        AuthField(View.GONE);
                                        item.removeView(findViewById(R.id.pulse_activity));
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
                Intent backToHome = new Intent(AggActivity.this,ActivityMain.class);
                startActivity(backToHome);
                Toast.makeText(AggActivity.this, "sign out success", Toast.LENGTH_SHORT).show();
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
                    mAuth.createUserWithEmailAndPassword(emailString,pwd).addOnCompleteListener(AggActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String emailString = email.getText().toString();
                            String pwd = password.getText().toString();
                            if(!emailString.equals("") && !pwd.equals("")) {
                                mAuth.signInWithEmailAndPassword(emailString,pwd)
                                        .addOnCompleteListener(AggActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (!task.isSuccessful()) {
                                                    Toast.makeText(AggActivity.this, "Failed sign in..",Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(AggActivity.this, "sign up Success!",Toast.LENGTH_SHORT).show();
                                                    String successLogin = "Loading...";
                                                    pulsaTitle.setText(successLogin);
                                                    fetchData(titleparam);
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
}

