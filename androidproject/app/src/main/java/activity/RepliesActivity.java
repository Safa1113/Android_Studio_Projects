package activity;


import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;



import com.example.gx620.androidproject.R;

import app.Navigation;

import helper.SessionManager;


public class RepliesActivity extends Navigation {


    private SessionManager session;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply_activity);

        btnTxt = (Button) findViewById(R.id.txt);
        btnMentioned = (Button) findViewById(R.id.mentioneduser);
        btnWriter = (Button) findViewById(R.id.writer);
        btnLikes = (Button) findViewById(R.id.likes);
        btnTime = (Button) findViewById(R.id.time_signal);


        session = new SessionManager(getApplicationContext());




        // Check if user is already logged in or not
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        first();



    }



    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(RepliesActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }




}
