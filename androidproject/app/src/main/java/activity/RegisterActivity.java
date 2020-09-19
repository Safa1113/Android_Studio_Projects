package activity;




        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.android.volley.Request.Method;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.example.gx620.androidproject.MainActivity;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.Map;

        import app.AppConfig;

        import app.Navigation;
        import app.ReaquestController;

        import com.example.gx620.androidproject.R;
        import helper.SessionManager;










public class RegisterActivity extends Navigation {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;

    private EditText inputUserName;
    private EditText inputAbout;
    private EditText inputSecurityQ;
    private EditText inputSecurityA;


    private SessionManager session;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.realname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        inputUserName = (EditText) findViewById(R.id.username);
        inputAbout = (EditText) findViewById(R.id.about);
        inputSecurityQ = (EditText) findViewById(R.id.sq);
        inputSecurityA = (EditText) findViewById(R.id.sa);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);





        // Session manager
        session = new SessionManager(getApplicationContext());





        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                String realname = inputFullName.getText().toString().trim();
                String username = inputUserName.getText().toString().trim();
                String about = inputAbout.getText().toString().trim();
                String sequrity_q = inputSecurityQ.getText().toString().trim();
                String sequrity_a = inputSecurityA.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!realname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !username.isEmpty() && !about.isEmpty() && !sequrity_q.isEmpty() && !sequrity_a.isEmpty()) {
                    register(username, email, password, realname, about, sequrity_q, sequrity_a);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please fill in all the blankets", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void register(final String username, final String email,
                              final String password, final String realname, final String about, final String s_q, final String s_a) {


        pDialog.setMessage("Registering ...");
        showDialog();


        StringRequest stringRequest = new StringRequest(Method.POST, AppConfig.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Register Response: " + response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if (!error) {
                                // User successfully stored in MySQL
                                // Now store the user in sqlite
                                //String uid = jObj.getString("uid");

                                JSONObject user = jObj.getJSONObject("user");
                                String uname = user.getString("username");
                                String email = user.getString("email");
                                String fname = user.getString("realname");
                                String created_at = user
                                        .getString("created_at");

                                // Inserting row in users table
                                db.addUser(uname, email, fname, created_at);

                                Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                                // Launch login activity
                                Intent intent = new Intent(
                                        RegisterActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                finish();
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
                        Log.e(TAG, "Registration Error: " + error.getMessage());
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



}