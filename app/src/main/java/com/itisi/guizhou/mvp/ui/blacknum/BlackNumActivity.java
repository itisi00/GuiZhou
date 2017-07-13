package com.itisi.guizhou.mvp.ui.blacknum;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.yalantis.phoenix.PullToRefreshView;

import butterknife.BindView;

public class BlackNumActivity extends RootActivity<BlackNumPresenter> implements BlackNumContract.View
        , View.OnClickListener {

    @BindView(R.id.pullrefresh)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int pageSize = 10;
    private int pageIndex = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_black_num;
    }

    @Override
    protected void initEventAndData() {
        initRepresh();
    }
    /**
     * 下拉刷新
     */
    private void initRepresh() {
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView .setRefreshing(false);
                    }
                },3000);
//                mPresenter.loadData(pageSize, pageIndex);
            }
        });
    }



    @Override
    public void onClick(View view) {

    }

    @Override
    public void testShowView(String smg) {

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
