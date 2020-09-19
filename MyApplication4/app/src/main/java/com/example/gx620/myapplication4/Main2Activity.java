package com.example.gx620.myapplication4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends Activity {

    private TextView textView;
    private Button btnAdd, btnSub, btnMul, btnDiv, btnClear;
    private EditText editText, editText2;
    private float op1, op2, op3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView = (TextView) findViewById(R.id.textView);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText = (EditText) findViewById(R.id.editText);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnClear = (Button) findViewById(R.id.btnClear);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Since we're using numbers. We have to parse them from String to Float for getting input.
                op1 = Float.parseFloat(editText.getText().toString());
                op2 = Float.parseFloat(editText2.getText().toString());
                op3 = op1 + op2;
                textView.setText(Float.toString(op3));



            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op1 = Float.parseFloat(editText.getText().toString());
                op2 = Float.parseFloat(editText2.getText().toString());
                op3 = op1 - op2;
                textView.setText(Float.toString(op3));
            }
        });


        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op1 = Float.parseFloat(editText.getText().toString());
                op2 = Float.parseFloat(editText2.getText().toString());
                op3 = op1 * op2;
                textView.setText(Float.toString(op3));
            }
        });


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { editText.setText("");
                editText2.setText("");
                textView.setText("");
            }
        });



        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op1 = Float.parseFloat(editText.getText().toString());
                op2 = Float.parseFloat(editText2.getText().toString());
                op3 = op1 / op2;
                textView.setText(Float.toString(op3));
            }
        });
    }}
