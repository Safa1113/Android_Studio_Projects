package activity;

import android.app.ProgressDialog;
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

public class WriteActivity extends Navigation {
    private static final String TAG = WriteActivity.class.getSimpleName();
    private Button btnWrite;

    private EditText inputTxt;
    private EditText inputMentioned;



    private SessionManager session;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_activity);

        inputTxt = (EditText) findViewById(R.id.write_txt);
        inputMentioned = (EditText) findViewById(R.id.write_mentioned);
        btnWrite = (Button) findViewById(R.id.writeAction);





        // Session manager
        session = new SessionManager(getApplicationContext());


        // Check if user is already logged in or not
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Register Button Click event
        btnWrite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                String Txt = inputTxt.getText().toString().trim();
                String Mentioned = inputMentioned.getText().toString().trim();
                String Username = Navigation.USERNAME;

                if (!Txt.isEmpty() && !Username.isEmpty()) {
                    if (Reply==false)
                        write(Username, Mentioned, Txt);
                    else{
                        reply(Username, Mentioned, Txt);
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please write something", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen

    }

    private void write(final String username, final String mentioned,
                          final String txt) {



        pDialog.setMessage("Signaling ...");
        showDialog();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Write,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Signaling Response: " + response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if (!error) {


                                Toast.makeText(getApplicationContext(), "Signaling succeeded", Toast.LENGTH_LONG).show();


                                Intent intent = new Intent(
                                        WriteActivity.this,
                                        MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {


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
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }){

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("writer", username);
                params.put("txt", txt);
                if (!mentioned.isEmpty())
                params.put("mentioned", mentioned);


                return params;
            }





        };



        ReaquestController.getInstance(this).addToRequestQueue(stringRequest);

    }




    private void reply(final String username, final String mentioned,
                       final String txt) {


        String Pid = null;
        Reply = false;

        String[][] rep = db.getReplyDetails();
        if (!rep[0].equals("0")) {
            Pid = rep[row_index][6];
        }
        final String id = Pid;

        pDialog.setMessage("Signaling ...");
        showDialog();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Reply,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Signaling Response: " + response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if (!error) {


                                Toast.makeText(getApplicationContext(), "Signaling succeeded", Toast.LENGTH_LONG).show();


                                Intent intent = new Intent(
                                        WriteActivity.this,
                                        MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {


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
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }){

            @Override
            protected Map<String, String> getParams() {
                // Posting params
                Map<String, String> params = new HashMap<String, String>();
                params.put("writer", username);
                params.put("txt", txt);
                params.put("mentioned", mentioned);
                params.put("to_id", id);



                return params;
            }





        };



        ReaquestController.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(WriteActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
