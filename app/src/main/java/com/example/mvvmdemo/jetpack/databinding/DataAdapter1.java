package com.example.mvvmdemo.jetpack.databinding;

import android.util.Log;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class DataAdapter1 {
    // BindingAdapter android属性要慎用，等于所有setText方法都被hook
    @BindingAdapter("android:text")
    public static void setText(TextView view, String text) {
        final CharSequence oldText = view.getText();
        if (text.equals(oldText) || (text == null && oldText.length() == 0) || text.endsWith("为所有text属性添加后缀")) {
            return;
        }
        view.setText(text + "+++  为所有text属性添加后缀");
    }

//    @BindingAdapter("app:bigText")
//    public static void setAppBigText(TextView view, String text) {
//        final CharSequence oldText = view.getText();
//        if (text == oldText || (text == null && oldText.length() == 0)) {
//            return;
//        }
//        view.setText(text + "+++ 自定义属性作为后缀");
//    }


}
