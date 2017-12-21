package com.remy.loginregisterlaravel;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by remy on 27/11/2017.
 */

public class MyApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        if(LeakCanary.isInAnalyzerProcess(this)){

            return;
        }

        LeakCanary.install(this);
    }
}
