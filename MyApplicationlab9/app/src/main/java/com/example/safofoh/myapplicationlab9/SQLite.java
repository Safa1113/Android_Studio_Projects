package com.example.safofoh.myapplicationlab9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by GX620 on 27/04/17.
 */

public class SQLite extends SQLiteOpenHelper {

    //DB info
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "info.db";


    //Tables and its columns
    public static final String TABLE_NAME = "tblAMIGO";
    public static final String COLUMN_RECID = "recID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";



    public SQLite(Context context) {

        //Create a helper object to create, open, and/or manage a database
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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        Log.d("DB", "The table has been removed!");
        onCreate(db);
    }




    //Print out the database as a string
    public String databaseToString(){

        String dbData = " ";
        SQLiteDatabase db =  getWritableDatabase();


        String query = "SELECT * FROM " + TABLE_NAME;



        //Cursor point to a location in your result
        Cursor c = db.rawQuery(query, null);

        //Move to first row in your result
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            //if (c.getString(c.getColumnIndex(COLUMN_NAME)) != null) {
            dbData += c.getString(c.getColumnIndex(COLUMN_RECID));
            dbData += " | " + c.getString(c.getColumnIndex(COLUMN_NAME));
            dbData += " | " + c.getString(c.getColumnIndex(COLUMN_PHONE));
            dbData += "\n";
            c.moveToNext();
        }


        db.close();
        return dbData;
    }



    //Add new row record to database
    public void addRecord(String naStr, String phStr){

        SQLiteDatabase db = getWritableDatabase();


        //     db.execSQL("insert into "+ TABLE_NAME + "("+COLUMN_NAME + ","+ COLUMN_PHONE +") VALUES (?,?)", new String[] {naStr,phStr});
        //     db.close();



        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, naStr);
        values.put(COLUMN_PHONE, phStr);

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection


    }

}

