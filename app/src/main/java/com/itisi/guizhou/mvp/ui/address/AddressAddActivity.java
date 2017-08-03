package com.itisi.guizhou.mvp.ui.address;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;

import java.util.List;

/**
 * **********************
 * 功 能:新增地址
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/3 16:53
 * 修改人:itisi
 * 修改时间: 2017/8/3 16:53
 * 修改内容:itisi
 * *********************
 */
@UseRxBus
public class AddressAddActivity extends RootActivity<AddressPresenter>
        implements AddressContract.View {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_add;

    }

    @Override
    protected void initEventAndData() {
//        setToolbarBackground(getResources().getColor(R.color.colorTransparent));
//        StatusBarUtil.setTranslucent(this, 0);//不加0 是半透明效果

        initView();
        initHeaderView();
        initAdapter();
        initViewListener();
        setToolbarTvTitle();
        setToolbarMoreTxt();
    }

    private void initHeaderView() {
    }

    @Override
    protected String setToolbarTvTitle() {
        return "新增地址";
    }

    private void initView() {

    }


    private void initAdapter() {


    }

    private void initViewListener() {

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

    private void loadData() {
    }
}
