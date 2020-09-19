package com.example.gx620.asmaaa;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void mov1(View v){

        Intent i=new Intent(getApplicationContext(),Main2Activity.class);
        startActivityForResult(i,1);

    }
    public void mov2(View v){
        Intent i=new Intent(getApplicationContext(),Main3Activity.class);
        startActivityForResult(i,2);

    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode==RESULT_OK && requestCode==1){
            String order="Order is: "+data.getStringExtra("coffee_Type")+data.getStringExtra("addTo_Coffee");
            Toast.makeText(getApplicationContext(),order,Toast.LENGTH_LONG).show();
        }
        else if(resultCode==RESULT_OK && requestCode==2){
            String order="was service is: "+data.getStringExtra("service Type");
            Toast.makeText(getApplicationContext(),order,Toast.LENGTH_LONG).show();
        }
    }
}

