package com.src.mannuo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZM on 2016/7/13 0013.
 */
public abstract class MainFragment extends  BaseFragment{

    private String title;
    private int iconId;
    private int leftIconId;
    /**
     * 标志位，标志已经初始化完成
     */
    public boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    public boolean mHasLoadedOnce;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getLeftIconId() {
        return leftIconId;
    }

    public void setLeftIconId(int leftIconId) {
        this.leftIconId = leftIconId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  super.onCreateView(inflater, container, savedInstanceState);
    }

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;

    //setUserVisibleHint  adapter中的每个fragment切换的时候都会被调用，如果是切换到当前页，那么isVisibleToUser==true，否则为false
    /***
     * 判断当前Fragment可见时才进行数据加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }



    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

}
