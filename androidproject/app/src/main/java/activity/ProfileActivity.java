package activity;

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
 * Created by GX620 on 06/04/17.
 */

public class ProfileActivity extends Navigation {
    private static final String TAG = ProfileActivity.class.getSimpleName();
    private Button btnPuser;
    private Button btnPreal;
    private Button btnPfollow;
    private Button btnPtime;
    private Button btnPabout;
    private Button btnFOLLOW;





    private SessionManager session;

    private String alpha ;
    private String beta ;
    private String delta ;






    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        btnPuser = (Button) findViewById(R.id.prousername);
        btnPreal = (Button) findViewById(R.id.prorealname);
        btnPfollow = (Button) findViewById(R.id.profollowers);
        btnPtime = (Button) findViewById(R.id.protime);
        btnPabout = (Button) findViewById(R.id.proabout);
        btnFOLLOW = (Button) findViewById(R.id.profollow);


        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }





    }

    @Override
    protected void onResume() {
        super.onResume();
        showprofile(Navigation.PROFILE);
         followers(Navigation.PROFILE);

          printDetails();
    }






    private void showprofile (final String user){

        final String iam = Navigation.USERNAME;
        final String mood = String.format("%d",Navigation.MOOD);
        final String usern = user;



        pDialog.setMessage("Getting data ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Profile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            // Check for error node in json
                            if (!error) {

                                db.deleteProfile();

                                 String realname = jObj.getString("realname");
                                 String username = jObj.getString("username");
                                 String about = jObj.getString("about");
                                String seqQ = jObj.getString("sequrity_q");
                                String seqA = jObj.getString("sequrity_a");

                                 String updated_at = jObj
                                        .getString("updated_at");

                                db.addProfile( username, realname, about, seqQ, seqA,  updated_at);





                                JSONObject signal = jObj.getJSONObject("signals");
                                boolean signals_error = signal.getBoolean("error");


                                    db.deleteReplies();


                                    int j = Integer.parseInt(jObj.getString("count"));

                                    for(int i = 0; i<j; i++){
                                        String c = String.format ("%d",i);
                                        JSONObject count = jObj.getJSONObject(c);
                                        String id = count.getString("id");
                                        String writer = count.getString("writer");
                                        String txt = count.getString("txt");
                                        String mentioned = count.getString("mentioned");
                                        String likes = count.getString("likes");
                                        String created_at = count
                                                .getString("created_at");

                                        // Inserting row in replies table
                                        db.addReplies(id, writer, txt, mentioned, likes, created_at);

                                    }









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

                params.put("Iam", iam);
                params.put("mood", mood);
                params.put("username", usern);

                return params;
            }};

// Add a request (in this example, called stringRequest) to your RequestQueue.
        ReaquestController.getInstance(this).addToRequestQueue(stringRequest);




    }







    private void followers (final String user){


        final String username = user;



        pDialog.setMessage("Getting data ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Followers,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            // Check for error node in json
                            if (!error) {







                                alpha = jObj.getString("1");
                                beta = jObj.getString("2");
                                delta = jObj.getString("3");

                                //printDetails();




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
                params.put("username", username);

                return params;
            }};

// Add a request (in this example, called stringRequest) to your RequestQueue.
        ReaquestController.getInstance(this).addToRequestQueue(stringRequest);




    }







    private void printDetails() {

        String[] profile = db.returnprofile();

        if (Integer.parseInt(profile[6]) != 0)
        {
            btnPuser.setText(profile[5]);
            btnPreal.setText(profile[4]);
            btnPabout.setText(profile[3]);
            btnPtime.setText("last updated at " + profile[2]);
        }


        btnPfollow.setText("followers (alpha, beta, delta): " + alpha + ", " + beta + ", " + delta);

    }

    public void show_replies (View view){
        if (MOOD != 2) {
            Intent intent = new Intent(ProfileActivity.this, RepliesActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    "You can't view replies in this mood", Toast.LENGTH_LONG).show();
        }

    }


    public void follow_btn (View view){

        if (MOOD == 3  ){
            Intent intent = new Intent(ProfileActivity.this, FollowActivity.class);
            startActivity(intent);
            finish();

        }else{
            Follow();
        }

    }



    public void Follow () {

        final String follows = PROFILE;
        final String username = USERNAME;
        final String mood = String.format("%d",MOOD);

       if (!username.equals(follows)) {
        pDialog.setMessage("Following user ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Follow,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        hideDialog();



                        try {
                            JSONObject jObj = new JSONObject(response);


                            boolean error = jObj.getBoolean("error");


                            if (!error) {


                                Toast.makeText(getApplicationContext(),
                                        "You followed this user", Toast.LENGTH_LONG).show();


                            } else {

                                // Error occurred in registration. Get the error
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

                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("follows", follows);
                params.put("mood", mood);

                return params;
            }


        };


        ReaquestController.getInstance(this).addToRequestQueue(stringRequest);
    }
    else{
           Toast.makeText(getApplicationContext(),
                   "you can't follow yourself", Toast.LENGTH_LONG).show();
           hideDialog();
       }

    }




    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
