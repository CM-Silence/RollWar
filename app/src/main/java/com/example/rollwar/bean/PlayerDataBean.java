package com.example.rollwar.bean;


/**
 * description ： 用于储存ViewPager2中的数据
 * author : Silence
 */

public class PlayerDataBean {
    private int playerImageID;//角色图片id
    private int weaponImageID;//武器图片id
    private boolean enabled;

    public PlayerDataBean(int playerImageID, int weaponImageID){
        this.playerImageID = playerImageID;
        this.weaponImageID =weaponImageID;
        this.enabled = false;
    }

    public int getPlayerImageID() {
        return playerImageID;
    }

    public void setPlayerImageID(int playerImageID) {
        this.playerImageID = playerImageID;
    }

    public int getWeaponImageID() {
        return weaponImageID;
    }

    public void setWeaponImageID(int weaponImageID) {
        this.weaponImageID = weaponImageID;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
