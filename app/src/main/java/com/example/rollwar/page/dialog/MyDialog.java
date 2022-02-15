package com.example.rollwar.page.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.rollwar.R;


//简单的自定义弹窗
public class MyDialog extends Dialog {
    private TextView mTvTitle; //显示的标题
    private TextView mTvMessage; //显示的内容
    private String title; //标题
    private String message; //内容


    public MyDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login_about);
        setCanceledOnTouchOutside(true); //对话框在用户碰到对话框以外的地方时关闭
        initView();
    }

    private void initView(){
        mTvTitle = findViewById(R.id.dialog_tv_title);
        if(title != null) {
            mTvTitle.setText(title); //设置标题
        }
        else {
            mTvTitle.setVisibility(View.GONE); //如果没有标题则隐藏
        }

        mTvMessage = findViewById(R.id.dialog_tv_message);
        mTvMessage.setText(message); //设置内容
    }

    public String getTitle() {
        return title;
    }

    public MyDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MyDialog setMessage(String message) {
        this.message = message;
        return this;
    }
}
