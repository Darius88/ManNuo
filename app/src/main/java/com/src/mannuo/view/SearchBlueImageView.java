package com.src.mannuo.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

/**
 * Created by ZM on 2018/12/18 0018.
 */

public class SearchBlueImageView extends AppCompatImageView {
    private ObjectAnimator objectAnimator;

    public static final int STATE_PLAYING = 1;//正在播放
    public static final int STATE_STOP = 3;//停止
    public int state;

    public SearchBlueImageView(Context context) {
        super(context);
        init();
    }

    public SearchBlueImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchBlueImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        state = STATE_STOP;
        objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);//添加旋转动画，旋转中心默认为控件中点
        objectAnimator.setDuration(1000);//设置动画时间
        objectAnimator.setInterpolator(new LinearInterpolator());//动画时间线性渐变
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
    }

    @SuppressLint("NewApi")
    public void startSearchBlue() {
        if (state == STATE_STOP) {
            objectAnimator.start();//动画开始
            state = STATE_PLAYING;
        }
//        else if(state == STATE_PAUSE){
//            objectAnimator.resume();
//            state = STATE_PLAYING;
//        }else if(state == STATE_PLAYING){
//            objectAnimator.pause();//动画暂停
//            state = STATE_PAUSE;
//        }
    }

    public void stopSearchBlue() {
        objectAnimator.end();//动画结束
        state = STATE_STOP;
    }

    public boolean isSearching() {
        if (state == STATE_PLAYING) {
            return true;
        }
        return false;

    }
}
