package com.example.rollwar.gamedata;

import static android.service.controls.ControlsProviderService.TAG;
import static com.example.rollwar.base.BaseActivity.screenWidth;
import static java.lang.Math.random;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rollwar.R;
import com.example.rollwar.page.fragment.GameFragment;

import java.util.ArrayList;
import java.util.Random;

public class EnemySpawn extends androidx.appcompat.widget.AppCompatImageView{
    private ArrayList<Enemy> enemyArrayList; //装敌人的列表

    private int enemyNumber; //敌人序号
    private int maxEnemy; //敌人列表中的最大敌人数
    private long spawnTime; //敌人刷新时间
    private int spawnCount; //刷怪次数

    //用于随机生成敌人类别的数据
    private int enemy1Range;
    private int enemy2Range;
    private int enemy3Range;

    private static ArrayList<Ammo> ammoArrayList; //子弹列表(复用子弹对象,免得new一堆子弹出来撑爆手机内存)
    private static int ammoNumber; //子弹列表中的子弹序号
    private static int maxAmmo; //子弹列表中的子弹个数


    public EnemySpawn(@NonNull Context context) {
        super(context);
    }

    public EnemySpawn(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EnemySpawn(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static ArrayList<Ammo> getAmmoArrayList() {
        return ammoArrayList;
    }

    public static int getAmmoNumber() {
        return ammoNumber;
    }

    public static void setAmmoNumber(int ammo) {
        ammoNumber = ammo;
    }

    public static int getMaxAmmo() {
        return maxAmmo;
    }

    public static void setMaxAmmo(int max) {
        maxAmmo = max;
    }

    //难度增加
    private void addDifficulty(){
        if(spawnTime > 2000){
            spawnTime -= 250; //刷怪时间减短
        }
        if(enemy1Range > 20){
            enemy1Range -= 2;
        }
        if(enemy3Range < 30){
            enemy3Range += 1;
        }
        spawnCount = 0;
    }

    public void start(){
        initData();
        spawnEnemy();
    }

    private void initData(){
        enemyArrayList = new ArrayList<>();
        maxEnemy = 20;
        enemyNumber = 0;
        enemy1Range = 50;
        enemy2Range = 30;
        enemy3Range = 15;
        spawnTime = 5000;
        for(int i = 0; i < maxEnemy; i++){
            Enemy enemy = new Enemy(getContext());
            enemy.setAttribute(R.drawable.enemy1,30,20,5,10);
            enemy.setAlpha(0f);
            enemy.setAttack(false);
            enemy.setMinimumWidth(64);
            enemy.setMinimumHeight(64);
            enemyArrayList.add(enemy);
            GameFragment.layout.addView(enemy);
        }

        ammoArrayList = new ArrayList<>();
        ammoNumber = 0;
        maxAmmo = 10;
        for (int i = 0; i < maxAmmo; i++) {
            Ammo ammo = new Ammo(getContext());
            ammo.setAttribute(i, R.drawable.enemy_ammo1, 15, 0, null);
            ammo.setAlpha(0f);
            ammo.setMaxHeight(8);
            ammo.setMaxWidth(8);
            ammo.setAttack(false);
            ammoArrayList.add(ammo);
            GameFragment.layout.addView(ammo); //UI操作只能在主线程中执行
        }
    }

    private void addEnemy() {
        Log.w(TAG, "addEnemy: action");
        for(int i = 0; i < 5; i++){
            Enemy enemy = new Enemy(getContext());
            enemy.setAttribute(R.drawable.enemy1,30,20,5,10);
            enemy.setAlpha(0f);
            enemy.setAttack(false);
            enemy.setMinimumWidth(64);
            enemy.setMinimumHeight(64);
            enemyArrayList.add(enemy);

            //这东西一定要少用,不知道为什么很容易闪退
            this.post(() -> {
                Message.obtain();
                GameFragment.layout.addView(enemy); //UI操作只能在主线程中执行
            });
        }
        maxEnemy += 5;
    }

    private void spawnEnemy(){
        Random ra = new Random();
        new Thread(() -> {
            while (!GameFragment.gameOver) {
                int randomEnemy = ra.nextInt(100); //随机敌人
                if(randomEnemy < enemy1Range){
                    enemyArrayList.get(enemyNumber).setAttribute(R.drawable.enemy1,30,20,0,15);
                    enemyArrayList.get(enemyNumber).setEnemyNumber(1);
                }
                else if(randomEnemy < enemy1Range + enemy2Range){
                    enemyArrayList.get(enemyNumber).setAttribute(R.drawable.enemy2,40,20,3.5f,10);
                    enemyArrayList.get(enemyNumber).setEnemyNumber(2);
                }
                else if(randomEnemy < enemy1Range + enemy2Range + enemy3Range){
                    enemyArrayList.get(enemyNumber).setAttribute(R.drawable.enemy3,50,20,3f,10);
                    enemyArrayList.get(enemyNumber).setEnemyNumber(3);
                }
                else{
                    enemyArrayList.get(enemyNumber).setAttribute(R.drawable.enemy4,120,35,4f,5);
                    enemyArrayList.get(enemyNumber).setEnemyNumber(3);
                }
                enemyArrayList.get(enemyNumber).setX(ra.nextInt(screenWidth - 200) + 100);
                enemyArrayList.get(enemyNumber).setY(ra.nextInt(50) - 150);
                enemyArrayList.get(enemyNumber).start();
                enemyNumber++;
                spawnCount++;
                if (enemyNumber >= maxEnemy) {
                    if (enemyArrayList.get(0).isAttack()) { //第一个敌人还在屏幕内说明敌人不够用,要额外添加
                        addEnemy();
                    } else {
                        enemyNumber = 0; //从第一个Enemy对象开始复用
                    }
                }
                if(spawnCount >= 8){
                    addDifficulty(); //刷怪8次增加一次难度
                }
                try {
                    Thread.sleep(spawnTime + ra.nextInt(2000) - 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
