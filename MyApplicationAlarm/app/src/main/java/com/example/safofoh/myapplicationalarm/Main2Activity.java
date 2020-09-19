package com.example.safofoh.myapplicationalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;
import android.widget.Toast;

public class Main2Activity extends Activity {




    private TimePicker alarmTimePicker;


    SQLite dbHandler;
    Button save, show, update, delete;
    EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        dbHandler = new SQLite(this);
        save = (Button) findViewById(R.id.b1);
        show = (Button) findViewById(R.id.b2);
        update = (Button) findViewById(R.id.b3);
        delete = (Button) findViewById(R.id.b4);
        ed = (EditText) findViewById(R.id.dbid);
    }

    public void show(View V){

        String dbString = dbHandler.databaseToString();
        Toast.makeText(getApplicationContext(),dbString,Toast.LENGTH_LONG).show();

    }

    public void save(View V){

        String hStr = alarmTimePicker.getCurrentHour().toString();
        String mStr = alarmTimePicker.getCurrentMinute().toString();

        dbHandler.addRecord(hStr,mStr);


    }


    public void delete(View V){

        String idStr = ed.getText().toString();
        dbHandler.delete(idStr);

    }

    public void update(View V){

        String hStr = alarmTimePicker.getCurrentHour().toString();
        String mStr = alarmTimePicker.getCurrentMinute().toString();
        String idStr = ed.getText().toString();

        dbHandler.update(idStr, hStr, mStr);

    }





}







