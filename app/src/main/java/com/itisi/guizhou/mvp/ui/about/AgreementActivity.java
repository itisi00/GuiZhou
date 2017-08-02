package com.itisi.guizhou.mvp.ui.about;

import android.view.View;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;

import java.util.List;

/**
 * **********************
 * 功 能:服务协议
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/2 10:05
 * 修改人:itisi
 * 修改时间: 2017/8/2 10:05
 * 修改内容:itisi
 * *********************
 */
@UseRxBus
public class AgreementActivity extends RootActivity<AboutPresenter>
        implements AboutContract.View
        , View.OnClickListener {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showContent(String msg) {

    }

    @Override
    public void loadSuccess(List<MeiZiBean> beanList) {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateSuccess() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);

    }
}
