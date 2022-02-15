package com.example.rollwar.gamedata;

import android.view.View;

public class GameManager {
    public static boolean isCrash(View view1,View view2){
        float view1X1 = view1.getX();
        float view1X2 = view1X1 + view1.getWidth();
        float view1Y1 = view1.getY();
        float view1Y2 = view1Y1 + view1.getHeight();

        float view2X1 = view2.getX();
        float view2X2 = view2X1 + view2.getWidth();
        float view2Y1 = view2.getY();
        float view2Y2 = view2Y1 + view2.getHeight();

        //在四个角的情况
        if((view2X2 < view1X1 || view2X1 > view1X2) && (view2Y2 < view1Y1 || view2Y1 > view1Y2)){
            return false;
        }

        //在上下的情况
        if(((view1X2 > view1X1 && view1X2 < view2X2) || ((view1X1 > view1X1 && view1X1 < view2X2))) && (view2Y2 < view1Y1 || view2Y1 > view1Y2)){
            return false;
        }

        //在左右的情况
        if(((view1Y2 > view1Y1 && view1Y2 < view2Y2) || ((view1Y1 > view1Y1 && view1Y1 < view2Y2))) && (view2X2 < view1X1 || view2X1 > view1X2)){
            return false;
        }

        return true;
    }
}
