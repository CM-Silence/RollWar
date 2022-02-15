package com.example.rollwar.page.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.rollwar.R;
import com.example.rollwar.page.activity.MainActivity;
import com.example.rollwar.page.fragment.GameFragment;
import com.example.rollwar.page.fragment.MainFragment;


//简单的自定义弹窗
public class GameDialog extends Dialog implements View.OnClickListener{
    private TextView mTvTitle; //显示的标题
    private Button mBtnContinue; //继续按钮
    private Button mBtnLeave; //离开按钮
    private String title; //标题
    private boolean mContinue = true; //是否有继续按钮


    public GameDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_main_game_pause);
        setCanceledOnTouchOutside(true); //对话框在用户碰到对话框以外的地方时关闭
        initView();
        initClick();
    }

    private void initView(){
        mTvTitle = findViewById(R.id.dialog_tv_title);
        if(title != null) {
            mTvTitle.setText(title); //设置标题
        }
        else {
            mTvTitle.setVisibility(View.GONE); //如果没有标题则隐藏
        }
        mBtnContinue = findViewById(R.id.dialog_btn_continue);
        if(!ismContinue()){
            mBtnContinue.setVisibility(View.GONE);
        }
        mBtnLeave = findViewById(R.id.dialog_btn_leave);
    }

    private void initClick(){
        mBtnContinue.setOnClickListener(this);
        mBtnLeave.setOnClickListener(this);
    }

    public String getTitle() {
        return title;
    }

    public GameDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean ismContinue() {
        return mContinue;
    }

    public GameDialog setContinue(boolean mContinue) {
        this.mContinue = mContinue;
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_btn_continue:{
                this.dismiss();
                break;
            }
            case R.id.dialog_btn_leave:{
                MainFragment.gameFragment.requireActivity().onBackPressed();
                MainFragment.gameFragment = null;
                MainFragment mainFragment = (MainFragment) MainActivity.mainFragment;
                mainFragment.menuBack();
                this.dismiss();
                break;
            }
        }
    }
}
