package com.example.safofoh.myapplicationlab10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Safofoh on 4/7/2018.
 */

public class MyDBHandler extends SQLiteOpenHelper {
    //DB info
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "info.db";
    //Tables and its columns
    public static final String TABLE_NAME = "tblAMIGO";
    public static final String COLUMN_RECID = "recID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// A SQL statement to create a table of three columns
        String sqlStmt = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_RECID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_NAME + " TEXT," +
                COLUMN_PHONE + " TEXT " + ");";
        db.execSQL(sqlStmt);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.d("DB", "The table has been removed!");
        onCreate(db);
    }
}