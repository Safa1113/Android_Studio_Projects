package com.example.safofoh.myapplicationlab9;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;


public class MainActivity extends Activity {


    EditText etName,etPhone;
    SQLite dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etName = (EditText)findViewById(R.id.edName);
        etPhone = (EditText)findViewById(R.id.edPhon);

        dbHandler = new SQLite(this);

    }


    //Show the content of the DB using a Toast notification
    public void printDatabaseData(View V){

        String dbString = dbHandler.databaseToString();
        Toast.makeText(getApplicationContext(),dbString,Toast.LENGTH_LONG).show();

    }


    //Add a record to the database
    public void addButtonClicked(View view){

        String naStr = etName.getText().toString();
        String phStr = etPhone.getText().toString();

        dbHandler.addRecord(naStr,phStr);

        etName.setText("");
        etPhone.setText("");

    }

}

