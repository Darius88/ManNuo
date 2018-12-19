package com.src.mannuo.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.src.mannuo.R;


/**
 * Created by zhangmeng on 2017/12/28.
 */
public class LoadingDialog extends Dialog {


    private Context context = null;

    private static LoadingDialog customProgressDialog = null;


    private static ImageView iv_load_result;// 加载的结果图标显示
    private static TextView tv_load;// 加载的文字展示
    private static ProgressBar pb_loading;// 加载中的图片
    private final int LOAD_SUCC = 0x001;
    private final int LOAD_FAIL = 0x002;
    private final int LOAD_SHOW = 0x003;


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case LOAD_SHOW:
                    show();
                    break;
                case LOAD_SUCC:
                    dismiss();
                    break;
                case LOAD_FAIL:
                    dismiss();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }


    public static LoadingDialog createDialog(Context context) {
        customProgressDialog = new LoadingDialog(context, R.style.myDialogTheme2);
        customProgressDialog.setContentView(R.layout.commom_loading_layout);
        iv_load_result = (ImageView) customProgressDialog.findViewById(R.id.iv_load_result);
        tv_load = (TextView) customProgressDialog.findViewById(R.id.tv_load);
        pb_loading = (ProgressBar) customProgressDialog.findViewById(R.id.pb_loading);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        customProgressDialog.setCancelable(false);
        return customProgressDialog;
    }

    public void Show() {
        pb_loading.setVisibility(View.VISIBLE);
        iv_load_result.setVisibility(View.GONE);
        tv_load.setText("加载中");
        mHandler.sendEmptyMessage(LOAD_SHOW);
    }


    public void  resumeText(){
        tv_load.setText("加载中");
    }


    public void Show(String str) {
        pb_loading.setVisibility(View.VISIBLE);
        iv_load_result.setVisibility(View.GONE);
        tv_load.setText(str);
        mHandler.sendEmptyMessage(LOAD_SHOW);
    }

    public void setText(String str) {
        tv_load.setText(str);
    }


    public void dimissSuc() {// 加载成功
        pb_loading.setVisibility(View.GONE);
        iv_load_result.setVisibility(View.VISIBLE);
        tv_load.setText("加载成功");
        iv_load_result.setImageResource(R.mipmap.load_suc_icon);
        mHandler.sendEmptyMessageDelayed(LOAD_SUCC, 1000);
    }

    public  void  Dismiss(){
        mHandler.sendEmptyMessageDelayed(LOAD_FAIL, 100);
    }

    public void dimissSuc(String str) {// 加载成功
        pb_loading.setVisibility(View.GONE);
        iv_load_result.setVisibility(View.VISIBLE);
        tv_load.setText(str);
        iv_load_result.setImageResource(R.mipmap.load_suc_icon);
        mHandler.sendEmptyMessageDelayed(LOAD_SUCC, 1000);
    }

    public void dimissFail() {// 加载失败
        pb_loading.setVisibility(View.GONE);
        iv_load_result.setVisibility(View.VISIBLE);
        tv_load.setText("加载失败");
        iv_load_result.setImageResource(R.mipmap.load_fail_icon);
        mHandler.sendEmptyMessageDelayed(LOAD_FAIL, 1000);
    }


    public void dimissFail(String  str) {// 加载失败
        pb_loading.setVisibility(View.GONE);
        iv_load_result.setVisibility(View.VISIBLE);
        tv_load.setText(str);
        iv_load_result.setImageResource(R.mipmap.load_fail_icon);
        mHandler.sendEmptyMessageDelayed(LOAD_FAIL, 1000);
    }
}
