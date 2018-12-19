package com.src.mannuo.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by 张猛 on 2017/7/11
 */
public class BaseFragment extends Fragment implements View.OnClickListener{
    public SimpleTitleActivity mAct;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAct = (SimpleTitleActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onClick(View v) {
    }

    protected void startActivity(Class<?> c) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), c);
        startActivity(intent);
    }
}
