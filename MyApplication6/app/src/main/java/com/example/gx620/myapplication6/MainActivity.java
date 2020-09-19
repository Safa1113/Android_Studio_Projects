package com.example.gx620.myapplication6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private Button open, search, send, dial, loc, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        open = (Button) findViewById(R.id.openBtn);
        search = (Button) findViewById(R.id.searchBtn);
        send = (Button) findViewById(R.id.sendBtn);
        dial = (Button) findViewById(R.id.dialBtn);
        loc = (Button) findViewById(R.id.locBtn);
        contact = (Button) findViewById(R.id.contactBtn);

    }


    public void openSite  (View v)
    {

        EditText ed = (EditText) findViewById(R.id.httpLink);
        String url = "http://"+ ed.getText().toString();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }
    public  void sendSMS (View V){

        EditText ed = (EditText) findViewById(R.id.phoneNum);
        String url = "smsto:"+ ed.getText().toString();
        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_SENDTO,uri);

        startActivity(i);


    }
    public void dial (View V){

        EditText m = (EditText) findViewById(R.id.phoneNum);
        String myPhone = "tel:" + m.getText().toString() ;

        Intent itt = new Intent(Intent.ACTION_DIAL,
                Uri.parse(myPhone));
        startActivity(itt);


    }

    public void showContact (View V){
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(Uri.parse("content://contacts/people"));
        startActivity(intent);
    }

    public void searchTxt (View v) {

        Intent srch = new Intent(Intent.ACTION_WEB_SEARCH);
        srch.putExtra(SearchManager.QUERY,"");
        startActivity(srch);



    }
    public void showLoc(View v) {

        Intent loc = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?"+"saddr=9.983083,-84.0544308"+"daddr=9.926392,-84.055964"));

        startActivity(loc);

    }



}