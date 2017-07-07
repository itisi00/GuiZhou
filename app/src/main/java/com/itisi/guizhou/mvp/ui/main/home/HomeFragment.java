package com.itisi.guizhou.mvp.ui.main.home;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootFragment;
import com.itisi.guizhou.mvp.ui.main.temp.TestRxBusActivity;
import com.itisi.guizhou.utils.ToastUtil;
import com.yalantis.phoenix.PullToRefreshView;

import butterknife.BindView;


/**
 * 首页-主页
 */
public class HomeFragment extends RootFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.tv_test1)
    TextView tv_test;
    @BindView(R.id.pullrefresh)
    PullToRefreshView mPullToRefreshView;

    private int pageSize=10;
    private int pageIndex=1;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initEventAndData() {

        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mPresenter.loadData();
                startActivity(new Intent(getFragmentComponent().getActivity(), TestRxBusActivity.class));
            }
        });
        initRepresh();
    }

    /**
     * 下拉刷新
     */
    private void initRepresh() {
        mPullToRefreshView .setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mPullToRefreshView .postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Logger.i("刷新数据");
//                        mPullToRefreshView .setRefreshing(false);
//                    }
//                },3000);
                mPresenter.loadData(pageSize,pageIndex);
            }
        });
    }




    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
    }



    @Override
    public void showContent(String msg) {
        ToastUtil.Success(msg);
        mPullToRefreshView .setRefreshing(false);
    }

    @Override
    public void useNightMode(boolean isNight) {

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
}
