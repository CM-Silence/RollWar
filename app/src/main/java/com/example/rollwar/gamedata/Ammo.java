package com.example.rollwar.gamedata;

import static com.example.rollwar.base.BaseActivity.screenHeight;
import static com.example.rollwar.base.BaseActivity.screenWidth;
import static com.example.rollwar.gamedata.GameManager.isCrash;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rollwar.page.fragment.GameFragment;

public class Ammo extends androidx.appcompat.widget.AppCompatImageView{
    private int imageID;
    private int number; //序号
    private float speed; //子弹速度
    private boolean attack; //是否有攻击性
    private Person person; //谁射出的子弹
    private float x;
    private float y;
    private final float width = this.getWidth();
    private final float height = this.getHeight();
    private float radian;

    public Ammo(@NonNull Context context) {
        super(context);
    }

    public Ammo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Ammo(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.setImageResource(imageID);
        super.onDraw(canvas);
    }

    public void setAttribute(int number, int imageID, float speed, float radian, Person person){
        this.number = number;
        this.imageID = imageID;
        this.speed = speed;
        this.radian = radian;
        this.person = person;
        this.setImageResource(imageID);
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
        this.setImageResource(imageID);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public float getRadian() {
        return radian;
    }

    public void setRadian(float radian) {
        this.radian = radian;
    }

    public void start(){
        this.setAttack(true);
        this.setAlpha(1f);
        move(radian,person.isEnemy());
    }

    public void destroy(){
        this.setAttack(false);
        this.setAlpha(0f);
    }

    private void move(float radian,boolean enemy){
        x = this.getX();
        y = this.getY();
        new Thread(() -> {
            outer:while (y > 0 && y < screenHeight - height && x > 0 && x < screenWidth - width) {
                try {
                    Thread.sleep(20L); //等待0.02秒移动一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //子弹所在的坐标
                x = this.getX();
                y = this.getY();


                //x轴和y轴方向上的速度
                float speedX = (float) (this.getSpeed() * sin(radian));
                float speedY = (float) (this.getSpeed() * cos(radian));

                this.setX(x + speedX);
                this.setY(y + speedY);

                //如果碰到敌人
                if(enemy) {
                    //敌人的敌人只有主角一个
                    if (isCrash(this, GameFragment.getIvPlayer())) {
                        GameFragment.getIvPlayer().injure(this);
                        break;
                    }
                }
                else{
                    //主角的敌人数量比较多,这里想不到其他方法,只能把所有在屏幕内的敌人都检测一遍
                    for(int i = 0;i < GameFragment.getAttackEnemyArrayList().size(); i++) {
                        if (isCrash(this, GameFragment.getAttackEnemyArrayList().get(i))) {
                            GameFragment.getAttackEnemyArrayList().get(i).injure(this);
                            break outer; //跳出外层循环
                        }
                    }
                }

            }
            this.destroy(); //销毁子弹

        }).start();
    }

}


