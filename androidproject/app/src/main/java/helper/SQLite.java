package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLite extends SQLiteOpenHelper {


    private final static int version = 1;

    private final static String name = "android-project";

    private final static String table_name = "firsttest";

    private final static String c1 = "firstcolumn";

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



    // profile table
    private static final String TABLE_PROFILE = "profile";
    // profile columns
    private static final String KEY_Real = "realname";
    private static final String KEY_Username = "username";
    private static final String KEY_About ="about";
    private static final String KEY_Updated_at ="updated_at";
    //private static final String KEY_alpha ;
  //  private static final String KEY_beta ;
  //  private static final String KEY_delta ;
    private static final String KEY_S_q = "s_q";
    private static final String KEY_S_a = "s_a";




    // App table
    private static final String TABLE_AppConfig = "appconfig";
    // App columns
    private static final String KEY_AppMood = "mood";
    private static final String KEY_AppUsername = "username";
    private static final String KEY_AppProfile ="profile";
    private static final String KEY_AppReply ="reply";
    private static final String KEY_AppRowIndext = "rowindex";
    private static final String KEY_AppTotalRow= "totalrow";



    public SQLite (Context context){
        super (context, name, null, version);

    }
    public void onCreate (SQLiteDatabase db){
      //  String sql = "Create table " + table_name + " ( " + c1 + " text primary key " + " );";
     //   db.execSQL(sql);


        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + " ( "
                + KEY_UName + " TEXT PRIMARY KEY, " + KEY_FNAME + " TEXT, "
                + KEY_EMAIL + " TEXT UNIQUE, "
                + KEY_CREATED_AT + " TEXT " + " ); ";
        db.execSQL(CREATE_LOGIN_TABLE);



        String CREATE_REPLIES_TABLE = "CREATE TABLE " + TABLE_REPLIES + " ( "
                + KEY_id + " TEXT PRIMARY KEY, " + KEY_writer + " TEXT, "
                + KEY_mentioned + " TEXT, " + KEY_txt + " TEXT, "
                + KEY_likes + " TEXT, "
                + KEY_CREATED_AT + " TEXT " + " ); ";
        db.execSQL(CREATE_REPLIES_TABLE);

        String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_PROFILE + " ( "
                + KEY_Username + " TEXT PRIMARY KEY, " + KEY_Real + " TEXT, "
                + KEY_About + " TEXT, " + KEY_S_q + " TEXT, "
                + KEY_S_a+ " TEXT, "
                + KEY_Updated_at+ " TEXT " + " ); ";
        db.execSQL(CREATE_PROFILE_TABLE);

        String CREATE_App_TABLE = "CREATE TABLE " + TABLE_AppConfig + " ( "
                + KEY_AppUsername + " TEXT PRIMARY KEY, " + KEY_AppMood + " TEXT, "
                + KEY_AppProfile + " TEXT, " + KEY_AppReply + " TEXT, "
                + KEY_AppRowIndext+ " TEXT, "
                + KEY_AppTotalRow+ " TEXT " + " ); ";
        db.execSQL(CREATE_App_TABLE);



    }
    public void onUpgrade (SQLiteDatabase db, int old, int newv){

      //  db.execSQL("DROP TABLE IF EXISTS " + table_name);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPLIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AppConfig);

        onCreate(db);

    }



    public void addUser(String uname, String email, String fname, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UName, uname); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_FNAME, fname); // Realname
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

    }

    public void addProfile(String username, String realname, String about, String seqQ, String seqA, String updated_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Username, username);
        values.put(KEY_Real, realname);
        values.put(KEY_About, about);
        values.put(KEY_Updated_at, updated_at);
        values.put(KEY_S_q, seqQ);
        values.put(KEY_S_a, seqA); // Created At

        // Inserting Row
        db.insert(TABLE_PROFILE, null, values);
        db.close(); // Closing database connection


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
        db.insert(TABLE_REPLIES, null, values);
        db.close(); // Closing database connection


    }

    public void addConfig(String username, String mood, String profile, String reply, String rowindex, String totalrow) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AppMood, mood);
        values.put(KEY_AppProfile, profile);
        values.put(KEY_AppReply, reply);
        values.put(KEY_AppRowIndext, rowindex);
        values.put(KEY_AppTotalRow, totalrow);
        values.put(KEY_AppUsername, username); // Created At

        // Inserting Row
        db.insert(TABLE_AppConfig, null, values);
        db.close(); // Closing database connection


    }

    public String[][] getReplyDetails () {

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_REPLIES, new String[]{KEY_id, KEY_writer, KEY_mentioned, KEY_txt, KEY_likes, KEY_CREATED_AT}, null, null, null, null, null);

        int num = cursor.getCount();

        String[][] rep = new String[num][7];

        int count = 0;
        rep[0][0] = String.format("%d",num);


            if (cursor.moveToFirst()) {
                do {


                    rep[count][1] = cursor.getString(cursor.getColumnIndex(KEY_writer));
                    rep[count][2] = cursor.getString(cursor.getColumnIndex(KEY_mentioned));
                    rep[count][3] = cursor.getString(cursor.getColumnIndex(KEY_txt));
                    rep[count][4] = cursor.getString(cursor.getColumnIndex(KEY_likes));
                    rep[count][5] = cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT));
                    rep[count][6] = cursor.getString(cursor.getColumnIndex(KEY_id));

                    count ++;


                } while (cursor.moveToNext());
            }

        return rep;

    }

    public String read (){
        SQLiteDatabase dp = this.getReadableDatabase();

        Cursor cursor = dp.query(table_name, new String[] {c1}, null,
                null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String result = cursor.getString(0);

        return result;


    }

    public String[] returnprofile (){
        SQLiteDatabase dp = this.getReadableDatabase();

        Cursor cursor = dp.query(TABLE_PROFILE, new String[] {KEY_S_a,KEY_S_q, KEY_Updated_at,KEY_About, KEY_Real, KEY_Username}, null,
                null, null, null, null, null);

        int num = cursor.getCount();
        if (cursor != null)
            cursor.moveToFirst();

        String[] result = new String[7];

         result[0] = cursor.getString(0);
        result[1] = cursor.getString(1);
        result[2] = cursor.getString(2);
        result[3] = cursor.getString(3);
        result[4] = cursor.getString(4);
        result[5] = cursor.getString(5);
        result[6] = String.format("%d",num);


        return result;


    }

    public String[] returnConfig (){
        SQLiteDatabase dp = this.getReadableDatabase();

        Cursor cursor = dp.query(TABLE_AppConfig, new String[] {KEY_AppTotalRow,KEY_AppRowIndext, KEY_AppReply,KEY_AppProfile, KEY_AppMood, KEY_AppUsername}, null,
                null, null, null, null, null);

        int num = cursor.getCount();
        if (cursor != null)
            cursor.moveToFirst();

        String[] result = new String[7];

        result[0] = cursor.getString(0);
        result[1] = cursor.getString(1);
        result[2] = cursor.getString(2);
        result[3] = cursor.getString(3);
        result[4] = cursor.getString(4);
        result[5] = cursor.getString(5);
        result[6] = String.format("%d",num);


        return result;


    }




    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();


    }

    public void deleteReplies() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_REPLIES, null, null);
        db.close();


    }

    public void deleteProfile() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PROFILE, null, null);
        db.close();


    }

    public void deleteAppconfig() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_AppConfig, null, null);
        db.close();


    }



}

