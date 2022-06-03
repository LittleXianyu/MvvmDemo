package com.example.mvvmdemo.jetpack.databinding.model;

import android.graphics.drawable.ColorDrawable;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingConversion;

public class Model3 extends BaseObservable {
    public Model3(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.example.mvvmdemo.BR.name);
    }
    @Bindable
    private String name;

    @BindingConversion
    public static ColorDrawable convertStringToDrawable(String color) {
        if(color.equals("红色")){
            return new ColorDrawable(0xffff0000);
        }
        else{
            return new ColorDrawable(0xff00ff00);
        }

    }

}
