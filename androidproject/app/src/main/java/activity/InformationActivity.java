package activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.gx620.androidproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.Navigation;
import app.ReaquestController;

import helper.SessionManager;


public class InformationActivity extends Navigation {
    private static final String TAG = InformationActivity.class.getSimpleName();
    private Button btnChange;
    private Button btnLinkToHome;
    private EditText changeFullName;
    private EditText changeEmail;
    private EditText changePassword;


    private EditText changeAbout;
    private EditText changeSecurityQ;
    private EditText changeSecurityA;


    private SessionManager session;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_activity);

        changeFullName = (EditText) findViewById(R.id.realname2);
        changeEmail = (EditText) findViewById(R.id.email2);
        changePassword = (EditText) findViewById(R.id.password2);


        changeAbout = (EditText) findViewById(R.id.about2);
        changeSecurityQ = (EditText) findViewById(R.id.sq2);
        changeSecurityA = (EditText) findViewById(R.id.sa2);

        btnChange = (Button) findViewById(R.id.btnChange);
        btnLinkToHome = (Button) findViewById(R.id.btnLinkToHome);




        // Session manager
        session = new SessionManager(getApplicationContext());




        // Check if user is already logged in or not
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Change Button Click event
        btnChange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                String realname = changeFullName.getText().toString().trim();
                String username = Navigation.USERNAME;
                String about = changeAbout.getText().toString().trim();
                String sequrity_q = changeSecurityQ.getText().toString().trim();
                String sequrity_a = changeSecurityA.getText().toString().trim();
                String email = changeEmail.getText().toString().trim();
                String password = changePassword.getText().toString().trim();

                if (!realname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !username.isEmpty() && !about.isEmpty() && !sequrity_q.isEmpty() && !sequrity_a.isEmpty()) {
                    change(username, email, password, realname, about, sequrity_q, sequrity_a);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please fill in all the blankets", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Settings Screen
        btnLinkToHome.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        SettingsActivity.class);
                startActivity(i);
                finish();
            }
        });

    }



    private void change(final String username, final String email,
                          final String password, final String realname, final String about, final String s_q, final String s_a) {


        pDialog.setMessage("Wait ...");
        showDialog();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Change,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, " Response: " + response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if (!error) {


                                Toast.makeText(getApplicationContext(), "information successfully changed", Toast.LENGTH_LONG).show();

                                // Launch Settings activity
                                Intent intent = new Intent(
                                        InformationActivity.this,
                                        SettingsActivity.class);
                                startActivity(intent);
                                finish();
                            } else {

                                // Error occurred . Get the error
                                // message
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Changing Information Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }){

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("realname", realname);
                params.put("about", about);
                params.put("sequrity_q", s_q);
                params.put("sequrity_a", s_a);

                return params;
            }





        };



        ReaquestController.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(InformationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



}
