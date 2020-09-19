package com.example.safofoh.myapplication6;

        import android.content.Intent;
        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class Main3Activity extends Activity {
    Button b1;
    String serviceType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        b1=(Button)findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent =new Intent();
                returnIntent.putExtra("service Type",serviceType);
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    public void selectService(View v){
        switch (v.getId()){
            case R.id.Excellent:
                serviceType="Excellent";
                break;
            case R.id.Good:
                serviceType="Good";
                break;

            case R.id.Average:
                serviceType="Average";
                break;
            case R.id.Poor:
                serviceType="Poor";
                break;
        }
    }}

