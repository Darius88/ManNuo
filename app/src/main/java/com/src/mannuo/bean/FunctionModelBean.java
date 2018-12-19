package com.src.mannuo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZM on 2018/12/18 0018.
 */

public class FunctionModelBean implements Parcelable {

    Integer  imagesId;

    boolean  isChecked;

    public Integer getImagesId() {
        return imagesId;
    }

    public void setImagesId(Integer imagesId) {
        this.imagesId = imagesId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "FunctionModelBean{" +
                "imagesId=" + imagesId +
                ", isChecked=" + isChecked +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.imagesId);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    public FunctionModelBean() {
    }

    protected FunctionModelBean(Parcel in) {
        this.imagesId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isChecked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<FunctionModelBean> CREATOR = new Parcelable.Creator<FunctionModelBean>() {
        @Override
        public FunctionModelBean createFromParcel(Parcel source) {
            return new FunctionModelBean(source);
        }

        @Override
        public FunctionModelBean[] newArray(int size) {
            return new FunctionModelBean[size];
        }
    };
}
