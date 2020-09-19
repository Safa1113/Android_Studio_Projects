package com.example.gx620.myapplication4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class Main3Activity extends Activity {

    EditText input ;
    TextView result ;
    Button cal;
    private float op1, op2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        input= (EditText)findViewById(R.id.input);
        result = (TextView)findViewById(R.id.result);
        cal = (Button)findViewById(R.id.cal);


        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Since we're using numbers. We have to parse them from String to Float for getting input.
                op1 = Float.parseFloat(input.getText().toString());

                op2 = (9/5) * op1 + 32;
                result.setText(Float.toString(op2));
            }
        });

    }
}