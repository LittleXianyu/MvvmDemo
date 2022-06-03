package com.example.mvvmdemo;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
//        GcWatcherInternal.addGcWatcher(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("xianyu","addGcWatcher");
//            }
//        });
//        MyNotificationManager.createNotificationChannel(this);


    }
}
