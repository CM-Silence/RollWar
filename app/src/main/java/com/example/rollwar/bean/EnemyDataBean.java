package com.example.rollwar.bean;


/**
 * description ： 用于储存RecycleView中的数据
 * author : Silence
 */

public class EnemyDataBean {
    private int enemyImageID; //图片ID
    private String enemyName; //敌人名称
    private String enemyIntroduce; //敌人介绍
    private boolean choose; //是否在rv中被选中

    public EnemyDataBean(int enemyImageID, String enemyName, String enemyIntroduce){
        this.enemyImageID = enemyImageID;
        this.enemyName = enemyName;
        this.enemyIntroduce = enemyIntroduce;
    }

    public int getEnemyImageID() {
        return enemyImageID;
    }

    public void setEnemyImageID(int enemyImageID) {
        this.enemyImageID = enemyImageID;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public String getEnemyIntroduce() {
        return enemyIntroduce;
    }

    public void setEnemyIntroduce(String enemyIntroduce) {
        this.enemyIntroduce = enemyIntroduce;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }
}
