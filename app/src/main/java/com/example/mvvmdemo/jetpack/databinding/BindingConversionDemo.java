package com.example.mvvmdemo.jetpack.databinding;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.databinding.BindingConversion;

public class BindingConversionDemo {
    @BindingConversion
    public static int convertStringToColor(String str) {
        if (str.equals("红色")) {
            return Color.parseColor("#FF4081");
        }

        if (str.equals("蓝色")) {
            return Color.parseColor("#3F51B5");
        }
        return Color.parseColor("#344567");
    }
}
