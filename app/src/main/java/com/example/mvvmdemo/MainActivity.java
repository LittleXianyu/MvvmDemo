package com.example.mvvmdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.bumptech.glide.Glide;

import com.example.mvvmdemo.databinding.MainBinding;
import com.example.mvvmdemo.fps.ChoreographerFPS;
import com.example.mvvmdemo.fps.LooperMonitor;
import com.example.mvvmdemo.jetpack.LifeCycleDemo;
import com.example.mvvmdemo.jetpack.databinding.model.Model1;
import com.example.mvvmdemo.jetpack.databinding.model.Model3;
import com.example.mvvmdemo.list.ListActivity;
import com.example.mvvmdemo.refresh.RefreshActivity;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    public static Context context;
    private MutableLiveData<String> mLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("xianyu","intent data: "+getIntent().getData());

        MainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.main);
        // 利用ObservableField数据自动更新UI
        Model1 model1 = new Model1("名字1");
        mainBinding.setModel1(model1);
        model1.getName().set("名字1-b");
        // ObservableList包装类，实现list到ui更新
        ObservableList<String> list = new ObservableArrayList<>();
        list.add("list 数据 index = 0");
        mainBinding.setIndex(0);
        mainBinding.setList(list);
        Model3 model3 = new Model3("名字3");
        mainBinding.setModel3(model3);
        // setListenr2对应的是 xml中定义的对象listener2,给这个对象绑定type类型对应的实体对象
        //添加一个生命周期观察者    getLifecycle()是FragmentActivity中的方法
        LifeCycleDemo observer = new LifeCycleDemo();
        getLifecycle().addObserver(observer);
        getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull @NotNull LifecycleOwner source, @NonNull @NotNull Lifecycle.Event event) {
//                Log.d("xianyu", "onStateChanged==> "+event.name());
            }
        });
        mainBinding.tolist.setOnClickListener(view->{
            Intent intent =  new Intent();
            intent.setClass(this, ListActivity.class);
            startActivity(intent);
        });
        mainBinding.toRefresh.setOnClickListener(view->{
            Intent intent =  new Intent();
            intent.setClass(this, RefreshActivity.class);
            startActivity(intent);
        });
        getWindow().getDecorView().getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                Log.d("xianyu","getDecorView onDraw");
            }
        });
        ChoreographerFPS.computeFps();
        LooperMonitor.register(listener);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d("xianyu","onWindowFocusChanged");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("xianyu","onResume");
    }


    private LooperMonitor.LooperDispatchListener listener = new LooperMonitor.LooperDispatchListener() {
        long startTime;
        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public void dispatchStart() {
            super.dispatchStart();
            startTime = SystemClock.uptimeMillis();
        }

        @Override
        public void dispatchEnd() {
            super.dispatchEnd();
            long costTime = SystemClock.uptimeMillis() - startTime;
//            if(costTime > 16){
//                Log.d("xianyu","掉帧");
//            }
//            Log.d("xianyu","costTime: "+costTime);
        }
    };
}