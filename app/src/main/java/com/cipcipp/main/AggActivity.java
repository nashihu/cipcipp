package com.cipcipp.main;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cipcipp.main.Model.AggModel;
import com.cipcipp.main.TableEngine.AggAdapter;
import com.cipcipp.main.TableEngine.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class AggActivity extends AppCompatActivity implements AggAdapter.ItemClickListener {
    private ArrayList<String> provider_name = new ArrayList<>();
    private ArrayList<String> provider_nominal = new ArrayList<>();
    private ArrayList<String> provider_price = new ArrayList<>();
    private ArrayList<Integer> provider_img_id = new ArrayList<>();
    private AggAdapter adapterProv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agg_activity);
        LinearLayout item = findViewById(R.id.aggGroup);
        View child = getLayoutInflater().inflate(R.layout.agg_item, item,false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,to_px(80));
        params.setMargins(to_px(48),to_px(48),to_px(48),to_px(10));
        child.setLayoutParams(params);
        child.findViewById(R.id.agg_item_separator).setVisibility(View.GONE);
        item.addView(child);
        findViewById(R.id.aggGroup);
        DatabaseHandler db = new DatabaseHandler(AggActivity.this);
        List<AggModel> cheapList = db.getAllCheapestValue();
//        for(int i = 0; i < 10; i++) {
//            provider_img_id.add(R.mipmap.ic_launcher);
//            provider_name.add("name " + i);
//            provider_nominal.add((1000*i)+"");
//            provider_price.add((1100*i)+"");
//        }
        for(AggModel agg : cheapList) {
            provider_img_id.add(agg.getProvider_id());
            provider_name.add(agg.getProvider_name());
            provider_nominal.add(agg.getNominal());
            provider_price.add(agg.getPrice());
        }

        LinearLayoutManager verticalLayout
                = new LinearLayoutManager(AggActivity.this, LinearLayoutManager.VERTICAL, false);
        RecyclerView aggView = findViewById(R.id.agg_recycler_view);
        aggView.setLayoutManager(verticalLayout);
        adapterProv = new AggAdapter(this,provider_nominal,provider_price,provider_name,provider_img_id);
        adapterProv.setClickListener(AggActivity.this);
        aggView.setAdapter(adapterProv);
        item.removeView(aggView);
        item.addView(aggView);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(AggActivity.this, "you click" + adapterProv.getItem(position), Toast.LENGTH_SHORT).show();
    }

    private int to_px(int dp) {
        Resources r = getApplicationContext().getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics());
    }
}
