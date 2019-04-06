package com.cipcipp.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cipcipp.main.TableEngine.DatabaseHandler;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener{
    LinearLayout tsel;
    LinearLayout im3;
    LinearLayout xl;
    LinearLayout axis;
    LinearLayout tri;
    LinearLayout smartfren;
    Button cipvideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler db = new DatabaseHandler(this);
        db.reCreateTable();
        setContentView(R.layout.main_activity);
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
    }
    @Override
    public void onClick(View view) {
        String title;
        Intent moveIntent;
        switch (view.getId()) {
            case R.id.imgTelkomsel:
                title = "Telkomsel";
                moveIntent = new Intent(ActivityMain.this, AggActivity.class);
                moveIntent.putExtra("title",title);
                startActivity(moveIntent);
                break;
            case R.id.imgIndosat:
                title = "Indosat";
                moveIntent = new Intent(ActivityMain.this, AggActivity.class);
                moveIntent.putExtra("title",title);
                startActivity(moveIntent);
                break;
            case R.id.imgXL:
                title = "XL";
                moveIntent = new Intent(ActivityMain.this, AggActivity.class);
                moveIntent.putExtra("title",title);
                startActivity(moveIntent);
                break;
            case R.id.cipcipphow:
                String url = "http://www.youtube.com/nashihu";
                Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            default:
                Toast.makeText(ActivityMain.this,"Not Available Yet",Toast.LENGTH_SHORT).show();
        }
    }
}
