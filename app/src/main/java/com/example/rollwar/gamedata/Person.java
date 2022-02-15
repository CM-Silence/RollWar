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
import com.example.rollwar.page.activity.MainActivity;
import com.example.rollwar.page.activity.StartActivity;
import com.example.rollwar.page.fragment.GameFragment;

public abstract class Person extends androidx.appcompat.widget.AppCompatImageView {
    private int imageID;
    private int maxHealth;
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
        this.maxHealth = health;
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
        this.maxHealth = health;
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

    //受伤
    public void injure(Ammo ammo){
        this.setHealth(getHealth() - ammo.getPerson().getDamage());
        if(this.getHealth() <= 0){
            dead();
        }
    }

    public void injure(Person person){
        this.setHealth(getHealth() - person.getDamage());
        if(enemy = false){
            GameFragment.getPbHealth().setProgress(100 * health / maxHealth);
        }
        if(this.getHealth() <= 0){
            dead();
        }
    }

    //挂掉
    private void dead(){
        if(enemy = true){
            GameFragment.getAttackEnemyArrayList().remove(this);//将敌人从列表中移除
            GameFragment.point += 20; //增加分数
            GameFragment.refreshView(); //刷新分数
            if(MainActivity.maxPoint < GameFragment.point){
                MainActivity.maxPoint = GameFragment.point;
            }
        }
        else{
            GameFragment.gameOver = true; //游戏结束
            MainActivity.saveData(); //保存数据
        }
    }


}
