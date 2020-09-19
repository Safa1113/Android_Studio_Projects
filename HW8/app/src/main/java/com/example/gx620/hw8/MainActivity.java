package com.example.gx620.hw8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;


public class MainActivity extends Activity {

    Button change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        TextView txtV= (TextView) findViewById(R.id.txtvSignUp);
         change = (Button)  findViewById(R.id.ChangeBtn);


        txtV.setOnClickListener(new View.OnClickListener(){


            public void onClick(View V){

                Intent it = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(it);
                finish();
            }

        });

    }





    public void usrLogIn(View V){

        EditText userName = (EditText) findViewById(R.id.nameInp);
        EditText passInput = (EditText) findViewById(R.id.pwdInp);

        String user = userName.getText().toString();
        String pass = passInput.getText().toString();

        SharedPreferences usrAccounts = getSharedPreferences("UsersAccounts", 0);


        if(!usrAccounts.contains(user)){
            Toast.makeText(getApplicationContext(),"The user account is not existent",Toast.LENGTH_LONG).show();
            return;
        }


        if( (usrAccounts.getString(user,null)).equals(pass)) {

            Toast.makeText(getApplicationContext(), "The access is authenticated", Toast.LENGTH_LONG).show();
            change.setVisibility(View.VISIBLE);
        }
        else
            Toast.makeText(getApplicationContext(),"The password entered is wrong",Toast.LENGTH_LONG).show();

    }

    public void gotochange(View V){

        Intent Int = new Intent(getApplicationContext(),Main3Activity.class);
        startActivity(Int);
        finish();
    }

}

