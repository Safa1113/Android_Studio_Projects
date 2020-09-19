package com.example.gx620.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import helper.SqliteHandler;

public class MainActivity extends Activity {


    private Button update;
    private Button insert;
    private Button delete;
    private Button next;
    private Button previous;

    private TextView auther;
    private TextView pages;
    private TextView title;
    private SqliteHandler handler;
    public static boolean isupdate = false;
    public static int row_index = 0;
    public static int total_row = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        insert = (Button) findViewById(R.id.insert);
        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);



        auther = (TextView) findViewById(R.id.auther);
        pages = (TextView) findViewById(R.id.page_numbers);
        title = (TextView) findViewById(R.id.title);

        handler = new SqliteHandler(this);

        handler.add("Lessons for Children", "Anna Laetitia Barbauld", "88");
        MainActivity.total_row = 1;


    }
    @Override
    protected void onResume() {
        super.onResume();
     if (total_row!= 0)
        first();
    }


    public void update (View v){
        if (total_row != 0) {
            isupdate = true;
            Intent i = new Intent(getApplicationContext(),
                    Main2Activity.class);
            startActivity(i);
            finish();

        }
    }
    public void insert (View v){

        isupdate = false;
        Intent i = new Intent(getApplicationContext(),
                Main2Activity.class);
        startActivity(i);
        finish();

    }

       public void delete (View v){
           if (total_row != 0) {
               String[][] book = handler.read();
               int row = MainActivity.row_index;

               String titl = book[row][1];
               handler.delete(titl);

               if (total_row != 1) {
                   first();

               } else {
                   total_row = 0;
                   showBooks(null,0);

               }
           }
    }


    public void next (View view){

        if (total_row != 0) {
            String[][] book = handler.read();
            if (++row_index != total_row) {
                String[] row = book[row_index];
                int total = total_row;
                showBooks(row, total);
            } else {
                row_index = 0;
                String[] row = book[row_index];
                int total = total_row;
                showBooks(row, total);
            }
        }
    }

    public void prev (View view){

        if (total_row != 0) {
            String[][] book = handler.read();
            if (--row_index != -1) {
                String[] row = book[row_index];
                int total = total_row;
                showBooks(row, total);
            } else {
                row_index = total_row - 1;
                String[] row = book[row_index];
                int total = total_row;
                showBooks(row, total);
            }
        }
    }

    public  void  first (){

        String [][] book = handler.read();
        row_index = 0;


        String [] row = book[0];

            int total = Integer.parseInt(book[0][0]);
            total_row = total;

            showBooks(row, total);

    }

    public  void showBooks(String [] row, int total) {


        if (total != 0) {
            title.setText(row[1]);
            auther.setText(row[2]);
            pages.setText(row[3]);

        }else{
            title.setText("");
            auther.setText("");
            pages.setText("");

        }


    }
}
