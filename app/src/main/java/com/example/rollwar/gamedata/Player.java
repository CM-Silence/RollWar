package com.example.rollwar.gamedata;

import static android.service.controls.ControlsProviderService.TAG;
import static com.example.rollwar.base.BaseActivity.screenHeight;
import static com.example.rollwar.base.BaseActivity.screenWidth;
import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import static java.lang.Math.sqrt;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rollwar.page.activity.MainActivity;
import com.example.rollwar.page.fragment.GameFragment;

import java.util.ArrayList;
import java.util.Random;

public class Player extends Person{
    private float x;
    private float y;
    private int playerNumber;
    private int attackCount;
    private int ammoNumber; //子弹列表中的子弹序号
    private int maxAmmo; //子弹列表中的子弹个数
    private int ammoImage; //子弹图片
    private ArrayList<Ammo> ammoArrayList; //子弹列表(复用子弹对象,免得new一堆子弹出来撑爆手机内存)

    private Handler mHandler;

    public Player(@NonNull Context context) {
        super(context);
    }

    public Player(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Player(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAttribute(int imageID, int health, int damage, float attackSpeed, float speed, int attackCount,int energy, int playerNumber, int ammoImage) {
        super.setAttribute(imageID, health, damage, attackSpeed, speed, energy);
        this.attackCount = attackCount;
        this.playerNumber = playerNumber;
        this.ammoImage = ammoImage;
    }

    public void start(){
        initData();
        move();
        attack();
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getAmmoImage() {
        return ammoImage;
    }

    public void setAmmoImage(int ammoImage) {
        this.ammoImage = ammoImage;
    }


    //初始化数据
    private void initData(){
        ammoArrayList = new ArrayList<>();
        ammoNumber = 0;
        maxAmmo = 50;
        for(int i = 0; i < maxAmmo; i++){
            Ammo ammo = new Ammo(getContext());
            ammo.setAttribute(i,ammoImage,-20,0,this);
            ammo.setAlpha(0f);
            ammo.setAttack(false);
            ammoArrayList.add(ammo);
            GameFragment.layout.addView(ammo); //UI操作只能在主线程中执行
        }
    }

    //如果列表中的子弹不够用的话就再添加
    private void addAmmo(){
        Log.w(TAG, "addAmmo: action");
        for(int i = 0; i < 10; i++){
            Ammo ammo = new Ammo(getContext());
            ammo.setAttribute(i + maxAmmo,ammoImage,-20,0,this);
            ammo.setAlpha(0f);
            ammo.setAttack(false);
            ammoArrayList.add(ammo);
            this.post(() -> {
                Message.obtain();
                GameFragment.layout.addView(ammo); //UI操作只能在主线程中执行
            });
        }
        maxAmmo += 10;
    }
    //人物移动逻辑,向着手指点击方向移动
    private void move(){
        //开线程
        new Thread(() -> {
            while (getHealth() > 0) {
                try {
                    Thread.sleep(20L); //等待0.02秒移动一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //手指点击的坐标
                float x0 = MainActivity.touchX;
                float y0 = MainActivity.touchY;

                //人物所在的坐标
                x = this.getX();
                y = this.getY();

                //人物的宽高
                float width = this.getWidth();
                float height = this.getHeight();

                //让人吐血的数学运算
                double hypotenuse = sqrt(pow(y0 - y, 2) + pow(x0 - x, 2)); //斜边长度(同时也是两者之间的距离)

                //x轴和y轴方向上的速度
                float speedX = (float) (this.getSpeed() * (x0 - x) / hypotenuse);
                float speedY = (float) (this.getSpeed() * (y0 - y) / hypotenuse);

                if(hypotenuse > 45) { //如果角色与手指点击距离很近的话就不用移动了(不然会剧烈抖动)
                    if((x > 0 || speedX > 0) && (x < screenWidth - width || speedX < 0))
                    this.setX(x + speedX);
                    if((y > 0 || speedY > 0) && (y < screenHeight - height || speedY < 0))
                    this.setY(y + speedY);
                }
            }
        }).start();
    }

    //攻击
    private void attack(){
        mHandler = new Handler();
        new Thread(() -> {
            Random ra = new Random();
            while (this.getHealth() > 0) {
                try {
                    for(int i = 0; i < this.attackCount; i++) {
                        ammoArrayList.get(ammoNumber).setX(Player.this.getX() + Player.this.getWidth());
                        ammoArrayList.get(ammoNumber).setY(Player.this.getY());
                        if (Player.this.getPlayerNumber() == 2) {
                            ammoArrayList.get(ammoNumber).setRadian((float) ((random() * PI / 3) - PI / 6)); //-30到30度
                        }
                        ammoArrayList.get(ammoNumber).start();
                        ammoNumber++;
                        if (ammoNumber >= maxAmmo) {
                            if (ammoArrayList.get(0).isAttack()) { //第一个子弹还在屏幕内说明子弹不够用,要额外添加
                                addAmmo();
                            } else {
                                ammoNumber = 0; //子弹够用,再从第一个子弹开始复用
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


}
