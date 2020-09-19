package com.example.safofoh.myapplicationlab10;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class insTsk extends Activity {
    private EditText etName,etPhone;
    private MyDBHandler dbHandler;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_tsk);
        etName = (EditText)findViewById(R.id.edName);
        etPhone = (EditText)findViewById(R.id.edPhone);
        dbHandler = new MyDBHandler(getApplicationContext());
        db = dbHandler.getWritableDatabase();
    }
    //Add a record to the database
    public void addButtonClicked(View view){
        String naStr = etName.getText().toString();
        String phStr = etPhone.getText().toString();
        if(naStr.isEmpty() || phStr.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please, fill-in missing data",Toast.LENGTH_LONG).show();
            return;
        }

        db.execSQL("insert into " + dbHandler.TABLE_NAME + "(" + dbHandler.COLUMN_NAME + ","+
                dbHandler.COLUMN_PHONE +") VALUES (?,?)", new String[] {naStr,phStr});

        String tstMsg;
        tstMsg = "Name: " + naStr + ", Phone: " + phStr + " is inserted";
        Toast.makeText(getApplicationContext(),tstMsg,Toast.LENGTH_LONG).show();
        etName.setText("");
        etPhone.setText("");

    }
    public void backTo(View view){
        Intent t = new Intent(this,MainActivity.class);
        startActivity(t);
        dbHandler.close();
        finish();
    }
}