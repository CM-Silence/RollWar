package com.example.rollwar.gamedata;

import static com.example.rollwar.utils.AnimationUtil.alpha;
import static com.example.rollwar.utils.AnimationUtil.alphaUDBack;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rollwar.R;
import com.example.rollwar.page.activity.StartActivity;
import com.example.rollwar.page.fragment.GameFragment;

public abstract class Person extends androidx.appcompat.widget.AppCompatImageView {
    private int imageID;
    private int health;
    private int damage;
    private float attackSpeed;
    private float speed;
    private int energy;
    private float x;
    private float y;
    private boolean enemy; //是否是敌人

    private Handler mHandler;

    public Person(@NonNull Context context) {
        super(context);
    }

    public Person(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Person(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.setImageResource(imageID);
        super.onDraw(canvas);
    }

    //给玩家用的构造方法
    public void setAttribute(int imageID, int health, int damage, float attackSpeed, float speed, int energy){
        this.imageID = imageID;
        this.health = health;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.speed = speed;
        this.energy = energy;
        this.enemy = false;
        this.setImageResource(imageID);
    }

    //给敌人用的构造方法
    public void setAttribute(int imageID, int health, int damage, float attackSpeed, float speed){
        this.imageID = imageID;
        this.health = health;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.speed = speed;
        this.enemy = true;
        this.post(() -> {
            Message.obtain();
            this.setImageResource(imageID);
        });
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
        this.setImageResource(imageID);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }

    //攻击
    private void attack(int count){
        mHandler = new Handler();
        new Thread(() -> {
            while (health > 0) {
                try {
                    for(int i = 0; i < count; i++){
                        new MyHandler().start();
                    }
                    Thread.sleep((long) (attackSpeed * 1000));//等待一段时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //受伤
    public void injure(Ammo ammo){
        this.setHealth(getHealth() - ammo.getPerson().getDamage());
        if(this.getHealth() <= 0){
            dead();
        }
    }

    public void injure(Person person){
        this.setHealth(getHealth() - person.getDamage());
        if(this.getHealth() <= 0){
            dead();
        }
    }

    //挂掉
    private void dead(){
        if(enemy = true){
            GameFragment.getAttackEnemyArrayList().remove(this);
        }
        else{
            GameFragment.gameOver = true;
        }
    }

    //移动
    private void move(){

    }

    //用于UI处理的线程
    private class MyHandler extends Thread {
        @Override
        public void run() {

            //通过Handler将Runnable中的run方法传递给主线程执行
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Ammo ammo = new Ammo(getContext());
                    //ammo.setAttribute(R.drawable.ammo1,-5,0,Person.this);
                    ammo.setX(Person.this.getX() + Person.this.getWidth());
                    ammo.setY(Person.this.getY());
                    GameFragment.layout.addView(ammo); //UI操作只能在主线程中执行
                    mHandler = new Handler(); //每次重新执行run()方法都要new一个Handler出来
                }
            });
        }
    }
}
