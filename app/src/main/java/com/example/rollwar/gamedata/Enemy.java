package com.example.rollwar.gamedata;

import static android.service.controls.ControlsProviderService.TAG;
import static com.example.rollwar.base.BaseActivity.screenHeight;
import static com.example.rollwar.base.BaseActivity.screenWidth;
import static com.example.rollwar.gamedata.GameManager.isCrash;
import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rollwar.R;
import com.example.rollwar.page.activity.MainActivity;
import com.example.rollwar.page.fragment.GameFragment;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Person{
    private float x;
    private float y;
    private boolean attack; //是否有攻击性(在屏幕内)
    private int EnemyNumber; //敌人类型
    private int attackCount; //单次攻击次数

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

    public int getEnemyNumber() {
        return EnemyNumber;
    }

    public void setEnemyNumber(int enemyNumber) {
        EnemyNumber = enemyNumber;
    }

    //启动
    public void start(){
        this.setAttack(true);
        this.setAlpha(1f);
        GameFragment.getAttackEnemyArrayList().add(this);
        move();
        attack();
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

    //攻击
    private void attack() {
        new Thread(() -> {
            Random ra = new Random();
            outer:while (this.getHealth() > 0) {
                try {
                    for (int i = 0; i < this.attackCount; i++) {
                        ArrayList<Ammo> ammoArrayList = EnemySpawn.getAmmoArrayList();
                        int ammoNumber = EnemySpawn.getAmmoNumber();
                        int maxAmmo = EnemySpawn.getMaxAmmo();
                        switch (this.getEnemyNumber()){
                            case 1 : {
                                break outer; //一号敌人靠碰撞攻击,直接退出attack()方法即可
                            }
                            case 2: {
                                break; //二号敌人直线攻击,不需要调整子弹角度
                            }
                            case 3: {

                            }
                            case 4: {
                                ammoArrayList.get(ammoNumber).setRadian((float) ((random() * PI / 3) - PI / 6)); //-30到30度
                                break;
                            }
                            default:{
                                break;
                            }
                        }
                        ammoArrayList.get(ammoNumber).setX(this.getX() + this.getWidth() / 2);
                        ammoArrayList.get(ammoNumber).setY(this.getY());

                        ammoArrayList.get(ammoNumber).start();
                        EnemySpawn.setAmmoNumber(ammoNumber + 1);
                        if (ammoNumber >= maxAmmo) {
                            if (ammoArrayList.get(0).isAttack()) { //第一个子弹还在屏幕内说明子弹不够用,要额外添加
                                new EnemySpawn(null).addAmmo();
                            } else {
                                EnemySpawn.setAmmoNumber(0); //子弹够用,再从第一个子弹开始复用
                            }
                        }
                    }
                    Thread.sleep((long) (this.getAttackSpeed() * 1000));//等待一段时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

                if(getEnemyNumber() == 1) {
                    this.setX(x + speedX * 2);
                }
                this.setY(y + speedY);

                if(isCrash(this,GameFragment.getIvPlayer())){
                    GameFragment.getIvPlayer().injure(this);
                    break;
                }
            }
            this.destroy();
        }).start();
    }
}
