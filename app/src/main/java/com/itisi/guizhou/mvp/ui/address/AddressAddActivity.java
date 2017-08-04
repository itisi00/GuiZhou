package com.itisi.guizhou.mvp.ui.address;

import android.view.View;
import android.widget.TextView;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;

import java.util.List;

import butterknife.BindView;

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
        implements AddressContract.View
        , View.OnClickListener {


    @BindView(R.id.tv_address_select)
    TextView tv_address_select;

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

        tv_address_select.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address_select:
//                int row = mPresenter.test_insert();
//                mPresenter.test_select();
//                mPresenter.test_update();
                mPresenter.test_delete();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.closeDB();
    }
}
