package com.src.mannuo.menu;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.src.mannuo.R;
import com.src.mannuo.base.SimpleTitleActivity;
import com.src.mannuo.eventbus.MainBatteryEventBean;
import com.src.mannuo.function.FunctionMainActivity;
import com.src.mannuo.touch.TouchActivity;
import com.src.mannuo.video.VideoPlayActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ZM on 2018/12/18 0018.
 */

public class MainMenuActivity extends SimpleTitleActivity {

    ImageView mIvMenuMusic;
    ImageView mIvMenuVideo;
    ImageView mIvMenuFunction;
    ImageView mIvMenuTouch;
    ImageView mIvMenuBack;
    TextView tv_battery;

    @Override
    public int setContentView() {
        return R.layout.activity_main_menu;
    }

    @Override
    protected void initView() {
        super.initView();
        mApp.addActivityToList(this);
        mIvMenuBack = findViewById(R.id.iv_menu_back);
        mIvMenuMusic = findViewById(R.id.iv_menu_music);
        mIvMenuVideo = findViewById(R.id.iv_menu_movie);
        mIvMenuFunction = findViewById(R.id.iv_menu_function);
        mIvMenuTouch = findViewById(R.id.iv_menu_touch);
        tv_battery = findViewById(R.id.tv_battery);
        if (!TextUtils.isEmpty(mApp.getmElectric())) {
            tv_battery.setText(mApp.getmElectric() + "%");
        } else {
            tv_battery.setText("---");
        }
        mIvMenuBack.setOnClickListener(this);
        mIvMenuMusic.setOnClickListener(this);
        mIvMenuVideo.setOnClickListener(this);
        mIvMenuFunction.setOnClickListener(this);
        mIvMenuTouch.setOnClickListener(this);

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
        if (!TextUtils.isEmpty(mApp.getmElectric())) {
            tv_battery.setText(mApp.getmElectric() + "%");
        } else {
            tv_battery.setText("---");
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_menu_back:
                finish();
                break;
            case R.id.iv_menu_music:
                break;
            case R.id.iv_menu_movie:
                startActivity(VideoPlayActivity.class);
                break;
            case R.id.iv_menu_function:
                startActivity(FunctionMainActivity.class);
                break;
            case R.id.iv_menu_touch:
                startActivity(TouchActivity.class);
                break;
        }
    }
}
