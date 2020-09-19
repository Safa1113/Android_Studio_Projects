package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gx620.androidproject.R;

import app.Navigation;

import helper.SessionManager;


public class SettingsActivity extends Navigation{


    private Button showProfile, changeSettings, About, logout;
    private SessionManager session;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        showProfile = (Button) findViewById(R.id.showProfile);
        changeSettings = (Button) findViewById(R.id.changeSettings);
        About = (Button) findViewById(R.id.aboutThisApp);
        logout = (Button) findViewById(R.id.logout);




        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        showProfile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Navigation.PROFILE = Navigation.USERNAME;

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();


            }

        });

        changeSettings.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(intent);
                finish();


            }

        });


        About.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                finish();


            }

        });
        logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                logoutUser();


            }

        });



    }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



}
