package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gx620.sqlite.MainActivity;

/**
 * Created by GX620 on 15/04/17.
 */

public class SqliteHandler extends SQLiteOpenHelper {

    private final static int version = 1;

    private final static String name = "project";

    private final static String table_name = "Books";

    private final static String c1 = "title";
    private final static String c2 = "author";
    private final static String c3 = "page_number";




    public SqliteHandler(Context context){
        super (context, name, null, version);

    }
    public void onCreate (SQLiteDatabase dp){

//String sql = "Create table " + table_name + " ( " + c1 + " text primary key " + " );";
     //   dp.execSQL(sql);

        String CREATE_Book_TABLE = "CREATE TABLE " + table_name + " ( "
                + c1 + " TEXT PRIMARY KEY, " + c2 + " TEXT, "
                + c3 + " TEXT  "
                 + " ); ";
        dp.execSQL(CREATE_Book_TABLE);



    }
    public void onUpgrade (SQLiteDatabase dp, int old, int newv){

        dp.execSQL("DROP TABLE IF EXISTS " + table_name);


        onCreate(dp);

    }

    public void add (String mainc1, String mainc2 , String mainc3){

        SQLiteDatabase dp = this.getWritableDatabase();

        ContentValues v = new ContentValues();

        v.put(c1,mainc1);
        v.put(c2,mainc2);
        v.put(c3,mainc3);

        dp.insert(table_name, null, v);
        dp.close();


    }

    public String[][] read (){
        SQLiteDatabase dp = this.getReadableDatabase();

        Cursor cursor = dp.query(table_name, new String[] {c1, c2, c3}, null,
                null, null, null, null, null);


        int num = cursor.getCount();
        String[][] book = new String[num][7];

        int count = 0;
        book[0][0] = String.format("%d",num);


        if (cursor.moveToFirst()) {
            do {

                book[count][1] = cursor.getString(cursor.getColumnIndex(c1));
                book[count][2] = cursor.getString(cursor.getColumnIndex(c2));
                book[count][3] = cursor.getString(cursor.getColumnIndex(c3));

                count ++;


            } while (cursor.moveToNext());
        }



        return book;


    }

    public void update (String mainc1, String mainc2 , String mainc3){

        SQLiteDatabase dp = this.getWritableDatabase();

        ContentValues v = new ContentValues();

       // v.put(c1,mainc1);
        v.put(c2,mainc2);
        v.put(c3,mainc3);
   String where = c1 + " = ?";
        dp.update(table_name, v, where, new String[] {mainc1});
       dp.close();

       // String sql = "UPDATE " + table_name + " set " + c2 + " = mainc2 "


    }

    public void delete (String mainc1){

        SQLiteDatabase dp = this.getWritableDatabase();


        dp.delete(table_name, c1 + " = ?", new String[] {mainc1} );
        dp.close();


    }




}
