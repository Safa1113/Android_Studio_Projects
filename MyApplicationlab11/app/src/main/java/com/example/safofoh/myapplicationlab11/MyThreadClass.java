package com.example.safofoh.myapplicationlab11;

import android.util.Log;

/**
 * Created by Safofoh on 4/18/2018.
 */

public class MyThreadClass extends Thread{
    public void run()
    {
        try{

            for(int i=100;i<105;i++){
                Thread.sleep(1000);
                Log.e("t2:<<Thread>>","Thread talking: "+ i );

            }

        }catch (InterruptedException e){
            Log.e("t2:<<Thread>>",e.getMessage());
        }
    }

}
