package com.itisi.guizhou.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itisi.guizhou.R;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;

/**
 * **********************
 * 功 能:日程
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/24 17:54
 * 修改人:itisi
 * 修改时间: 2017/7/24 17:54
 * 修改内容:itisi
 * *********************
 */

public class AgendaAdapter extends BaseQuickAdapter<MeiZiBean, BaseViewHolder> {

    public AgendaAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiZiBean item) {
        int position = helper.getAdapterPosition();
        helper.getPosition();
//        iv_cover tv_title  tv_address tv_time tv_remind_count
//        view_circle_accent view_circle_primary view_circle_gray
        if (position<=3){
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.colorAccent));
            helper.setVisible(R.id.view_circle_accent,true);
            helper.setVisible(R.id.tv_remind_count,true);
            helper.setText(R.id.tv_remind_count,"已提醒"+(4-position)+"次");
        }else if(position<=6&&position>3){
            helper.setVisible(R.id.view_circle_primary,true);


        }else if(position>6){
            helper.setVisible(R.id.view_circle_gray,true);

        }
    }
}
