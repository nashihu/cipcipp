package com.cipcipp.main.ui.signupactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cipcipp.main.R;
import com.cipcipp.main.model.RealUser;
import com.cipcipp.main.ui.pulseactivity.PulseActivity;
import com.cipcipp.main.ui.reportform.Indonesia;
import com.cipcipp.main.utils.Util;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        mAuth = FirebaseAuth.getInstance();
        ArrayList<String> jenis_kelamins = new ArrayList<>();
        final String titlevar = getIntent().getStringExtra("title");
        jenis_kelamins.add("Jenis Kelamin");
        jenis_kelamins.add("Laki-laki");
        jenis_kelamins.add("Perempuan");
        Spinner kelamin_spinner = findViewById(R.id.sign_up_kelamin);
        String[] jenis_kelamin = {"Jenis Kelamin", "Laki-laki", "Perempuan"};
        ArrayAdapter kelamin_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jenis_kelamin);
        kelamin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kelamin_spinner.setAdapter(kelamin_adapter);
        kelamin_spinner.setSelection(0);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setSpinner(R.id.sign_up_kelamin, jenis_kelamins);

        ArrayList<String> tahun_lahir_arr = new ArrayList<>();
        tahun_lahir_arr.add("Tahun Lahir");
        for (int i = 2012; i > 1944; i--) {
            tahun_lahir_arr.add(String.valueOf(i));
        }
        setSpinner(R.id.sign_up_TTL, tahun_lahir_arr);


        final ArrayList<String> provinces = new ArrayList<>();
        provinces.add("Provinsi");
        try {
            final JSONObject obj = new JSONObject(Indonesia.wawa);
            for (int i = 1; i < 34; i++) {
                provinces.add(obj.getJSONObject(String.valueOf(i)).names().get(0).toString());
            }
        } catch (JSONException error) {
            Log.e("error json", error + "");
        }
        setSpinner(R.id.sign_up_provinces, provinces);
        ((Spinner) findViewById(R.id.sign_up_provinces)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    ArrayList<String> kota = new ArrayList<>();
                    try {
                        final JSONObject obj = new JSONObject(Indonesia.wawa);
                        JSONArray jsonArray = obj.getJSONObject(String.valueOf(i)).getJSONArray(provinces.get(i));

                        for (int z = 0; z < jsonArray.length(); z++) {
                            try {
                                kota.add(jsonArray.getJSONObject(z).names().get(0) + "");
                            } catch (JSONException error) {
                                kota.add(jsonArray.getString(z));
                            }
                        }
                        Collections.sort(kota);
                        setSpinner(R.id.sign_up_kota, kota);
                    } catch (JSONException error) {
                        Log.e("error json", error + "");
                    }

                } else {
                    ArrayList<String> kosong = new ArrayList<>();
                    kosong.add("");
                    setSpinner(R.id.sign_up_kota, kosong);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //switch button locic
        {
            findViewById(R.id.sign_up_switch_button_sign).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (findViewById(R.id.sign_up_group).getVisibility() == View.VISIBLE) {
                        ((Button) findViewById(R.id.sign_up_action_button_sign)).setText(R.string.sign_in_field);
                        ((Button) findViewById(R.id.sign_up_switch_button_sign)).setText(R.string.sign_up_field);
                        findViewById(R.id.sign_up_group).setVisibility(View.GONE);
                        ((TextView) findViewById(R.id.sign_up_sudah_punya_akun_sebelumnya)).setText(R.string.belum_punya_akun);

                    } else {
                        ((Button) findViewById(R.id.sign_up_action_button_sign)).setText(R.string.sign_up_field);
                        ((Button) findViewById(R.id.sign_up_switch_button_sign)).setText(R.string.sign_in_field);
                        findViewById(R.id.sign_up_group).setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.sign_up_sudah_punya_akun_sebelumnya)).setText(R.string.sudah_punya_akun_sebelumnya);
                    }

                }
            });
            findViewById(R.id.sign_up_switch_button_sign).callOnClick();
        }

        //action button logic
        {
            findViewById(R.id.sign_up_action_button_sign).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (findViewById(R.id.sign_up_group).getVisibility() == View.VISIBLE) {
                        //sign up
                        final String username = ((TextView) findViewById(R.id.sign_up_username)).getText().toString();
                        final String jenis_kelamin = ((Spinner) findViewById(R.id.sign_up_kelamin)).getSelectedItem().toString();
                        final String tahun_lahir = ((Spinner) findViewById(R.id.sign_up_TTL)).getSelectedItem().toString();
                        final String provinsi = ((Spinner) findViewById(R.id.sign_up_provinces)).getSelectedItem().toString();
                        final String kota = ((Spinner) findViewById(R.id.sign_up_kota)).getSelectedItem().toString();
                        final String email = ((TextView) findViewById(R.id.sign_up_email)).getText().toString();
                        final String password = ((TextView) findViewById(R.id.sign_up_password)).getText().toString();
                        if (!username.equals("") && !jenis_kelamin.equals("") && !tahun_lahir.equals("") && !provinsi.equals("")
                                && !kota.equals("") && !email.equals("") && !password.equals("")) {
                            mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(SignUpActivity.this, new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    if (authResult != null) {
                                        DatabaseReference databaseReference;
                                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                        String userid;
                                        if (authResult.getUser() != null) {
                                            userid = authResult.getUser().getUid();
                                            databaseReference = firebaseDatabase.getReference().child("users").child(userid);
                                            databaseReference.child("username").setValue(username);
                                            databaseReference.child("jenis_kelamin").setValue(jenis_kelamin);
                                            databaseReference.child("tahun_lahir").setValue(tahun_lahir);
                                            databaseReference.child("provinsi").setValue(provinsi);
                                            databaseReference.child("kota").setValue(kota);
                                            databaseReference.child("email").setValue(email);
                                            doLogin(email, password, titlevar, username);
                                        } else {
                                            databaseReference = firebaseDatabase.getReference().child("users");
                                            DatabaseReference newPost = databaseReference.push();
                                            RealUser realUser = new RealUser(username, jenis_kelamin, tahun_lahir, provinsi, kota, email);
                                            newPost.setValue(realUser);

                                        }

                                    }


                                }
                            });
                        } else {
                            null_checker();
                        }

                    } else {
                        //login
                        String email = ((TextView) findViewById(R.id.sign_up_email)).getText().toString();
                        String password = ((TextView) findViewById(R.id.sign_up_password)).getText().toString();
                        if (!email.equals("") && !password.equals("")) {
                            doLogin(email, password, titlevar, "defaultUser");
                        } else {
                            if (email.equals("")) {
                                Toast.makeText(SignUpActivity.this, "email masih kosong..", Toast.LENGTH_SHORT).show();
                            } else if (password.equals("")) {
                                Toast.makeText(SignUpActivity.this, "password masih kosong..", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.eh_apaan_ini))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.google_sign_sign);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        findViewById(R.id.google_sign_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public static final String TAG = SignUpActivity.class.getSimpleName();

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "your name: " + acct.getDisplayName(), Toast.LENGTH_LONG).show();
            Log.w(TAG, "GOOGLE name: " + acct.getDisplayName());
            Log.w(TAG, "GOOGLE email: " + acct.getEmail());
            Log.w(TAG, "GOOGLE photo: " + acct.getPhotoUrl());
            Log.w(TAG, "GOOGLE id: " + acct.getId());

            mAuth.signOut();
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                final FirebaseUser user = mAuth.getCurrentUser();

                                if (user != null) {
                                    Log.w(TAG, "success with user: " + user.getDisplayName());
                                    Log.w(TAG, user.getUid());
                                    Log.w(TAG, user.getEmail());
                                    Log.w(TAG, user.getPhotoUrl() + "");
                                    final DatabaseReference databaseReference;
                                    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    databaseReference = firebaseDatabase.getReference().child("users");
                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Log.w(TAG, dataSnapshot.getValue().toString());
                                            ArrayList<String> emails = new ArrayList<>();
                                            for (DataSnapshot data234 : dataSnapshot.getChildren()) {
                                                emails.add(data234.child("email").getValue() + "");


                                            }
                                            if (!emails.contains(user.getEmail())) {

                                                DatabaseReference databaseReference = firebaseDatabase.getReference().child("users").child(user.getUid());
                                                databaseReference.child("username").setValue(user.getDisplayName());
                                                databaseReference.child("email").setValue(user.getEmail());
                                                Log.w(TAG, user.getPhotoUrl() + "");
                                                databaseReference.child("url_photo").setValue(user.getPhotoUrl() + "");

                                            }

                                        }


                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    Log.w(TAG, databaseReference.getKey());
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());

                            }

                            // ...
                        }
                    });
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
//            updateUI(null);
        }
    }

    public static final int RC_SIGN_IN = 101;
    GoogleSignInClient mGoogleSignInClient;


    private void setSpinner(int id, ArrayList<String> arrayList) {
        Spinner spinner = findViewById(id);
        ArrayAdapter spinner_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setSelection(0);
    }

    private void doLogin(String email, String password, final String title, final String username) {

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Error connecting server..", Toast.LENGTH_SHORT).show();
                } else {
                    String uid;
                    if (mAuth.getCurrentUser() != null) {
                        uid = mAuth.getCurrentUser().getUid();
                    } else {
                        uid = Util.UID_Guest;
                    }
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users")
                            .child(uid);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                String usern = ((HashMap) dataSnapshot.getValue()).get("username").toString();
                                Toast.makeText(SignUpActivity.this, "hello " + usern + " !", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }
                    });

                    startActivity(new Intent(SignUpActivity.this, PulseActivity.class)
                            .putExtra("title", title));
                    finish();

                }
            }
        });
    }

    private void null_checker() {
        final String username = ((TextView) findViewById(R.id.sign_up_username)).getText().toString();
        final String jenis_kelamin = ((Spinner) findViewById(R.id.sign_up_kelamin)).getSelectedItem().toString();
        final String tahun_lahir = ((Spinner) findViewById(R.id.sign_up_TTL)).getSelectedItem().toString();
        final String provinsi = ((Spinner) findViewById(R.id.sign_up_provinces)).getSelectedItem().toString();
        final String kota = ((Spinner) findViewById(R.id.sign_up_kota)).getSelectedItem().toString();
        final String email = ((TextView) findViewById(R.id.sign_up_email)).getText().toString();
        final String password = ((TextView) findViewById(R.id.sign_up_password)).getText().toString();
        if (username.equals("")) {
            Toast.makeText(this, "username masih kosong..", Toast.LENGTH_SHORT).show();
        } else if (jenis_kelamin.equals("Jenis Kelamin")) {
            Toast.makeText(this, "jenis kelamin belum dipilih..", Toast.LENGTH_SHORT).show();
        } else if (tahun_lahir.equals("Tahun Lahir")) {
            Toast.makeText(this, "tahun lahir belum dipilih..", Toast.LENGTH_SHORT).show();
        } else if (provinsi.equals("Provinsi")) {
            Toast.makeText(this, "provinsi belum dipilih..", Toast.LENGTH_SHORT).show();
        } else if (kota.equals("")) {
            Toast.makeText(this, "kota belum dipilih..", Toast.LENGTH_SHORT).show();
        } else if (email.equals("")) {
            Toast.makeText(this, "email masih kosong..", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(this, "password masih kosong..", Toast.LENGTH_SHORT).show();
        }

    }
}
