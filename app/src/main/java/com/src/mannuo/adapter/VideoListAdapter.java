package com.src.mannuo.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.src.mannuo.R;
import com.src.mannuo.base.BAdapter;
import com.src.mannuo.bean.VideoBean;
import com.src.mannuo.utils.SearchFileUtil;

/**
 * Created by zhangmeng on 2018/12/19.
 */

public class VideoListAdapter extends BAdapter<VideoBean> {

    public VideoListAdapter(Activity act) {
        super(act);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_video_list, null);
            viewHolder.layout_video = view.findViewById(R.id.layout_video);
            viewHolder.iv_video_bg = view.findViewById(R.id.iv_video_bg);
            viewHolder.tv_video_title = view.findViewById(R.id.tv_video_title);
            viewHolder.tv_video_time = view.findViewById(R.id.tv_video_time);
            viewHolder.tv_video_size = view.findViewById(R.id.tv_video_size);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        VideoBean bean = mListData.get(position);
        if (bean.isSelected()) {
            viewHolder.layout_video.setBackgroundColor(mAct.getResources().getColor(R.color.connect_gray_text));
        } else {
            viewHolder.layout_video.setBackgroundColor(mAct.getResources().getColor(R.color.translate));
        }
        if (null != bean.getImage()) {
            viewHolder.iv_video_bg.setImageBitmap(bean.getImage());
        } else {
            viewHolder.iv_video_bg.setImageResource(R.drawable.img_default);
        }
        viewHolder.tv_video_title.setText(bean.getTitle());
        viewHolder.tv_video_time.setText(SearchFileUtil.TimeToDate(bean.getDuration()));
        viewHolder.tv_video_size.setText(SearchFileUtil.getNetFileSizeDescription(bean.getSize()));

        return view;
    }

    class ViewHolder {
        LinearLayout layout_video;
        ImageView iv_video_bg;
        TextView tv_video_title;
        TextView tv_video_time;
        TextView tv_video_size;


    }
}
