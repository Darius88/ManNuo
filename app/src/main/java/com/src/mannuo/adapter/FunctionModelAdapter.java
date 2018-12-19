package com.src.mannuo.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;


import com.src.mannuo.R;
import com.src.mannuo.base.BAdapter;
import com.src.mannuo.bean.FunctionModelBean;

/**
 * Created by ZM on 2018/12/18 0018.
 */

public class FunctionModelAdapter extends BAdapter<FunctionModelBean> {


    public FunctionModelAdapter(Activity act) {
        super(act);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_function_model, null);
            viewHolder.cb_function_model = view.findViewById(R.id.cb_function_model);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        FunctionModelBean bean = mListData.get(position);
        Log.e("", "==FunctionModelBean==" + bean.toString());
        if (bean.getImagesId() != -1) {
            viewHolder.cb_function_model.setBackgroundDrawable(mAct.getResources().getDrawable(bean.getImagesId().intValue()));
        }
        viewHolder.cb_function_model.setChecked(bean.isChecked());
        return view;

    }



    class ViewHolder {
        CheckBox cb_function_model;
    }


}
