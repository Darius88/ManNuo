package com.src.mannuo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ZM
 * 适配器父类
 * @param <T>
 *      各类Bean 集合
 */
public abstract class BAdapter<T> extends BaseAdapter {

    public Activity mAct;
    public LayoutInflater mInflater;
    public MyApplication mApp;
    public List<T> mListData;
    protected int checkNum = 0;
    protected CheckListener checkListener;

    /***
     * 构造传入Activity参数
     * @param act
     */
    public BAdapter(Activity act) {
        mAct = act;
        mApp = (MyApplication) act.getApplication();
        mInflater = LayoutInflater.from(mAct);
        mListData = new ArrayList<T>();
    }

    /**
     * 设置监听
     */

    public void setOnCheckListener(CheckListener checkListener){
        this.checkListener = checkListener;
    };

    public interface CheckListener{
        void checkListener(boolean isZero);
    }
    /***
     * Context
     * @param context
     */
    public BAdapter(Context context) {
        mListData = new ArrayList<T>();
        mInflater = LayoutInflater.from(context);
    }

    /***
     *  添加bean集合
     * @param t
     *   bean 集合
     * @param isNotify
     *     是否需要刷新
     */
    public void addData(T t, boolean isNotify) {
        mListData.add(t);
        if (isNotify) {
            notifyDataSetChanged();
        }
    }


    /***
     *  添加bean到某一位置
     * @param t
     *   bean 集合
     * @param isNotify
     *     是否需要刷新
     */
    public void addData(int location, T t, boolean isNotify) {
        mListData.add(location, t);
        if (isNotify) {
            notifyDataSetChanged();
        }
    }

    public Handler handler;


    /***
     * 设置handler，从外部传入，方便适配器内部进行监听
     * @param handler
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    /***
     * 设置适配器全部数据集合
     * @param list
     */
    public void setListData(List<T> list) {
        if (mListData != null) {
            mListData.clear();
            mListData.addAll(list);
            notifyDataSetChanged();
        }
    }

    /***
     * 添加适配器部分数据集合
     * @param list
     */
    public void addListData(List<T> list) {
        if (list != null) {
            mListData.addAll(list);
            notifyDataSetChanged();
        }
    }

    /***
     * 添加适配器某一条数据
     * @param t
     */
    public void addData(T t) {
        if (t != null) {
            mListData.add(t);
            notifyDataSetChanged();
        }
    }

    /***
     * 添加适配器数据列表到某一位置
     * @param
     */
    public void addListData(int location, List<T> list) {
        if (list != null) {
            mListData.addAll(location, list);
            notifyDataSetChanged();
        }
    }

    /***
     * 清除适配器中全部数据
     * @param
     */
    public void clearListData() {
        mListData.clear();
        notifyDataSetChanged();
    }

    /***
     * 清除适配器中某一个位置的数据并刷新
     * @param
     */
    public void removeListData(int location) {
        mListData.remove(location);
        notifyDataSetChanged();
    }

    /***
     * 清除适配器中某一个位置的数据并刷新
     * @param
     */
    public void removeListDataNoUpdata(int location) {
        mListData.remove(location);
        notifyDataSetChanged();
    }
    /***
     * 清除适配器中某一个位置的数据
     * 不刷新
     * @param
     */
    public void removeData(int location) {
        mListData.remove(location);
    }


    /***
     * 根据下标集合清除适配器中内容
     * @param
     */
    public void removeListData(List<T> location) {
        for (int i = 0; i <location.size() ; i++) {
            mListData.remove(location.get(i));
        }
        notifyDataSetChanged();
    }

    /***
     * 清除适配器在某一条数据
     * @param
     */
    public void removeListData(T t) {
        mListData.remove(t);
        notifyDataSetChanged();
    }

    /***
     * 清除适配器在某一条数据
     * @param
     */
    public void removeListDataNoUpdata(T t) {
        mListData.remove(t);
    }

    /***
     * 添加一条数据进入适配器中，并进行刷新
     * @param position
     * @param t
     */
    public void setChangeData(int position, T t) {
        mListData.set(position, t);
        notifyDataSetChanged();
    }


    /***
     * 获取适配器中的全部内容
     * @return
     */
    public  List<T>  getmListData(){
        return mListData;
    }

    /***
     * 获取适配器中全部内容的数量
     * @return
     */
    @Override
    public int getCount() {
        return mListData.size();
    }

    /***
     * 获取适配器中某一条数据
     * @param position
     * @return
     */
    @Override
    public T getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public abstract View getView(int position, View convertView,
                                 ViewGroup parent);



}
