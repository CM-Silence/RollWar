package com.example.rollwar.page.activity;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.rollwar.R;
import com.example.rollwar.base.BaseActivity;
import com.example.rollwar.gamedata.Player;
import com.example.rollwar.page.fragment.GameFragment;
import com.example.rollwar.page.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    public MainActivity(){
        super(true,true);
    }

    //用于储存数据的sp
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    //有关碎片的东西
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public static Fragment mainFragment;

    //一些数据
    public static int sound; //音量
    public static float touchX = 0;
    public static float touchY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_alpha_in,0);//入场动画
        initData(); //加载布局之前初始化数据
        setContentView(R.layout.activity_main);
        initFragment();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        return super.onTouchEvent(event);
    }

    private void initData(){
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sound = sharedPreferences.getInt("sound",50);
    }


    //开始该活动的方法
    public static void startActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private void initFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        mainFragment = fragmentManager.findFragmentById(R.id.activity_main);
        if (mainFragment == null) {
            mainFragment = new MainFragment();
        }
        fragmentTransaction.replace(R.id.activity_main, mainFragment);
        fragmentTransaction.commit();
    }

}