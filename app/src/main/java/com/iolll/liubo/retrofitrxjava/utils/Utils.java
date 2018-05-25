package com.iolll.liubo.retrofitrxjava.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public class Utils {

//    Toast
    public static Gson gson = new Gson();
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
    public static String getString(@StringRes int resId){
        return context.getString(resId);
    }

    public static void toast(String s) {
        Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
    }

    public static void toast(int s) {
        Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
    }

    public static void longToast(String s) {
        Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
    }
    public static void startAct(Class toclass) {
        Intent intent = new Intent(context,toclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }


        @SuppressLint("StaticFieldLeak")
        private static Application sApplication;

        static WeakReference<Activity> sTopActivityWeakRef;
        static List<Activity> sActivityList = new LinkedList<>();

        private static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                sActivityList.add(activity);
                setTopActivityWeakRef(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                setTopActivityWeakRef(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                setTopActivityWeakRef(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                sActivityList.remove(activity);
            }
        };



        /**
         * 初始化工具类
         *
         * @param app 应用
         */
        public static void init(@NonNull final Application app) {
            Utils.sApplication = app;
            app.registerActivityLifecycleCallbacks(mCallbacks);
            context = app;
        }

        /**
         * 获取Application
         *
         * @return Application
         */
        public static Application getApp() {
            if (sApplication != null) return sApplication;
            throw new NullPointerException("u should init first");
        }

        private static void setTopActivityWeakRef(Activity activity) {
            if (sTopActivityWeakRef == null || !activity.equals(sTopActivityWeakRef.get())) {
                sTopActivityWeakRef = new WeakReference<>(activity);
            }
        }

}