package com.iolll.liubo.retrofitrxjava;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.v7.app.AppCompatActivity;

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by LiuBo on 2018/5/25.
 */

public class BaseActivity extends AppCompatActivity implements LifecycleOwner {
    public final LifecycleProvider<Lifecycle.Event> provider
            = AndroidLifecycle.createLifecycleProvider(this);
}
