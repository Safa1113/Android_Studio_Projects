package com.example.safofoh.myapplicationalarm;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class SQLite extends SQLiteOpenHelper {

    //DB info
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ALARM.db";


    //Tables and its columns
    public static final String TABLE_NAME = "alarm";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_HOUR = "hour";
    public static final String COLUMN_MINUTE = "minute";



    public SQLite(Context context) {

        //Create a helper object to create, open, and/or manage a database
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        // A SQL statement to create a table of three columns
        String sqlStmt = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_HOUR + " TEXT," +
                COLUMN_MINUTE + " TEXT " + ");";

        db.execSQL(sqlStmt);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST" + TABLE_NAME);
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
            dbData += c.getString(c.getColumnIndex(COLUMN_ID));
            dbData += " | " + c.getString(c.getColumnIndex(COLUMN_HOUR));
            dbData += " : " + c.getString(c.getColumnIndex(COLUMN_MINUTE));
            dbData += "\n";
            c.moveToNext();
        }


        db.close();
        return dbData;
    }



    //Add new row record to database
    public void addRecord(String hStr, String mStr){

        SQLiteDatabase db = getWritableDatabase();


        //     db.execSQL("insert into "+ TABLE_NAME + "("+COLUMN_NAME + ","+ COLUMN_PHONE +") VALUES (?,?)", new String[] {naStr,phStr});
        //     db.close();



        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUR, hStr);
        values.put(COLUMN_MINUTE, mStr);

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection


    }

    public void update(String idStr, String hStr, String mStr){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, idStr);
        values.put(COLUMN_HOUR, hStr);
        values.put(COLUMN_MINUTE, mStr);

        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{idStr});



    }

    public void delete(String idStr){

        SQLiteDatabase db = getWritableDatabase();


        //     db.execSQL("insert into "+ TABLE_NAME + "("+COLUMN_NAME + ","+ COLUMN_PHONE +") VALUES (?,?)", new String[] {naStr,phStr});
        //     db.close();


        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{idStr});

    }

}

