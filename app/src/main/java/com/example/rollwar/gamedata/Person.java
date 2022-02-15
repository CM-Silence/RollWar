package com.example.rollwar.gamedata;

import static android.service.controls.ControlsProviderService.TAG;
import static com.example.rollwar.utils.AnimationUtil.alpha;
import static com.example.rollwar.utils.AnimationUtil.alphaUDBack;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rollwar.R;
import com.example.rollwar.page.activity.MainActivity;
import com.example.rollwar.page.activity.StartActivity;
import com.example.rollwar.page.dialog.GameDialog;
import com.example.rollwar.page.fragment.GameFragment;

public abstract class Person extends androidx.appcompat.widget.AppCompatImageView {
    private int imageID; //图片ID
    private int maxHealth; //最大生命值
    private int health; //生命值
    private int damage; //攻击伤害
    private float attackSpeed; //攻击速度
    private float speed; //移动速度
    private int energy; //能量上限(备用)
    private float x;
    private float y;
    private boolean enemy; //是否是敌人

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
    public void setAttribute(int imageID, int health, int damage, float attackSpeed, float speed, int energy,boolean enemy){
        this.imageID = imageID;
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.speed = speed;
        this.energy = energy;
        this.enemy = enemy;
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

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    //受伤
    public void injure(Ammo ammo){
        this.setHealth(getHealth() - ammo.getPerson().getDamage());
        if(this.getHealth() <= 0){
            this.dead();
        }
        this.post(() -> {
            Message.obtain();
            GameFragment.refreshView(); //刷新
        });

    }

    public void injure(Person person){
        this.setHealth(getHealth() - person.getDamage());
        if(enemy = false){
            GameFragment.getPbHealth().setProgress(100 * health / maxHealth);
        }
        if(this.getHealth() <= 0){
            this.dead();
        }

        this.post(() -> {
            Message.obtain();
            GameFragment.refreshView(); //刷新
        });
    }

    //挂掉
    public void dead(){
        Log.w(TAG, "dead: someone");
        if(enemy = true){
            Log.w(TAG, "dead: enemy");
            GameFragment.getAttackEnemyArrayList().remove(this);//将敌人从列表中移除
            GameFragment.point += 20; //增加分数
            if(MainActivity.maxPoint < GameFragment.point){
                MainActivity.maxPoint = GameFragment.point;
            }
            this.post(() -> {
                Message.obtain();
                GameFragment.refreshView(); //刷新
            });
        }
        else{
            Log.w(TAG, "dead: player");
            GameFragment.gameOver = true; //游戏结束
            GameDialog myDialog = new GameDialog(getContext());
            myDialog.setTitle("游戏结束").show();
            MainActivity.saveData(); //保存数据
        }
    }


}
