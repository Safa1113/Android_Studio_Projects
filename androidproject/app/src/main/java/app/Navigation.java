package app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gx620.androidproject.MainActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import activity.MoodActivity;
import activity.NotifyActivity;
import activity.ProfileActivity;
import activity.RepliesActivity;
import activity.SearchActivity;
import activity.SettingsActivity;
import activity.WriteActivity;
import helper.SQLite;




public class Navigation extends Activity {

    public static String USERNAME ;
    public static int MOOD = 3;
    public static String PROFILE;
    public static boolean Reply = false;
    public static int row_index = 0;
    public static int total_row = 0;




    public static Button btnTxt;
    public static Button btnMentioned;
    public static Button btnWriter;
    public static Button btnLikes;
    public static Button btnTime;
    public static SQLite db;
    public static ProgressDialog pDialog;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        db = new SQLite(getApplicationContext());
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

    }


    public static void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public static void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public void Ntxt (View view){
        String Pid= null;

        String[][] rep = db.getReplyDetails();
        if (!rep[0].equals("0")) {
            Pid = rep[row_index][6];
        }

        if (Pid!=null) {

            final String id= Pid;
            pDialog.setMessage("Loading replies...");
            showDialog();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_ShowReplies,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


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

                                    }
                                    first();




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
                    params.put("to_id", id);

                    return params;
                }


            };


            ReaquestController.getInstance(this).addToRequestQueue(stringRequest);

        }



    }

    public void Nlike(View view){
        String Pid= null;

        String[][] rep = db.getReplyDetails();
        if (!rep[0].equals("0")) {
             Pid = rep[row_index][6];
       }


if (Pid!=null) {

final String id= Pid;
    pDialog.setMessage("Wait ...");
    showDialog();

    StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Like,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    hideDialog();


                    try {
                        JSONObject jObj = new JSONObject(response);


                        boolean error = jObj.getBoolean("error");


                        if (!error) {



                            Toast.makeText(getApplicationContext(),
                                    "You liked this post successfully", Toast.LENGTH_LONG).show();


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
            params.put("id", id);

            return params;
        }


    };


    ReaquestController.getInstance(this).addToRequestQueue(stringRequest);

}


    }

    public void Nwriter (View view){
        if(total_row!=0) {
            Navigation.PROFILE = btnWriter.getText().toString().trim();

            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void Nmentioned (View view){
        if(total_row!=0) {
            Navigation.PROFILE = btnMentioned.getText().toString().trim();

            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void Nhome (View view){

        Intent i = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(i);
        finish();

    }

    public void Nmood (View view){

        Intent i = new Intent(getApplicationContext(),
                MoodActivity.class);
        startActivity(i);
        finish();

    }


    public void Nsettings (View view){


        Intent i = new Intent(getApplicationContext(),
                SettingsActivity.class);
        startActivity(i);
        finish();

    }

    public void Nwrite (View view){

        Intent i = new Intent(getApplicationContext(),
                WriteActivity.class);
        startActivity(i);
        finish();

    }

    public void Nsearch (View view){

        Intent i = new Intent(getApplicationContext(),
                SearchActivity.class);
        startActivity(i);
        finish();

    }
    public void Nnotify (View view){

        Intent i = new Intent(getApplicationContext(),
                NotifyActivity.class);
        startActivity(i);
        finish();

    }

    public void next (View view){

       String [][] rep = db.getReplyDetails();
        if(++row_index != total_row) {
           String [] row = rep[row_index];
            int total = total_row;
            showReplies(row, total);
        }
        else{
            row_index = 0;
            String [] row = rep[row_index];
            int total = total_row;
            showReplies(row, total);
        }
    }

    public void prev (View view){

        String [][] rep = db.getReplyDetails();
        if(--row_index != -1) {
            String [] row = rep[row_index];
            int total = total_row;
            showReplies(row, total);
        }
        else{
            row_index = total_row-1;
            String [] row = rep[row_index];
            int total = total_row;
            showReplies(row, total);
        }
    }

    public static void  first (){

        String [][] rep = db.getReplyDetails();
        row_index = 0;

        String [] row = rep[0];

            int total = Integer.parseInt(rep[0][0]);
       total_row = total;

        showReplies(row, total);

    }


    public static void showReplies(String [] row, int total) {


      if (total != 0) {
            btnWriter.setText(row[1]);
            btnMentioned.setText(row[2]);
            btnTxt.setText(row[3]);
            btnLikes.setText(row[4]);
            btnTime.setText(row[5]);

      }


    }

    public void Nreply (View view){
        Reply = true;
        Intent i = new Intent(getApplicationContext(),
                WriteActivity.class);
        startActivity(i);
        finish();
    }




}
