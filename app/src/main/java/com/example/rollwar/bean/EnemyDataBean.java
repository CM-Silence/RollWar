package com.example.rollwar.bean;

public class EnemyDataBean {
    private int enemyImageID;
    private String enemyName;
    private String enemyIntroduce;
    private boolean choose;

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
