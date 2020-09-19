package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gx620.androidproject.R;

import app.Navigation;


public class AboutActivity extends Navigation {

    private Button btnLinkToSettings;
    private TextView aboutApp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about_activity);

        aboutApp = (TextView) findViewById(R.id.APP);

        btnLinkToSettings = (Button) findViewById(R.id.btnLinkToSettingScreen);


        btnLinkToSettings.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        SettingsActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
