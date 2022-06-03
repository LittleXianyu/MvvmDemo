package com.example.mvvmdemo.jetpack.databinding;


import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

@BindingMethods({@BindingMethod(type = TextView.class, attribute = "toast1", method = "showToast")})
public class BindingMethodTextView extends androidx.appcompat.widget.AppCompatTextView {
    public BindingMethodTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void showToast(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }

        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }
}
