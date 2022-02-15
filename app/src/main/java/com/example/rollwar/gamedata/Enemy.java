package com.example.rollwar.gamedata;

import static com.example.rollwar.base.BaseActivity.screenHeight;
import static com.example.rollwar.base.BaseActivity.screenWidth;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rollwar.page.activity.MainActivity;
import com.example.rollwar.page.fragment.GameFragment;

import java.util.ArrayList;

public class Enemy extends Person{
    private float x;
    private float y;
    private boolean attack;

    public Enemy(@NonNull Context context) {
        super(context);
    }

    public Enemy(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Enemy(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    //启动
    public void start(){
        this.setAttack(true);
        this.setAlpha(1f);
        GameFragment.getAttackEnemyArrayList().add(this);
        move();
    }

    //销毁(不是真的销毁,留着再用)
    public void destroy(){
        this.setAttack(false);
        this.setAlpha(0f);
        GameFragment.getAttackEnemyArrayList().remove(this);
    }

    public void injure(Ammo ammo){
        this.setHealth(getHealth() - ammo.getPerson().getDamage());
        if(this.getHealth() <= 0){
            dead();
        }
    }

    private void dead(){
        GameFragment.getAttackEnemyArrayList().remove(this);
        this.destroy();
    }

    private void move(){
        //开线程
        new Thread(() -> {
            float i = 0; //用于让敌人左右移动
            while (getHealth() > 0 && y > -500 && y < screenHeight + 200 && x > -400 && x < screenWidth + 250) {
                try {
                    Thread.sleep(20L); //等待0.02秒移动一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //人物所在的坐标
                x = this.getX();
                y = this.getY();

                //人物的宽高
                float width = this.getWidth();
                float height = this.getHeight();

                //x轴和y轴方向上的速度
                float speedX = (float) (this.getSpeed() * sin(i));
                float speedY = this.getSpeed();
                i += 0.1;

                this.setX(x + speedX * 2);
                this.setY(y + speedY);

            }
            this.destroy();
        }).start();
    }
}
