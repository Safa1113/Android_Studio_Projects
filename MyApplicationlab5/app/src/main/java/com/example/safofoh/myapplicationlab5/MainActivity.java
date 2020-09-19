package com.example.safofoh.myapplicationlab5;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    Button b1 ,b2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
    }


    public void call1 (View v){


        Intent I = new Intent(this,Main2Activity.class);

        startActivity(I);
        finish();
    }

    public void call2 (View v){


        Intent I = new Intent(this,Main3Activity.class);

        startActivity(I);
        finish();
    }

}
