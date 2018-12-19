package com.src.mannuo.eventbus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangmeng on 2018/12/19.
 */

public class MainBatteryEventBean implements Parcelable {

    public MainBatteryEventBean(){

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    protected MainBatteryEventBean(Parcel in) {
    }

    public static final Parcelable.Creator<MainBatteryEventBean> CREATOR = new Parcelable.Creator<MainBatteryEventBean>() {
        @Override
        public MainBatteryEventBean createFromParcel(Parcel source) {
            return new MainBatteryEventBean(source);
        }

        @Override
        public MainBatteryEventBean[] newArray(int size) {
            return new MainBatteryEventBean[size];
        }
    };
}
