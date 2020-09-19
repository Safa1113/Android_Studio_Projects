package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gx620.androidproject.MainActivity;
import com.example.gx620.androidproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.Navigation;
import app.ReaquestController;
import helper.SQLiteHandler;
import helper.SessionManager;

/**
 * Created by GX620 on 04/04/17.
 */

public class MoodActivity extends Navigation{

    private static final String TAG = MoodActivity.class.getSimpleName();
    private Button alpha, beta, delta;

    private SessionManager session;
    private int mood;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mood);

        alpha = (Button) findViewById(R.id.alpha);
        beta = (Button) findViewById(R.id.beta);
        delta = (Button) findViewById(R.id.delta);




        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (!session.isLoggedIn()) {
            logoutUser();

        }

        alpha.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = Navigation.USERNAME;
                mood = 1;



                    changeMood (username, mood);

            }

        });

        beta.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = Navigation.USERNAME;
                mood = 2;



                changeMood (username, mood);

            }

        });

        delta.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = Navigation.USERNAME;
                mood = 3;



                changeMood (username, mood);

            }

        });



    }


    private void changeMood (final String username, final int mood){



        pDialog.setMessage("Changing mood ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_MOOD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Change mood Response: " + response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            // Check for error node in json
                            if (!error) {

                                // user successfully changed mood




                                Navigation.MOOD = mood;
                                String m ="";

                                if (mood==1)
                                    m = "Alpha";
                                if (mood==2)
                                    m = "Beta";
                                if (mood==3)
                                    m = "Delta";

                                Toast.makeText(getApplicationContext(),
                                        "your current mood now is " + m, Toast.LENGTH_LONG).show();


                                // Launch main activity
                                Intent intent = new Intent(MoodActivity.this,
                                        MainActivity.class);
                                startActivity(intent);
                                finish();




                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }){

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                String M = String.format("%d" , mood);
                params.put("username", username);
                params.put("mood", M);

                return params;
            }};

// Add a request (in this example, called stringRequest) to your RequestQueue.
        ReaquestController.getInstance(this).addToRequestQueue(stringRequest);




    }


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MoodActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
