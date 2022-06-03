package com.example.mvvmdemo.jetpack;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.mvvmdemo.MainActivity;

import static androidx.lifecycle.Lifecycle.Event.ON_CREATE;
import static androidx.lifecycle.Lifecycle.Event.ON_STOP;

public class LifeCycleDemo implements LifecycleObserver {
    @OnLifecycleEvent(ON_STOP)
    void onStopped() {
//        Log.d("xianyu", "onStopped");
    }

    @OnLifecycleEvent(ON_CREATE)
    void onCreate() {
//        Toast.makeText(MainActivity.context,"OnLifecycleEvent onCreate",Toast.LENGTH_LONG).show();
        Log.d("xianyu", "onCreate");
    }

    @OnLifecycleEvent(ON_CREATE)
    void onCreate2() {
        Log.d("xianyu", "onCreate2");
    }


}
