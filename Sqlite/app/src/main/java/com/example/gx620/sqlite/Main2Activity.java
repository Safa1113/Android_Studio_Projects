package com.example.gx620.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import helper.SqliteHandler;

public class Main2Activity extends Activity {

    private Button save;
    private Button returnE;


    private EditText title;
    private EditText auther;
    private EditText pages;
    private SqliteHandler handler;
    private boolean isup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        save = (Button) findViewById(R.id.save);
        returnE = (Button) findViewById(R.id.returnE);


        auther = (EditText) findViewById(R.id.autherEdit);
        pages = (EditText) findViewById(R.id.page_numbersEdit);
        title = (EditText) findViewById(R.id.titleEdit);

        handler = new SqliteHandler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String titl = "";
        int total = MainActivity.total_row;
        if (total != 0) {
            String[][] book = handler.read();
            int row = MainActivity.row_index;

          titl  = book[row][1];
        }
        isup = MainActivity.isupdate;

        if (isup){
            save.setText("Update");
            title.setText(titl);
            title.setEnabled(false);


        }
        else{
            save.setText("Insert");
            title.setText("");


        }
    }

    public void returnE (View v){


        Intent i = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(i);
        finish();


    }

    public void save (View v){

        String auth = auther.getText().toString().trim();
        String titli = title.getText().toString().trim();
        String pag = pages.getText().toString().trim();

        //boolean is = MainActivity.isupdate;

        if (isup) {

            if (!titli.equals("") && !auth.equals("") && !pag.equals("")) {

                handler.update(titli, auth, pag);
                Toast.makeText(getApplicationContext(),
                        "Data has been updated successfully", Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }
            }



        else{
            if (!titli.equals("") && !auth.equals("") && !pag.equals("")) {
                handler.add(titli, auth, pag);
                Toast.makeText(getApplicationContext(),
                        "Data has been inserted successfully", Toast.LENGTH_LONG).show();

                MainActivity.total_row ++;
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }

        }

    }


}
