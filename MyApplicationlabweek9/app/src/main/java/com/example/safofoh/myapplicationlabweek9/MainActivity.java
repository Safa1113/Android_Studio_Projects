package com.example.safofoh.myapplicationlabweek9;


        import android.app.Activity;
        import android.content.Context;
        import android.provider.Telephony;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.telecom.TelecomManager;
        import android.telephony.TelephonyManager;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    String IMEINumber;
    String subscriberID;
    String SIMSerialNumber;
    String networkCountryISO;
    String SIMCountryISO;
    String softwareVersion;
    String voiceMailNumber;
    TelephonyManager mt;
    Button b1;
    String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.t1);
        b1=(Button)findViewById(R.id.b1);

        TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        IMEINumber=tm.getDeviceId();
        subscriberID=tm.getLine1Number();
        SIMSerialNumber=tm.getSimSerialNumber();
        networkCountryISO=tm.getNetworkCountryIso();
        SIMCountryISO=tm.getSimCountryIso();
        softwareVersion=tm.getDeviceSoftwareVersion();
        voiceMailNumber=tm.getVoiceMailNumber();

        String strphoneType="";
        int phoneType=tm.getPhoneType();

        switch (phoneType){
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType="CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType="GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType="NONE";
                break;
        }


        String ta="";
        int call=tm.getCallState();

        switch (call){
            case (TelephonyManager.CALL_STATE_IDLE):
                ta="CALL_STATE_IDLE";
                break;
            case (TelephonyManager.CALL_STATE_OFFHOOK):
                ta="CALL_STATE_OFFHOOK";
                break;
            case (TelephonyManager.CALL_STATE_RINGING):
                ta="CALL_STATE_RINGING";
                break;
        }


        String sim="";
        int simstate=tm.getSimState();

        switch (simstate){
            case (TelephonyManager.SIM_STATE_ABSENT):
                sim="SIM_STATE_ABSENT";
                break;
            case (TelephonyManager.SIM_STATE_NETWORK_LOCKED):
                sim="SIM_STATE_NETWORK_LOCKED";
                break;
            case (TelephonyManager.SIM_STATE_PIN_REQUIRED):
                sim="SIM_STATE_PIN_REQUIRED";
                break;

            case (TelephonyManager.SIM_STATE_PUK_REQUIRED):
                sim="SIM_STATE_PUK_REQUIRED";
                break;
            case (TelephonyManager.SIM_STATE_READY):
                sim="SIM_STATE_READY";
                break;
            case (TelephonyManager.SIM_STATE_UNKNOWN):
                sim="SIM_STATE_UNKNOWN";
                break;


        }




        info="Phone Details:\n";
        info+="\n IMEI Number: "+IMEINumber;
        info+="\nLine Number: "+subscriberID;
        info+="\nSim Serial Number: "+SIMSerialNumber;
        info+="\nPhone network Type: "+strphoneType;
        info+="\nCall state: "+ta;
        info+="\nSim state: "+sim;


    }
    public void click(View v){

        textView.setText(info);
    }

}
