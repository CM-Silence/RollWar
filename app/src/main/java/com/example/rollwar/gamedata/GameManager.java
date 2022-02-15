package com.example.rollwar.gamedata;

import android.graphics.RectF;
import android.view.View;

/**
 * description ： 实际上只是起到了碰撞检测的作用
 * author : Silence
 */

public class GameManager {

    //碰撞检测
    public static boolean isCrash(View view1,View view2){
        float view1X1 = view1.getX();
        float view1X2 = view1X1 + view1.getWidth();
        float view1Y1 = view1.getY();
        float view1Y2 = view1Y1 + view1.getHeight();

        float view2X1 = view2.getX();
        float view2X2 = view2X1 + view2.getWidth();
        float view2Y1 = view2.getY();
        float view2Y2 = view2Y1 + view2.getHeight();

        RectF rect1 = new RectF(view1X1,view1Y1,view1X2,view1Y2);
        RectF rect2 = new RectF(view2X1,view2Y1,view2X2,view2Y2);

        return RectF.intersects(rect1,rect2);
    }
}
