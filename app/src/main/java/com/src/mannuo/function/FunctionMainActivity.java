package com.src.mannuo.function;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.src.mannuo.R;
import com.src.mannuo.adapter.FunctionModelAdapter;
import com.src.mannuo.base.SimpleTitleActivity;
import com.src.mannuo.bean.FunctionModelBean;
import com.src.mannuo.eventbus.MainBatteryEventBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZM on 2018/12/18 0018.
 */

public class FunctionMainActivity extends SimpleTitleActivity implements AdapterView.OnItemClickListener {
    GridView mGvFunctionModel;

    ImageView iv_function_model;

    FunctionModelAdapter mAdapter;

    ImageView iv_function_back;

    boolean isOpen = false;

    private Integer[] images = {R.drawable.selector_ui_functon_1, R.drawable.selector_ui_functon_2, R.drawable.selector_ui_functon_3, R.drawable.selector_ui_functon_4,
            R.drawable.selector_ui_functon_5, R.drawable.selector_ui_functon_6, R.drawable.selector_ui_functon_7,
            R.drawable.selector_ui_functon_8, R.drawable.selector_ui_functon_9, -1, R.drawable.selector_ui_functon_10};

    private List<FunctionModelBean> mListImages;

    TextView tv_battery;

    @Override
    public int setContentView() {
        return R.layout.activity_function_main;
    }

    @Override
    protected void initView() {
        super.initView();
        mApp.addActivityToList(this);
        mListImages = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            FunctionModelBean bean = new FunctionModelBean();
            bean.setImagesId(images[i]);
            bean.setChecked(false);
            mListImages.add(bean);
        }
        iv_function_model = findViewById(R.id.iv_function_model);
        mGvFunctionModel = findViewById(R.id.gv_function_model);
        iv_function_back=findViewById(R.id.iv_function_back);
        tv_battery=findViewById(R.id.tv_battery);
        if(!TextUtils.isEmpty(mApp.getmElectric())){
            tv_battery.setText(mApp.getmElectric()+"%");
        }else{
            tv_battery.setText("---");
        }
        mAdapter = new FunctionModelAdapter(this);
        mGvFunctionModel.setAdapter(mAdapter);
        mAdapter.setListData(mListImages);
        iv_function_model.setOnClickListener(this);
        iv_function_back.setOnClickListener(this);
        mGvFunctionModel.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_function_model:
                if (!isOpen) {
                    int random = (int) (Math.random() * 9);
                    setModelStatus(true);
                    setAutoModel(random);
                } else {
                    for (int j = 0; j < mAdapter.getCount(); j++) {
                        mAdapter.getItem(j).setChecked(false);
                    }
                    mAdapter.notifyDataSetChanged();
                    setModelStatus(false);
                }
                break;
            case R.id.iv_function_back:
                finish();
                break;
        }
    }


    public void setAutoModel(int i) {
        for (int j = 0; j < mAdapter.getCount(); j++) {
            if (j == i) {
                mAdapter.getItem(j).setChecked(true);
            } else {
                mAdapter.getItem(j).setChecked(false);
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != 9) {
            setAutoModel(i);
            setModelStatus(true);
        }
    }


    public void setModelStatus(boolean ISOPEN) {
        if (ISOPEN) {
            iv_function_model.setImageResource(R.mipmap.mode1_close_p);
            isOpen = true;
        } else {
            iv_function_model.setImageResource(R.mipmap.mode1_close);
            isOpen = false;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainBatteryEventBean event) {
        if(!TextUtils.isEmpty(mApp.getmElectric())){
            tv_battery.setText(mApp.getmElectric()+"%");
        }else{
            tv_battery.setText("---");
        }
    }
}
