package com.example.quseit.lazylib.widget;

import android.app.Application;

import org.xutils.x;

/**
 * author : pc-spyrx7
 * date : 2016/5/1
 * email:spyhanfeng@qq.com
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
