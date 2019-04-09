package com.cipcipp.main.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cipcipp.main.model.AggModel;
import com.cipcipp.main.model.CellModel;
import com.cipcipp.main.model.Report;
import com.cipcipp.main.model.RowCells;
import com.cipcipp.main.ui.aggactivity.AggCallback;
import com.cipcipp.main.R;
import com.cipcipp.main.engine.DatabaseHandler;
import com.cipcipp.main.ui.reportform.reportFormInt;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirebaseHelper {
    private String title;
    private Context context;
    public FirebaseHelper(Context context, String title) {
        this.context = context;
        FirebaseApp.initializeApp(context);
        this.title = title;
    }
    public void readReports(final reportFormInt reportFormInt) {
        FirebaseDatabase database;
        DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();
        database.getReference().child(title);
        databaseReference = database.getReference("report").child(title); //Telkomsel,etc
        Log.v("Result","before this");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Report> reports = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Report report = child.getValue(Report.class);
                    if(report!=null) {
                        Log.v("Result",child.getValue(Report.class).getPrice());
                    } else {
                        Log.v("Result","null coy...");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.v("Result","after this");

    }
    public void readData(final AggCallback aggCallback) {
        FirebaseDatabase database;
        DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().getReference().child(title);
        databaseReference = database.getReference("testing").child(title); //Telkomsel,etc
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DatabaseHandler db = new DatabaseHandler(context);
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
                aggCallback.onCallback(setupFirebaseData(db));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAGGG","ga nemu coy");
            }
        });
    }

    public void getProviderName(final reportFormInt reportFormInt) {
        DatabaseHandler db = new DatabaseHandler(context);
        List<RowCells> rowCellsList = db.getAllContacts();
        ArrayList<String> colVallz = new ArrayList<>();
        ArrayList<String> rowNums = new ArrayList<>();
        rowNums.add("pilih Provider");
        for(RowCells rowCell : rowCellsList) {
            String logger = rowCell.logger(rowCell);
            Log.v("price_DB",""+logger);
            if(rowCell.getC1().equals("zeroField")) {
                colVallz = rowCell.bulkStringGetter(rowCell);
                Log.v("cols",""+colVallz);
            } else if(!rowCell.getC1().equals("updatedAt")) {
                rowNums.add(rowCell.getC1());
            }
        }
        colVallz.add("pilih Nominal");
        for(int i = 0;i<colVallz.size();i++) {
            if(i!=(colVallz.size()-1)){
                Collections.swap(colVallz,i,(colVallz.size()-1));
            }

        }
        reportFormInt.onCallBack(rowNums,colVallz);
    }

    private List<AggModel> setupFirebaseData(DatabaseHandler db) {
        List<RowCells> rowCellsList = db.getAllContacts();
        ArrayList<String> colVallz = new ArrayList<>();
        for(RowCells rowCell : rowCellsList) {
            String logger = rowCell.logger(rowCell);
            Log.v("price_DB",""+logger);
            if(rowCell.getC1().equals("zeroField")) {
                    colVallz = rowCell.bulkStringGetter(rowCell);
                    Log.v("cols",""+colVallz);
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
        return cheapList;
    }
}

