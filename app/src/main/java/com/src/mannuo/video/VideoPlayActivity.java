package com.src.mannuo.video;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.src.mannuo.R;
import com.src.mannuo.adapter.VideoListAdapter;
import com.src.mannuo.base.SimpleTitleActivity;
import com.src.mannuo.bean.VideoBean;
import com.src.mannuo.eventbus.MainBatteryEventBean;
import com.src.mannuo.utils.SearchFileUtil;
import com.src.mannuo.utils.ToastUtil;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangmeng on 2018/12/19.
 */

public class VideoPlayActivity extends SimpleTitleActivity implements AdapterView.OnItemClickListener {

    List<VideoBean> mVideoInfo;
    NiceVideoPlayer mNiceVideoPlayer;
    MyVideoPlayerController controller;
    ImageView iv_video_back;
    TextView tv_battery;

    ListView mLvVideo;

    VideoListAdapter mAdapter;

    @Override
    public int setContentView() {
        return R.layout.activity_layout_videoplay;
    }

    @Override
    protected void initView() {
        super.initView();
        mApp.addActivityToList(this);
        mVideoInfo = new ArrayList<>();
        mNiceVideoPlayer = findViewById(R.id.nplayer_video);
        iv_video_back = findViewById(R.id.iv_video_back);
        tv_battery = findViewById(R.id.tv_battery);
        mLvVideo = findViewById(R.id.lv_video);
        mAdapter = new VideoListAdapter(this);
        mLvVideo.setAdapter(mAdapter);
        if (!TextUtils.isEmpty(mApp.getmElectric())) {
            tv_battery.setText(mApp.getmElectric() + "%");
        } else {
            tv_battery.setText("---");
        }
        iv_video_back.setOnClickListener(this);
        mLvVideo.setOnItemClickListener(this);
        controller = new MyVideoPlayerController(VideoPlayActivity.this);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
        mNiceVideoPlayer.setController(controller);
        mDialog.Show("正在搜索视频文件");
        new Thread() {
            @Override
            public void run() {
                //这里写入子线程需要做的工作
                mVideoInfo = SearchFileUtil.getList(VideoPlayActivity.this, getResources().getDimensionPixelOffset(R.dimen.y500));
                handler.sendEmptyMessage(0);
            }
        }.start();


    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainBatteryEventBean event) {
        if (!TextUtils.isEmpty(mApp.getmElectric())) {
            tv_battery.setText(mApp.getmElectric() + "%");
        } else {
            tv_battery.setText("---");
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Log.e("====", "==mVideoInfo==" + mVideoInfo.toString());
                    mDialog.Dismiss();
                    if (null != mVideoInfo && mVideoInfo.size() != 0) {
                        mVideoInfo.get(0).setSelected(true);
                        mAdapter.setListData(mVideoInfo);
                        mNiceVideoPlayer.setUp(mVideoInfo.get(0).getPath(), null);
                        controller.setTitle(mVideoInfo.get(0).getTitle());
                        controller.imageView().setImageBitmap(mVideoInfo.get(0).getImage());
                    } else {
                        ToastUtil.show(VideoPlayActivity.this, "暂未扫描到手机中的视频文件");
                    }

                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_video_back:
                finish();
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        for (int j = 0; j < mVideoInfo.size(); j++) {
            if (j == i) {
                mVideoInfo.get(j).setSelected(true);
            } else {
                mVideoInfo.get(j).setSelected(false);
            }
        }
        mAdapter.notifyDataSetChanged();
        controller.reset();
        mNiceVideoPlayer.releasePlayer();

        mNiceVideoPlayer.setUp(mVideoInfo.get(i).getPath(), null);
        controller.setTitle(mVideoInfo.get(i).getTitle());
        controller.imageView().setImageBitmap(mVideoInfo.get(i).getImage());


        mNiceVideoPlayer.start();

    }
}
