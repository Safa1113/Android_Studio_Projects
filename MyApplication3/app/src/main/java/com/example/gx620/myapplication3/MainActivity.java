package com.example.gx620.myapplication3;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends Activity {


    private static final String Tag = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(Tag,"onCreate (Bundle) called");
        setContentView(R.layout.activity_main);

        final Button btn = (Button) findViewById(R.id.btn2Show);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(getApplicationContext(), "Btn 1 message using listener", Toast.LENGTH_LONG);
                t.show();
            }


        });


    }


    @Override
    public void onStart(){
        super.onStart();
        Log.d(Tag,"onStart called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(Tag,"onResume called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(Tag,"onPause called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(Tag,"onStop called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(Tag,"onDestroy called");
    }


    public void showToast(View V){
        Toast t2 = Toast.makeText(getApplicationContext(), "Btn 2 message using event handling", Toast.LENGTH_LONG);
        t2.show();

    }
}







