package activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import helper.SessionManager;



public class NotifyActivity extends Navigation {
    private static final String TAG = NotifyActivity.class.getSimpleName();

    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notify_activity);

        btnTxt = (Button) findViewById(R.id.txt);
        btnMentioned = (Button) findViewById(R.id.mentioneduser);
        btnWriter = (Button) findViewById(R.id.writer);
        btnLikes = (Button) findViewById(R.id.likes);
        btnTime = (Button) findViewById(R.id.time_signal);



        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }






    }

    @Override
    protected void onResume() {
        super.onResume();
        returnSignals();
    }



    private void returnSignals(){
        final String username = Navigation.USERNAME;
        final String mood = String.format("%d",Navigation.MOOD);



        pDialog.setMessage("Downloading Notify Stream ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Notify,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Response: " + response.toString());
                        hideDialog();



                        try {
                            JSONObject jObj = new JSONObject(response);



                            boolean error = jObj.getBoolean("error");



                            if (!error) {



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

                                    // Inserting row in users table
                                    db.addReplies(id, writer, txt, mentioned, likes, created_at);
                                    first();
                                }


                            } else {

                                // Error occurred. Get the error
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
                params.put("username", username);
                params.put("mood", mood);


                return params;
            }





        };



        ReaquestController.getInstance(this).addToRequestQueue(stringRequest);

    }




    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(NotifyActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
