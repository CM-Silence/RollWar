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


    public EnemySpawn(@NonNull Context context) {
        super(context);
    }

    public EnemySpawn(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EnemySpawn(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    //难度增加
    private void addDifficulty(){
        if(spawnTime > 2000){
            spawnTime -= 500; //刷怪时间减短
        }
    }

    public void start(){
        initData();
        spawnEnemy();
    }

    private void initData(){
        enemyArrayList = new ArrayList<>();
        maxEnemy = 20;
        enemyNumber = 0;
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
                enemyArrayList.get(enemyNumber).setX(ra.nextInt(screenWidth - 200) + 100);
                enemyArrayList.get(enemyNumber).setY(ra.nextInt(50) - 150);
                enemyArrayList.get(enemyNumber).start();
                enemyNumber++;
                if (enemyNumber >= maxEnemy) {
                    if (enemyArrayList.get(0).isAttack()) { //第一个敌人还在屏幕内说明敌人不够用,要额外添加
                        addEnemy();
                    } else {
                        enemyNumber = 0; //从第一个Enemy对象开始复用
                    }
                }
                try {
                    Thread.sleep(spawnTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
