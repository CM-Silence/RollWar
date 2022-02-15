package com.example.rollwar.view;

import static com.example.rollwar.utils.AnimationUtil.settingMenuClose;
import static com.example.rollwar.utils.AnimationUtil.settingMenuOpen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rollwar.R;
import com.example.rollwar.page.activity.MainActivity;

//音量设置按钮
public class SoundSettingButton extends FrameLayout implements View.OnClickListener {
    private boolean isMenuOpen = false; //是否打开
    private Button mBtnSetting;
    private SeekBar mSbSound;

    public boolean isMenuOpen() {
        return isMenuOpen;
    }

    public Button getBtnSetting() {
        return mBtnSetting;
    }

    public SoundSettingButton(@NonNull Context context) {
        super(context);
    }

    public SoundSettingButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.main_btn_setting,this);
        initView();
        initListener();
    }

    public SoundSettingButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(){
        mBtnSetting = findViewById(R.id.login_setting_btn_setting);
        mSbSound = findViewById(R.id.login_setting_sb_sound);
        mSbSound.setProgress(MainActivity.sharedPreferences.getInt("sound",60));
    }

    public void menuOpen(){
        settingMenuOpen(mSbSound);
        mBtnSetting.setText("当前音量" + MainActivity.sound);
        isMenuOpen = true;
    }

    public void menuClose(){
        settingMenuClose(mSbSound);
        mBtnSetting.setText("音量设置");
        isMenuOpen = false;
    }


    private void initListener(){
        mBtnSetting.setOnClickListener(this);

        mSbSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                MainActivity.sound = progress;
                mBtnSetting.setText("当前音量" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.editor.putInt("sound", MainActivity.sound);
            }
        });
    }

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    public void onClick(View view) {
        if(isEnabled()) {
            switch (view.getId()) {
                case R.id.login_setting_btn_setting: {
                    if (!isMenuOpen) {
                        menuOpen();
                    } else {
                        menuClose();
                    }
                    break;
                }
            }
        }
    }
}
