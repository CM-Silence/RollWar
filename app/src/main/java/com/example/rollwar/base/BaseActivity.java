package com.example.rollwar.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 用于设置一些所有 Activity 可能都需要的属性
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 是否锁定竖屏
     */
    private final boolean mIsPortraitScreen;

    /**
     * 是否沉浸式状态栏
     */
    private final boolean mIsFullScreen;

    public static int screenHeight; //屏幕高度

    public static int screenWidth; //屏幕宽度

    public BaseActivity() {
        mIsPortraitScreen = true;
        mIsFullScreen = true;
    }

    /**
     * @param isPortraitScreen 是否锁定竖屏
     * @param isFullScreen 是否沉浸式状态栏
     */
    public BaseActivity(boolean isPortraitScreen, boolean isFullScreen) {
        mIsPortraitScreen = isPortraitScreen;
        mIsFullScreen = isFullScreen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mIsPortraitScreen) { // 锁定竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if (mIsFullScreen) { // 沉浸式状态栏
            FullScreen(this);
        }

        //获取屏幕宽高
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point); //还有个getRealSize()方法,不过那个宽高包括了部分手机底部的那个导航栏
        screenWidth = point.x;
        screenHeight = point.y;
    }

    //全屏(顺便隐藏状态栏和导航栏)
    private void FullScreen(Activity activity){
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}
