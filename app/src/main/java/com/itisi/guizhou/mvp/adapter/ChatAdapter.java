package com.itisi.guizhou.mvp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.itisi.guizhou.R;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;

import java.util.List;

/**
 * **********************
 * 功 能:聊天界面的适配器
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/6/19 18:37
 * 修改人:itisi
 * 修改时间: 2017/6/19 18:37
 * 修改内容:itisi
 * *********************
 */

public class ChatAdapter extends BaseQuickAdapter<MeiZiBean, BaseViewHolder> {


    public ChatAdapter(@Nullable List<MeiZiBean> data) {
        super(data);
        //1. 用来确定布局类型的字段
        setMultiTypeDelegate(new MultiTypeDelegate<MeiZiBean>() {
            @Override
            protected int getItemType(MeiZiBean chatSessionBean) {
                return chatSessionBean.getHeight();
            }
        });

        //2. 指定类型的布局
        getMultiTypeDelegate()
                .registerItemType(0, R.layout.item_msg_txt_receive)
                .registerItemType(1, R.layout.item_msg_txt_send)
                .registerItemType(2, R.layout.item_msg_img_receive)
                .registerItemType(3, R.layout.item_msg_img_send)
                .registerItemType(4, R.layout.item_msg_voice_receive)
                .registerItemType(5, R.layout.item_msg_voice_send)
                .registerItemType(6, R.layout.item_msg_video_receive)
                .registerItemType(7, R.layout.item_msg_video_send)
                .registerItemType(8, R.layout.item_msg_loc_receive)
                .registerItemType(9, R.layout.item_msg_loc_send)
        ;
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiZiBean item) {
        //这是给item中的子控件添加监听事件
        helper.addOnClickListener(R.id.tv_chat_box_txt_receive_content);
        //3.根据不同的类型 填充数据
        switch (helper.getItemViewType()){
//            case 0:
//                break;
//            case 1:
//                break;
        }
    }
}
