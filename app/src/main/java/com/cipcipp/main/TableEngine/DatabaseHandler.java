package com.cipcipp.main.TableEngine;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.cipcipp.main.Model.RowCells;
import com.cipcipp.main.Utils.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }

    public void addContact(RowCells rowCells) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Util.KEY_C1, rowCells.getC1());
        value.put(Util.KEY_C2, rowCells.getC2());
        value.put(Util.KEY_C2, rowCells.getC2());
        value.put(Util.KEY_C3, rowCells.getC3());
        value.put(Util.KEY_C4, rowCells.getC4());
        value.put(Util.KEY_C5, rowCells.getC5());
        value.put(Util.KEY_C6, rowCells.getC6());
        value.put(Util.KEY_C7, rowCells.getC7());
        value.put(Util.KEY_C8, rowCells.getC8());
        value.put(Util.KEY_C9, rowCells.getC9());
        value.put(Util.KEY_C10, rowCells.getC10());
        value.put(Util.KEY_C11, rowCells.getC11());
        value.put(Util.KEY_C12, rowCells.getC12());
        value.put(Util.KEY_C13, rowCells.getC13());
        value.put(Util.KEY_C14, rowCells.getC14());
        value.put(Util.KEY_C15, rowCells.getC15());
        value.put(Util.KEY_C16, rowCells.getC16());
        value.put(Util.KEY_C17, rowCells.getC17());
        value.put(Util.KEY_C18, rowCells.getC18());
        value.put(Util.KEY_C19, rowCells.getC19());
        value.put(Util.KEY_C20, rowCells.getC20());
        db.insert(Util.TABLE_NAME,null,value);
        db.close();
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
        return rowCellsList;
    }

    public void reCreateTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }
}
