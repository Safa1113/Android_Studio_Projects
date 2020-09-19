package com.example.safofoh.myapplicationlab10;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    private MyDBHandler dbHandler;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//dbHandler = new MyDBHandler(this);
        dbHandler = new MyDBHandler(getApplicationContext());
        db = dbHandler.getWritableDatabase();
    }

    //Show the content of the DB using a Toast notification
    public void printDatabaseData(View V) {
        String query = " SELECT * FROM "
        +MyDBHandler.TABLE_NAME;
        String dbData = " "
        ;
//Cursor point to a location in your result
        Cursor c = db.rawQuery(query, null);
//Move to first row in your result
        c.moveToFirst();
//Position after the last row means the end of the results
        while (!c.isAfterLast()) {

            dbData += c.getString(c.getColumnIndex(MyDBHandler.COLUMN_RECID));
            dbData += " | "
            +c.getString(c.getColumnIndex(MyDBHandler.COLUMN_NAME));
            dbData += " | "
            +c.getString(c.getColumnIndex(MyDBHandler.COLUMN_PHONE));
            dbData += "\n "
            ;
            c.moveToNext();
        }
        c.close();
        Toast.makeText(getApplicationContext(), dbData, Toast.LENGTH_LONG).show();
    }

    public void insForm(View V) {

        Intent t = new Intent(this, insTsk.class);
        startActivity(t);
        dbHandler.close();
        finish();
    }

    public void delForm(View V) {
        Intent t = new Intent(this, delTsk.class);
        startActivity(t);
        dbHandler.close();
        finish();

    }
}
