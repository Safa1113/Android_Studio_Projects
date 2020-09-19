package com.example.safofoh.myapplicationlab8;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;


public class Main3Activity extends Activity {

    SharedPreferences usrAccs;
    EditText user;
    EditText pass;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

    }

    public void change(View V){
        String usr = user.getText().toString().trim();
        String pw = pass.getText().toString().trim();

        usrAccs = getApplicationContext().getSharedPreferences("UsersAccounts", 0); // 0 - for private mode


        if(usrAccs.contains(usr)){
            SharedPreferences.Editor usrAccsEditor = usrAccs.edit();
            usrAccsEditor.putString(usr,pw);
            usrAccsEditor.commit();

            Toast.makeText(getApplicationContext(),"password changed successfully",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"The user account doesn't exist",Toast.LENGTH_LONG).show();
        }

    }

    public void returnLogin(View V){


        Intent Int = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(Int);
        finish();

    }
}
