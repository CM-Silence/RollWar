package com.example.rollwar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//简单的自定义view,添加了取得imageID的方法
public class MyImage extends androidx.appcompat.widget.AppCompatImageView {
    private int imageID;

    //注意:自定义view一定要下面三个构造函数，不然会报错
    public MyImage(@NonNull Context context) {
        super(context);
    }

    public MyImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }


}
