package com.src.mannuo.touch;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.src.mannuo.R;
import com.src.mannuo.base.SimpleTitleActivity;
import com.src.mannuo.eventbus.MainBatteryEventBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by zhangmeng on 2018/12/18.
 */
public class TouchActivity extends SimpleTitleActivity implements View.OnTouchListener {


    ImageView iv_touch_remote;

    ImageView iv_touch_back;

    TextView tv_battery;


    @Override
    public int setContentView() {
        return R.layout.activity_touch;
    }

    @Override
    protected void initView() {
        super.initView();
        mApp.addActivityToList(this);
        iv_touch_remote = findViewById(R.id.iv_touch_remote);
        iv_touch_back=findViewById(R.id.iv_touch_back);

        tv_battery=findViewById(R.id.tv_battery);
        if(!TextUtils.isEmpty(mApp.getmElectric())){
            tv_battery.setText(mApp.getmElectric()+"%");
        }else{
            tv_battery.setText("---");
        }
        iv_touch_remote.setOnTouchListener(this);
        iv_touch_back.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.iv_touch_back:
                finish();
                break;
        }

    }

    int downX;
    int downY;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) motionEvent.getRawX();
                downY = (int) motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int currentX = (int) motionEvent.getRawX();
                int currentY = (int) motionEvent.getRawY();
                int offx = currentX - downX;
                int offy = currentY - downY;
                iv_touch_remote.layout(iv_touch_remote.getLeft() + offx, iv_touch_remote.getTop() + offy, iv_touch_remote.getRight() + offx, iv_touch_remote.getBottom() + offy);
                downX = currentX;
                downY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }


        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
              iv_touch_remote.setX(motionEvent.getX());
                iv_touch_remote.setY(motionEvent.getY());
                break;
        }
        return true;

    }
}
