package com.studentassistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yash on 7/4/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Subjects.db";
    public static final String TABLE_NAME = "sublect_table";
    public static final String COL_1 = "sub_name";
    public static final String COL_2 = "sub_code";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(sub_name String ,sub_code String  )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertsubject(String subjectname,String subjectcode){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, subjectname);
        contentValues.put(COL_2, subjectcode);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getsubjects(){
        SQLiteDatabase db = this.getWritableDatabase();
        final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return c;
    }

}
