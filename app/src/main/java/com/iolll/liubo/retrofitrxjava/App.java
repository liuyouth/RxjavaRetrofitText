package com.iolll.liubo.retrofitrxjava;

import android.app.Application;

import com.iolll.liubo.retrofitrxjava.utils.Utils;

/**
 * Created by LiuBo on 2018/5/25.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
