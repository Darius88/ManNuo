package com.src.mannuo.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张猛 on 2017/7/11
 */
public class MyApplication extends Application {
    MyApplication mApp;
    public static Context applicationContext;
    private static MyApplication instance;
    private static Context context;

    private String mElectric="";

    public static List<Activity> mListBlueActivity;


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        super.onCreate();
        mListBlueActivity = new ArrayList<>();
//        initAppStatusListener();
        applicationContext = this;
        instance = this;
        context = this.getApplicationContext();
    }

    public void addActivityToList(Activity activity) {
        if (!mListBlueActivity.contains(activity)) {
            mListBlueActivity.add(activity);
        }
    }

    public void clearActivityToList() {
        for (int i = 0; i < mListBlueActivity.size(); i++) {
            mListBlueActivity.get(i).finish();
        }
        mListBlueActivity.clear();
    }

    public static Context getContext() {
        return context;
    }

    public static MyApplication getApp() {
        if (instance != null && instance instanceof MyApplication) {
            return (MyApplication) instance;
        } else {
            instance = new MyApplication();
            instance.onCreate();
            return (MyApplication) instance;
        }
    }

    public static MyApplication getInstance() {
        return instance;
    }


    public String getmElectric() {
        return mElectric;
    }

    public void setmElectric(String mElectric) {
        this.mElectric = mElectric;
    }
}
