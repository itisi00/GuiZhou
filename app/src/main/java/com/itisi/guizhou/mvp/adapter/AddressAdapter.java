package com.itisi.guizhou.mvp.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itisi.guizhou.R;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/3 17:45
 * 修改人:itisi
 * 修改时间: 2017/8/3 17:45
 * 修改内容:itisi
 * *********************
 */

public class AddressAdapter extends BaseQuickAdapter<MeiZiBean, BaseViewHolder> {

    public AddressAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiZiBean item) {
//         view_circle_primary   view_circle tv_name tv_phone tv_address
//        helper.setVisible(R.id.view_circle_gray,false);
        int position = helper.getLayoutPosition();
        if (position==0){
            helper.setVisible(R.id.view_circle_gray,false);
            helper.setVisible(R.id.view_circle_primary,true);
        }else {
            helper.setVisible(R.id.view_circle_gray,true);
            helper.setVisible(R.id.view_circle_primary,false);
        }
        helper.setText(R.id.tv_name,"诸葛武侯"+position);
        helper.setText(R.id.tv_phone,"1828517492"+position);
        helper.setText(R.id.tv_address,"贵州省贵阳市花果园一期十三栋6单元222"+position);

    }
}
