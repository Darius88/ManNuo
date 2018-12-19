package com.src.mannuo.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



/**
 * Created by 张猛 on 2017/7/11
 */
public abstract class SimpleTitleActivity extends AppCompatActivity implements View.OnClickListener{
    public MyApplication mApp;
    public Bundle savedInstanceState;
    public LoadingDialog mDialog;

    /*********** 蓝牙相关参数 ***********/
    public  String GOD_1 = "0000fff0-0000-1000-8000-00805f9b34fb";
    public  String CHARACT_NOTIFY = "0000fff4-0000-1000-8000-00805f9b34fb";
    public  String SERVICE = "0000fff0-0000-1000-8000-00805f9b34fb";
    public  String CHARACT_WRITE = "0000fff1-0000-1000-8000-00805f9b34fb";


    public  String  product_1="Sex toys";
    public  String  product_2="LXCDVP";




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(setContentView());
        mApp = (MyApplication) getApplication();
//        http = new Http();

        mDialog = LoadingDialog.createDialog(this);

        initView();
        initView(savedInstanceState);
    }


    public Bundle getBundle() {
        return savedInstanceState;
    }

    protected void initView() {
    }


    protected void initView(Bundle savedInstanceState) {

    }



    /**
     * @return
     */
    public abstract int setContentView();

    protected void startActivity(Class<?> classs) {
        Intent intent = new Intent();
        intent.setClass(this, classs);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }


}
