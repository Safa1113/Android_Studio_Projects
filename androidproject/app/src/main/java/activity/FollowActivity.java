package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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



public class FollowActivity extends Navigation {
    private Button btnFollow;
    private Button btnLinkToProfile;
    private EditText inputs_a;
    private TextView outputs_q;

    private String ANSWER;


    private SessionManager session;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.follow_activity);


        inputs_a = (EditText) findViewById(R.id.s_a);
        outputs_q = (TextView) findViewById(R.id.s_q);
        btnFollow = (Button) findViewById(R.id.FOLLOW);
        btnLinkToProfile = (Button) findViewById(R.id.btnLinkToProfileScreen);

        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }


        String[] profile = db.returnprofile();
        ANSWER = profile[0];
        outputs_q.setText("Answer this: " + profile[1]);

    }



    public void gotoprofile(View view) {

        Intent intent = new Intent(FollowActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }


    public void followuser(View view) {

        String answer = inputs_a.getText().toString().trim();
        // check if the user wrote something or not
        if (!answer.isEmpty()) {
            // the user has written something
            if (answer.equals(ANSWER)) {
                submitFollow(answer);

            } else {
                Toast.makeText(getApplicationContext(),
                        "WRONG ANSWER !!!", Toast.LENGTH_LONG)
                        .show();
            }


        } else {
            Toast.makeText(getApplicationContext(),
                    "Write something please", Toast.LENGTH_LONG)
                    .show();
        }


    }

    private void submitFollow(String answer) {

        final String follows = PROFILE;
        final String username = USERNAME;
        final String mood = String.format("%d", MOOD);
        final String a = answer;

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
                                            "You followed this user successfully", Toast.LENGTH_LONG).show();


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
                    // Posting params to following url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", username);
                    params.put("follows", follows);
                    params.put("mood", mood);
                    params.put("s_a", a);

                    return params;
                }


            };


            ReaquestController.getInstance(this).addToRequestQueue(stringRequest);

        }

    }


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(FollowActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}