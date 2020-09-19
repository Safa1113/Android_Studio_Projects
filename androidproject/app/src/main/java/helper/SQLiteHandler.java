package helper;

/**
 * Created by GX620 on 02/04/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_UName = "username";
    private static final String KEY_FNAME = "realname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CREATED_AT = "created_at";
    //Replies table name
    private static final String TABLE_REPLIES = "replies";
    // Replies Table Columns names
    private static final String KEY_id = "id";
    private static final String KEY_writer = "writer";
    private static final String KEY_txt = "txt";
    private static final String KEY_likes = "likes";
    private static final String KEY_mentioned = "mentioned";





    SQLiteDatabase db;
    Cursor cursor;



    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_UName + " TEXT PRIMARY KEY," + KEY_FNAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);


        String CREATE_REPLIES_TABLE = "CREATE TABLE " + TABLE_REPLIES + "("
                + KEY_id + " TEXT PRIMARY KEY," + KEY_writer + " TEXT,"
                + KEY_mentioned + " TEXT," + KEY_txt + " TEXT,"
                + KEY_likes + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_REPLIES_TABLE);




        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String uname, String email, String fname, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UName, uname); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_FNAME, fname); // Realname
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addReplies(String id, String writer, String txt, String mentioned, String likes, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_id, id);
        values.put(KEY_writer, writer);
        values.put(KEY_txt, txt);
        values.put(KEY_mentioned, mentioned);
        values.put(KEY_likes, likes);
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long ID = db.insert(TABLE_REPLIES, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New reply inserted into sqlite: " + ID);
    }


    public String[] getReplyDetails ()
    {







        String[] rep = new String[7];
      //  cursor.moveToNext();

      //   db = this.getReadableDatabase();
        //  cursor = db.rawQuery(selectQuery, null);
     //    cursor = db.query(TABLE_REPLIES, new String[]{KEY_id,KEY_writer,KEY_mentioned,KEY_txt,KEY_likes,KEY_CREATED_AT}, null, null, null, null, null);


        //cursor.moveToNext();
            rep[1] = cursor.getString(cursor.getColumnIndex(KEY_writer));
            rep[2] = cursor.getString(cursor.getColumnIndex(KEY_mentioned));
            rep[3] = cursor.getString(cursor.getColumnIndex(KEY_txt));
            rep[4] = cursor.getString(cursor.getColumnIndex(KEY_likes));
            rep[5] = cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT));
            rep[6] = cursor.getString(cursor.getColumnIndex(KEY_id));



        return  rep;



    }

    public boolean  cursorMoveToFirst(){

        if (cursor.moveToFirst()) {
return true;
        }
        else

        return  false;
    }
    public boolean cursorNext (){
        if (cursor.moveToNext()) {
            return true;
        }
        else

            return  false;

}
    public boolean cursorPrev (){
        if (cursor.moveToPrevious()) {
            return true;
        }
        else

            return  false;

    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void deleteReplies() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_REPLIES, null, null);
        db.close();

        Log.d(TAG, "Deleted all replies info from sqlite");
    }

    public void setCursor (){
       // String selectQuery = "SELECT  * FROM " + TABLE_USER;
        db = this.getReadableDatabase();
      //  cursor = db.rawQuery(selectQuery, null);
      cursor = db.query(TABLE_REPLIES, new String[]{KEY_id,KEY_writer,KEY_mentioned,KEY_txt,KEY_likes,KEY_CREATED_AT}, null, null, null, null, null);
        //String[] rep = new String[7];
      //  cursor.moveToFirst();
       // cursor.moveToNext();


    }

}