package com.example.safofoh.myapplication6;




        import android.content.Intent;
        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;

public class Main2Activity extends Activity {
    String coffeeType;
    String addTocoffee;
    CheckBox cre,sug;
    Button btnOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        cre=(CheckBox)findViewById(R.id.creamChkBox);
        sug=(CheckBox)findViewById(R.id.sugarChkBox);
        btnOrder=(Button)findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTocoffee="";
                if(cre.isChecked())
                    addTocoffee="....Cream added";
                if(sug.isChecked())
                    addTocoffee +="....Sugar added";
                Intent returnIntent =new Intent();
                returnIntent.putExtra("coffee_Type",coffeeType);
                returnIntent.putExtra("addTo_Coffee",addTocoffee);
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }
    public void selectCoffee(View v){
        switch (v.getId()){
            case R.id.radioDecaf:
                coffeeType="Decaf";
                break;
            case R.id.radioExpresso:
                coffeeType="Expressor";
                break;
            case R.id.radioColombian:
                coffeeType="Colombian";
                break;
        }
    }
}