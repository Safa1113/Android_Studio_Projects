package com.example.safofoh.myapplicationlab11;

/**
 * Created by Safofoh on 4/18/2018.
 */

import android.util.Log;

public class MyRunnableClass implements Runnable{

    public void run(){

        try{

            for(int i=100;i<105;i++){
                Thread.sleep(1000);
                Log.e("t1:<<runnable>>","runnable talking: "+ i );

            }

        }catch (InterruptedException e){
            Log.e("t1:<<runnable>>",e.getMessage());
        }

    }// run
}
