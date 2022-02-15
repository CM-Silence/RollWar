package com.example.rollwar.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

import com.example.rollwar.gamedata.Enemy;

public class AnimationUtil {
    private static ObjectAnimator alphaAnim1;
    private static ObjectAnimator alphaAnim2;//备用
    private static AnimatorSet animatorSet = new AnimatorSet();
    private static AnimatorSet settingAnimatorSet = new AnimatorSet();

    //逐渐消失(切换活动)
    public static void alphaChangeActivity(View view, int delay){
        if(animatorSet.isRunning()) //结束无限循环的动画
            animatorSet.cancel();
        alphaAnim1 = ObjectAnimator.ofFloat(view,"alpha",view.getAlpha(),0.0f);
        alphaAnim1.setDuration(500);
        alphaAnim1.setStartDelay(delay);
        alphaAnim1.start();
    }

    //逐渐消失
    public static void alphaDisappear(View view, int delay){
        view.setEnabled(false);
        alphaAnim1 = ObjectAnimator.ofFloat(view,"alpha",view.getAlpha(),0.0f);
        alphaAnim1.setDuration(800);
        alphaAnim1.setStartDelay(delay);
        alphaAnim1.start();
    }

    //逐渐出现
    public static void alphaAppear(View view,float finalAim ,int delay){
        view.setEnabled(true);
        alphaAnim1 = ObjectAnimator.ofFloat(view,"alpha",0.0f,finalAim);
        alphaAnim1.setDuration(800);
        alphaAnim1.setStartDelay(delay);
        alphaAnim1.start();
    }

    //渐变上下平移出现
    public static void alphaUDBack(View view, int direction, int delay){
        view.setEnabled(true);
        alphaAnim1 = ObjectAnimator.ofFloat(view,"alpha",0.0f,1.0f);
        alphaAnim1.setDuration(1000);
        alphaAnim2 = ObjectAnimator.ofFloat(view,"translationY",direction * 60.0f,0f);
        alphaAnim2.setDuration(750);
        animatorSet = new AnimatorSet();
        animatorSet.play(alphaAnim1).with(alphaAnim2);
        animatorSet.setStartDelay(delay);
        animatorSet.start();
    }

    //渐变上下平移离开
    public static void alphaUDLeave(View view, int direction, int delay){
        view.setEnabled(false);
        alphaAnim1 = ObjectAnimator.ofFloat(view,"alpha",1.0f,0.0f);
        alphaAnim1.setDuration(1000);
        alphaAnim2 = ObjectAnimator.ofFloat(view,"translationY",0f,direction * 60.0f);
        alphaAnim2.setDuration(750);
        animatorSet = new AnimatorSet();
        animatorSet.play(alphaAnim1).with(alphaAnim2);
        animatorSet.setStartDelay(delay);
        animatorSet.start();
    }

    //渐变左右平移离开
    public static void alphaRLLeave(View view, int direction){
        view.setEnabled(false);
        alphaAnim1 = ObjectAnimator.ofFloat(view,"alpha",1.0f,0.0f);
        alphaAnim1.setDuration(1000);
        alphaAnim2 = ObjectAnimator.ofFloat(view,"translationX",0f,direction * 600.0f);
        alphaAnim2.setDuration(750);
        animatorSet = new AnimatorSet();
        animatorSet.play(alphaAnim1).with(alphaAnim2);
        animatorSet.start();
    }

    //渐变左右平移回来
    public static void alphaRLBack(View view, int direction){
        view.setEnabled(true);
        alphaAnim1 = ObjectAnimator.ofFloat(view,"alpha",0.0f,1.0f);
        alphaAnim1.setDuration(1000);
        alphaAnim2 = ObjectAnimator.ofFloat(view,"translationX",direction * 600.0f,0f);
        alphaAnim2.setDuration(750);
        animatorSet = new AnimatorSet();
        animatorSet.play(alphaAnim1).with(alphaAnim2);
        animatorSet.start();
    }

    //文字闪动(无限循环)
    public static void alpha(View view,int delay){
        alphaAnim1 = ObjectAnimator.ofFloat(view,"alpha",view.getAlpha(),0.4f);
        alphaAnim1.setDuration(500);
        alphaAnim2 = ObjectAnimator.ofFloat(view,"alpha",0.4f,1.0f,0.4f);
        alphaAnim2.setRepeatMode(ValueAnimator.RESTART);
        alphaAnim2.setRepeatCount(ValueAnimator.INFINITE);
        alphaAnim2.setDuration(1000);
        animatorSet = new AnimatorSet();
        animatorSet.play(alphaAnim2).after(alphaAnim1);
        animatorSet.setStartDelay(delay);
        animatorSet.start();
    }

    //音量设置打开
    public static void settingMenuOpen(View view){
        if(settingAnimatorSet.isRunning()){
            settingAnimatorSet.cancel();
        }
        settingAnimatorSet = new AnimatorSet();
        settingAnimatorSet.play(ObjectAnimator.ofFloat(view,"translationY",0f,120f)).with
                (ObjectAnimator.ofFloat(view,"alpha",0f,1f));
        settingAnimatorSet.setDuration(400);
        settingAnimatorSet.start();
    }

    //音量设置关闭
    public static void settingMenuClose(View view){
        if(settingAnimatorSet.isRunning()){
            settingAnimatorSet.cancel();
        }
        settingAnimatorSet = new AnimatorSet();
        settingAnimatorSet.play(ObjectAnimator.ofFloat(view,"translationY",120f,0f)).with
                (ObjectAnimator.ofFloat(view,"alpha",1f,0f));
        settingAnimatorSet.setDuration(400);
        settingAnimatorSet.start();
    }


}
