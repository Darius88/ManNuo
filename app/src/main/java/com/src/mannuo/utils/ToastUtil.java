/**
 * 
 */
package com.src.mannuo.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by 张猛 on 2017/7/11
 * 关于Toast提示的工具类
 */
public class ToastUtil {

    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    private static Toast toast;
    private static Handler handler = new Handler();

    private static Runnable run = new Runnable() {
        public void run() {
            toast.cancel();
        }
    };

    private static void toast(Context ctx, CharSequence msg, int duration) {
        handler.removeCallbacks(run);
        // handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时长
        switch (duration) {
            case LENGTH_SHORT:// Toast.LENGTH_SHORT值为0，对应的持续时间大概为1s
                duration = 1000;
                break;
            case LENGTH_LONG:// Toast.LENGTH_LONG值为1，对应的持续时间大概为3s
                duration = 3000;
                break;
            default:
                break;
        }
        if (null != toast) {
            toast.setText(msg);
        } else {
            toast = Toast.makeText(ctx, msg, duration);
        }
        handler.postDelayed(run, duration);
        toast.show();
    }

    /**
     * 弹出Toast
     *
     * @param ctx
     *            弹出Toast的上下文
     * @param msg
     *            弹出Toast的内容
     */
    public static void show(Context ctx, CharSequence msg)
            throws NullPointerException {
        if (null == ctx) {
            throw new NullPointerException("The ctx is null!");
        }
        toast(ctx, msg, LENGTH_SHORT);
    }

    /**
     * 弹出Toast
     *
     * @param ctx
     *            弹出Toast的上下文
     *            弹出Toast的内容的资源ID
     */
    public static void show(Context ctx, int resId)
            throws NullPointerException {
        if (null == ctx) {
            throw new NullPointerException("The ctx is null!");
        }
        toast(ctx, ctx.getResources().getString(resId), LENGTH_SHORT);
    }



}
