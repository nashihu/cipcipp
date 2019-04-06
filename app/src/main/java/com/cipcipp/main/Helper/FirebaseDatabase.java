package com.cipcipp.main.Helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cipcipp.main.Model.AggModel;
import com.cipcipp.main.Model.CellModel;
import com.cipcipp.main.Model.RowCells;
import com.cipcipp.main.MyCallback;
import com.cipcipp.main.R;
import com.cipcipp.main.TableEngine.DatabaseHandler;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabase {
    private List<AggModel> aggs;
    private String title;
    private Context context;
    public FirebaseDatabase(Context context, String title) {
        this.context = context;
        FirebaseApp.initializeApp(context);
        this.title = title;
    }

    public void summarizeData(final Context context, String title) {
        com.google.firebase.database.FirebaseDatabase database;
        DatabaseReference databaseReference;
        FirebaseApp.initializeApp(context);
        database = com.google.firebase.database.FirebaseDatabase.getInstance();
        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child(title);
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
                aggs =  setupFirebaseData(db);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAGGG","ga nemu coy");
            }
        });

    }

    public void readData(final MyCallback myCallback) {
        com.google.firebase.database.FirebaseDatabase database;
        DatabaseReference databaseReference;
        database = com.google.firebase.database.FirebaseDatabase.getInstance();
        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child(title);
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
                aggs =  setupFirebaseData(db);
                myCallback.onCallback(aggs);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAGGG","ga nemu coy");
            }
        });
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

    public List<AggModel> getAggs() {
        return aggs;
    }

}

