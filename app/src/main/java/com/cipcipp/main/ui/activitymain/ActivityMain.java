package com.cipcipp.main.ui.activitymain;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cipcipp.main.R;
import com.cipcipp.main.engine.DatabaseHandler;
import com.cipcipp.main.model.User;
import com.cipcipp.main.ui.aggactivity.AggActivity;
import com.cipcipp.main.ui.pulseactivity.PulseActivity;
import com.cipcipp.main.ui.reportform.reportForm;
import com.cipcipp.main.ui.showresult.ShowResult;
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

import java.util.List;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {
    static final String TAG = ActivityMain.class.getSimpleName();
    LinearLayout tsel;
    LinearLayout im3;
    LinearLayout xl;
    LinearLayout axis;
    LinearLayout tri;
    LinearLayout smartfren;
    Button cipvideo;
    NavigationView navigationView;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;
    TextView nav_useremail, nav_username;

    @Override
    protected void onPause() {
        super.onPause();
        model.setData();
//        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.setData();
//        model.getData().observe(this,result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);
        db.reCreateTable();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        tsel = findViewById(R.id.imgTelkomsel);
        tsel.setOnClickListener(this);
        im3 = findViewById(R.id.imgIndosat);
        im3.setOnClickListener(this);
        xl = findViewById(R.id.imgXL);
        xl.setOnClickListener(this);
        axis = findViewById(R.id.imgAXIS);
        axis.setOnClickListener(this);
        tri = findViewById(R.id.img3);
        tri.setOnClickListener(this);
        smartfren = findViewById(R.id.imgSmartfren);
        smartfren.setOnClickListener(this);
        cipvideo = findViewById(R.id.cipcipphow);
        cipvideo.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        nav_useremail = navigationView.getHeaderView(0).findViewById(R.id.nav_header_email);
        nav_username = navigationView.getHeaderView(0).findViewById(R.id.nav_header_username);
        TextView nav_email = nav_useremail;
        progressBar = findViewById(R.id.ProgressBar);
        if (firebaseUser == null) {
            signInAsGuest();
        } else {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users")
                    .child(firebaseUser.getUid());

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
//                        TextView nav_useremail = navigationView.getHeaderView(0).findViewById(R.id.nav_header_username);
//                        nav_useremail.setText(dataSnapshot.getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            nav_email.setText(firebaseUser.getEmail());
        }
        model = ViewModelProviders.of(this).get(ActivityMainViewModel.class);
        model.getData().observe(this,result);

    }

    private final Observer<User> result = new Observer<User>() {
        @Override
        public void onChanged(@Nullable User strings) {
            if (strings != null) {
                Log.e(TAG, "masuk pak eko!");
                nav_useremail.setText(strings.getUseremail());
                nav_username.setText(strings.getUsername());
            }
        }
    };
    ActivityMainViewModel model;

    @Override
    public void onClick(View view) {
        String title;
        Intent moveIntent;
        switch (view.getId()) {
            case R.id.imgTelkomsel:
                title = getString(R.string.carrier_tsel);
                moveIntent = new Intent(ActivityMain.this, AggActivity.class);
                moveIntent.putExtra("title", title);
                startActivity(moveIntent);
                break;
            case R.id.imgIndosat:
                title = getString(R.string.carrier_indosat);
                moveIntent = new Intent(ActivityMain.this, AggActivity.class);
                moveIntent.putExtra("title", title);
                startActivity(moveIntent);
                break;
            case R.id.imgXL:
                title = getString(R.string.carrier_xl);
                moveIntent = new Intent(ActivityMain.this, AggActivity.class);
                moveIntent.putExtra("title", title);
                startActivity(moveIntent);
                break;
            case R.id.img3:
                title = getString(R.string.carrier_tri);
                moveIntent = new Intent(ActivityMain.this, AggActivity.class);
                moveIntent.putExtra("title", title);
                startActivity(moveIntent);
                break;
            case R.id.imgAXIS:
                title = getString(R.string.carrier_axis);
                moveIntent = new Intent(ActivityMain.this, AggActivity.class);
                moveIntent.putExtra("title", title);
                startActivity(moveIntent);
                break;
            case R.id.imgSmartfren:
                title = getString(R.string.carrier_smartfren);
                moveIntent = new Intent(ActivityMain.this, AggActivity.class);
                moveIntent.putExtra("title", title);
                startActivity(moveIntent);
                break;
            case R.id.cipcipphow:
                String url = "http://www.github.com/nashihu/cipcipp";
                Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            default:
                Toast.makeText(ActivityMain.this, "Not Available Yet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (mAuth.getCurrentUser() != null) {
                if (mAuth.getCurrentUser().getEmail() != null) {
                    if (!mAuth.getCurrentUser().getEmail().equals("guest@cipcipp.com")) {
                        mAuth.signOut();
                        signInAsGuest();
                    } else {
                        Toast.makeText(this, "sign out success", Toast.LENGTH_SHORT).show();
                        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_username)).setText(getString(R.string.guest));
                        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_email)).setText(getString(R.string.guest_email));
                    }
                }
            }
            return true;
        } else if (id == R.id.action_refresh) {
            startActivity(new Intent(ActivityMain.this, ActivityMain.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_rekomendasi_pulsa) {
            attachMenuListener(item, AggActivity.class);
        } else if (id == R.id.nav_gallery) {
            attachMenuListener(item, PulseActivity.class);
        } else if (id == R.id.nav_slideshow) {
            attachMenuListener(item, reportForm.class);
        } else if (id == R.id.nav_tools) {
            attachMenuListener(item, ShowResult.class);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void attachMenuListener(MenuItem item, final Class<?> cls) {

        final View rekomendasi_pulsa = findViewById(item.getItemId());
        rekomendasi_pulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_rekomendasi_pulsa);
                PopupMenu popupMenu = new PopupMenu(ActivityMain.this, MenuItemCompat.getActionView(menuItem));
                popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        startActivity(new Intent(ActivityMain.this, cls)
                                .putExtra("title", menuItem.getTitle().toString()));

                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        rekomendasi_pulsa.callOnClick();
    }

    private void signInAsGuest() {
        progressBar.setVisibility(View.VISIBLE);
        TextView nav_username = navigationView.getHeaderView(0).findViewById(R.id.nav_header_username);
        TextView nav_email = navigationView.getHeaderView(0).findViewById(R.id.nav_header_email);
        nav_username.setText(getString(R.string.guest));
        nav_email.setText(getString(R.string.guest_email));
        mAuth.signInWithEmailAndPassword("guest@cipcipp.com", "uiuiui89")
                .addOnCompleteListener(ActivityMain.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(ActivityMain.this, "Failed connect to server", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }
}
