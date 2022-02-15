package com.example.rollwar.utils;

import android.widget.Toast;

import com.example.rollwar.base.App;

public class ToastUtil {
    public static void show(String s) {
        Toast.makeText(App.appContext, s, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String s) {
        Toast.makeText(App.appContext, s, Toast.LENGTH_LONG).show();
    }
}
