package com.example.rollwar.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * <p>这里使用了后面的知识，主要是获取一个全局的 context</p>
 * 在这里继承于 Application 后再去 AndroidManifest.xml 中添加上：android:name=".base.App"
 */
public class App extends Application {

    /**
     * <p>获取全局 context，关于某些东西初始化时是很有必要的</p>
     * <p><strong>Note: </strong>只有这里才可以把 context 设成 static，如果去掉下面这个注解，编译器还会报黄，原因与生命周期有关</p>
     * <p><strong>Note: </strong>这个 appContext 也并不是可以拿来随便乱用</p>
     */
    @SuppressLint("StaticFieldLeak")
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
