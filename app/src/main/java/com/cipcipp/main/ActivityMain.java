package com.cipcipp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.cipcipp.main.Helper.PulsaParams;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener{
    LinearLayout tsel;
    LinearLayout im3;
    LinearLayout xl;
    LinearLayout axis;
    LinearLayout tri;
    LinearLayout smartfren;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
    private String title;
    private Intent moveIntent;
    private int params;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgTelkomsel:
                title = "Telkomsel";
                moveIntent = new Intent(ActivityMain.this, PulseActivity.class);
                params = PulsaParams.Telkomsel;
                moveIntent.putExtra("title",title);
                moveIntent.putExtra("params",params);
                startActivity(moveIntent);
                break;
            case R.id.imgIndosat:
                title = "Indosat";
                moveIntent = new Intent(ActivityMain.this, PulseActivity.class);
                params = PulsaParams.Indosat;
                moveIntent.putExtra("title",title);
                moveIntent.putExtra("params",params);
                startActivity(moveIntent);
                break;
            case R.id.imgXL:
                title = "XL";
                moveIntent = new Intent(ActivityMain.this, PulseActivity.class);
                params = PulsaParams.Indosat;
                moveIntent.putExtra("title",title);
                moveIntent.putExtra("params",params);
                startActivity(moveIntent);
                break;
            default:
                Toast.makeText(ActivityMain.this,"yg lainnya jg belum ada nih",Toast.LENGTH_SHORT).show();
        }
    }
}
