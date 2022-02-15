package com.example.rollwar.page.activity;

import static com.example.rollwar.utils.AnimationUtil.*;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.rollwar.R;
import com.example.rollwar.base.BaseActivity;

public class StartActivity extends BaseActivity implements View.OnClickListener{
    private TextView mTvMaker;
    private TextView mTvContinue;
    private TextView mTvGameName;
    private Handler mHandler;

    public StartActivity(){
        super(true,true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();
        initClick();

        mTvMaker.animate().alpha(1.0f).rotation(10.0f).setDuration(3000).setStartDelay(0);

        //开线程
        mHandler = new Handler();
        new MyHandler().start();

    }

    private void initView(){
        mTvMaker = findViewById(R.id.start_tv_maker);
        mTvMaker.setAlpha(0.0f);

        mTvContinue = findViewById(R.id.start_tv_continue);
        mTvContinue.setAlpha(0.0f);

        mTvGameName = findViewById(R.id.start_tv_game);
        mTvGameName.setAlpha(0.0f);
    }

    private void initClick(){
        mTvMaker.setOnClickListener(StartActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_tv_maker:{
                //当出现闪动的"Click to continue时点击屏幕切换活动"
                if(mTvContinue.getAlpha() != 0) {
                    mTvMaker.setEnabled(false); //防止多次点击
                    //动画
                    alphaChangeActivity(mTvGameName,0);
                    alphaChangeActivity(mTvContinue,0);
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000L);
                            MainActivity.startActivity(StartActivity.this);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
        }
    }

    //用于UI处理的线程
    class MyHandler extends Thread {
        @Override
        public void run() {
            //等待5秒
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //通过Handler将Runnable中的run方法传递给主线程执行
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mTvMaker.animate().alpha(0.0f).rotation(10.0f).setDuration(800).setStartDelay(0);
                    alphaUDBack(mTvGameName,-1,500);
                    alpha(mTvContinue,2000);
                }
            });
        }
    }

}