package com.cipcipp.main.TableEngine;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.cipcipp.main.Model.AggModel;
import com.cipcipp.main.Model.RowCells;
import com.cipcipp.main.R;
import com.cipcipp.main.Utils.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }

    public void addRowCells(RowCells rowCells,String rowName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Util.KEY_C1,rowName);
        value.put(Util.KEY_C2, rowCells.getC1());
        value.put(Util.KEY_C3, rowCells.getC2());
        value.put(Util.KEY_C4, rowCells.getC3());
        value.put(Util.KEY_C5, rowCells.getC4());
        value.put(Util.KEY_C6, rowCells.getC5());
        value.put(Util.KEY_C7, rowCells.getC6());
        value.put(Util.KEY_C8, rowCells.getC7());
        value.put(Util.KEY_C9, rowCells.getC8());
        value.put(Util.KEY_C10, rowCells.getC9());
        value.put(Util.KEY_C11, rowCells.getC10());
        value.put(Util.KEY_C12, rowCells.getC11());
        value.put(Util.KEY_C13, rowCells.getC12());
        value.put(Util.KEY_C14, rowCells.getC13());
        value.put(Util.KEY_C15, rowCells.getC14());
        value.put(Util.KEY_C16, rowCells.getC15());
        value.put(Util.KEY_C17, rowCells.getC16());
        value.put(Util.KEY_C18, rowCells.getC17());
        value.put(Util.KEY_C19, rowCells.getC18());
        value.put(Util.KEY_C20, rowCells.getC19());
        db.insert(Util.TABLE_NAME,null,value);
        db.close();
    }

    public void addColVal(RowCells rowCells) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        String rowName = "zeroField";
        value.put(Util.KEY_C1,rowName);
        value.put(Util.KEY_C2, rowCells.getC1());
        value.put(Util.KEY_C3, rowCells.getC2());
        value.put(Util.KEY_C4, rowCells.getC3());
        value.put(Util.KEY_C5, rowCells.getC4());
        value.put(Util.KEY_C6, rowCells.getC5());
        value.put(Util.KEY_C7, rowCells.getC6());
        value.put(Util.KEY_C8, rowCells.getC7());
        value.put(Util.KEY_C9, rowCells.getC8());
        value.put(Util.KEY_C10, rowCells.getC9());
        value.put(Util.KEY_C11, rowCells.getC10());
        value.put(Util.KEY_C12, rowCells.getC11());
        value.put(Util.KEY_C13, rowCells.getC12());
        value.put(Util.KEY_C14, rowCells.getC13());
        value.put(Util.KEY_C15, rowCells.getC14());
        value.put(Util.KEY_C16, rowCells.getC15());
        value.put(Util.KEY_C17, rowCells.getC16());
        value.put(Util.KEY_C18, rowCells.getC17());
        value.put(Util.KEY_C19, rowCells.getC18());
        value.put(Util.KEY_C20, rowCells.getC19());
        db.insert(Util.TABLE_NAME,null,value);
        db.close();
    }
    public List<AggModel> getAllCheapestValue() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<AggModel> cheapList = new ArrayList<>();

        for(String query: queries) {
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            try{
                cheapList.add(new AggModel(cursor.getString(1),cursor.getString(0), R.mipmap.ic_launcher));
                cursor.close();
            } catch (CursorIndexOutOfBoundsException e) {
                cursor.close();
            } catch (NumberFormatException e) {
                cursor.close();
            }
        }

        Cursor cursor = db.rawQuery("SELECT * FROM PulsaTsel WHERE c1 = 'zeroField'",null);
        ArrayList<String> colVallz = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                for(int i = 2;i<21;i++) {
                    colVallz.add(cursor.getString(i));
                }
            } while(cursor.moveToNext());
        }
        for(int i =0; i<cheapList.size();i++) {
            cheapList.get(i).setNominal(colVallz.get(i));
        }
        cursor.close();
        return  cheapList;
    }
    public List<RowCells> getAllContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<RowCells> rowCellsList = new ArrayList<>();
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll,null);
        if(cursor.moveToFirst()) {
            do{
                RowCells rowCells = new RowCells();
                rowCells.setId(Integer.parseInt(cursor.getString(0)));
                rowCells.setC1(cursor.getString(1));
                rowCells.setC2(cursor.getString(2));
                rowCells.setC3(cursor.getString(3));
                rowCells.setC4(cursor.getString(4));
                rowCells.setC5(cursor.getString(5));
                rowCells.setC6(cursor.getString(6));
                rowCells.setC7(cursor.getString(7));
                rowCells.setC8(cursor.getString(8));
                rowCells.setC9(cursor.getString(9));
                rowCells.setC10(cursor.getString(10));
                rowCells.setC11(cursor.getString(11));
                rowCells.setC12(cursor.getString(12));
                rowCells.setC13(cursor.getString(13));
                rowCells.setC14(cursor.getString(14));
                rowCells.setC15(cursor.getString(15));
                rowCells.setC16(cursor.getString(16));
                rowCells.setC17(cursor.getString(17));
                rowCells.setC18(cursor.getString(18));
                rowCells.setC19(cursor.getString(19));
                rowCells.setC20(cursor.getString(20));
                rowCellsList.add(rowCells);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return rowCellsList;
    }

    public void reCreateTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        db.execSQL(CREATE_CONTACT_TABLE);

    }

    private
    String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " ( "
            + Util.KEY_ID + " INTEGER PRIMARY KEY, "
            + Util.KEY_C1 + " TEXT, "
            + Util.KEY_C2 + " TEXT, "
            + Util.KEY_C3 + " TEXT, "
            + Util.KEY_C4 + " TEXT, "
            + Util.KEY_C5 + " TEXT, "
            + Util.KEY_C6 + " TEXT, "
            + Util.KEY_C7 + " TEXT, "
            + Util.KEY_C8 + " TEXT, "
            + Util.KEY_C9 + " TEXT, "
            + Util.KEY_C10 + " TEXT, "
            + Util.KEY_C11 + " TEXT, "
            + Util.KEY_C12 + " TEXT, "
            + Util.KEY_C13 + " TEXT, "
            + Util.KEY_C14 + " TEXT, "
            + Util.KEY_C15 + " TEXT, "
            + Util.KEY_C16 + " TEXT, "
            + Util.KEY_C17 + " TEXT, "
            + Util.KEY_C18 + " TEXT, "
            + Util.KEY_C19 + " TEXT, "
            + Util.KEY_C20 + " TEXT " + " )";

    private String[] queries = {
            "SELECT * FROM (SELECT c1, replace(c2,'-','999999999') FROM PulsaTsel WHERE c2 != 0 and c2 not like '%000' ORDER BY c2 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c3,'-','999999999') FROM PulsaTsel WHERE c3 != 0 and c3 not like '%000' ORDER BY c3 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c4,'-','999999999') FROM PulsaTsel WHERE c4 != 0 and c4 not like '%000' ORDER BY c4 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c5,'-','999999999') FROM PulsaTsel WHERE c5 != 0 and c5 not like '%000' ORDER BY c5 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c6,'-','999999999') FROM PulsaTsel WHERE c6 != 0 and c6 not like '%000' ORDER BY c6 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c7,'-','999999999') FROM PulsaTsel WHERE c7 != 0 and c7 not like '%000' ORDER BY c7 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c8,'-','999999999') FROM PulsaTsel WHERE c8 != 0 and c8 not like '%000' ORDER BY c8 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c9,'-','999999999') FROM PulsaTsel WHERE c9 != 0 and c9 not like '%000' ORDER BY c9 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c10,'-','999999999') FROM PulsaTsel WHERE c10 != 0 and c10 not like '%000' ORDER BY c10 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c11,'-','999999999') FROM PulsaTsel WHERE c11 != 0 and c11 not like '%000' ORDER BY c11 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c12,'-','999999999') FROM PulsaTsel WHERE c12 != 0 and c12 not like '%000' ORDER BY c12 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c13,'-','999999999') FROM PulsaTsel WHERE c13 != 0 and c13 not like '%000' ORDER BY c13 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c14,'-','999999999') FROM PulsaTsel WHERE c14 != 0 and c14 not like '%000' ORDER BY c14 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c15,'-','999999999') FROM PulsaTsel WHERE c15 != 0 and c15 not like '%000' ORDER BY c15 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c16,'-','999999999') FROM PulsaTsel WHERE c16 != 0 and c16 not like '%000' ORDER BY c16 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c17,'-','999999999') FROM PulsaTsel WHERE c17 != 0 and c17 not like '%000' ORDER BY c17 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c18,'-','999999999') FROM PulsaTsel WHERE c18 != 0 and c18 not like '%000' ORDER BY c18 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c19,'-','999999999') FROM PulsaTsel WHERE c19 != 0 and c19 not like '%000' ORDER BY c19 ) as waw ORDER BY 2 LIMIT 1",
            "SELECT * FROM (SELECT c1, replace(c20,'-','999999999') FROM PulsaTsel WHERE c20 != 0 and c20 not like '%000' ORDER BY c20 ) as waw ORDER BY 2 LIMIT 1",


    };
}