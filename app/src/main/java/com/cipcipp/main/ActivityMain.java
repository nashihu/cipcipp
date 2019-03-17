package com.cipcipp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cipcipp.main.Helper.CellListGenerator;
import com.cipcipp.main.Helper.ColumnHeaderGenerator;
import com.cipcipp.main.Model.ColumnHeaderModel;

import java.util.ArrayList;

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
    public Bundle bundle = new Bundle();
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgTelkomsel:
                String title = "Telkomsel";
//                ColumnHeaderGenerator mColumnHeaderList = new ColumnHeaderGenerator();
//                mColumnHeaderList.DataGenerator();
                CellListGenerator mColumnHeaderList = new CellListGenerator();
                mColumnHeaderList.ColumnDataGenerator();
                Intent moveIntent = new Intent(ActivityMain.this, PulseActivity.class);
                bundle.putParcelableArrayList( "columnList",mColumnHeaderList.GetColumnData());
                moveIntent.putExtra("title",title);
                moveIntent.putExtras(bundle);
                startActivity(moveIntent);

                break;
            case R.id.imgIndosat:
                Toast.makeText(ActivityMain.this,"indosat belum ada pak",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(ActivityMain.this,"yg lainnya jg belum ada nih",Toast.LENGTH_SHORT).show();
        }
    }
}
