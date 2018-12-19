package com.src.mannuo.BluetoothManage;

import android.content.Context;

import com.inuker.bluetooth.library.BluetoothClient;

/**
 * Created by ZM on 2018/12/18 0018.
 */

public class BluetoothClientTask {
    //将同步内容下方到if内部，提高了执行的效率，不必每次获取对象时都进行同步，只有第一次才同步，创建了以后就没必要了。
    public  static volatile BluetoothClientTask instance=null;


    private BluetoothClientTask (){

    }

    public static  BluetoothClientTask  getInstance(){
        if(instance==null){
            synchronized(BluetoothClientTask .class){
                if(instance==null){
                    instance=new BluetoothClientTask();
                }
            }
        }
        return instance;
    }


    public  BluetoothClient getClient(Context context){
        BluetoothClient  client=new BluetoothClient(context);
        return  client;
    }

}