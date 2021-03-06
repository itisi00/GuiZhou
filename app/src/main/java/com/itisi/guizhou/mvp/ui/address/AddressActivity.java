package com.itisi.guizhou.mvp.ui.address;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.adapter.AddressAdapter;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.utils.ActivityUtil;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;

import java.util.List;

import butterknife.BindView;

/**
 * **********************
 * 功 能:收获地址列表
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/3 17:03
 * 修改人:itisi
 * 修改时间: 2017/8/3 17:03
 * 修改内容:itisi
 * *********************
 */
@UseRxBus
public class AddressActivity extends RootActivity<AddressPresenter>
        implements AddressContract.View
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnItemClickListener
        , BaseQuickAdapter.OnItemLongClickListener
        , SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    AddressAdapter mAdapter;
    private int pageSize = 10;//页大小
    private int pageIndex = 1;//页数
    private int totalCount = 0;//服务器返回的总的数量 有些接口可能没有
    private boolean isRefreshing = true;//刷新 还是 加载更多 加载成功以后 是追加还是替换



    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;

    }

    @Override
    protected void initEventAndData() {
//        setToolbarBackground(getResources().getColor(R.color.colorTransparent));
//        StatusBarUtil.setTranslucent(this, 0);//不加0 是半透明效果

        initView();
        initHeaderView();
        initAdapter();
        initViewListener();

        setToolbarMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.getInstance().openActivity(mActivity,AddressAddActivity.class);
            }
        });
    }

    private void initHeaderView() {
    }

    @Override
    protected String setToolbarTvTitle() {
        return "地址管理";
    }

    @Override
    protected String setToolbarMoreTxt() {
        return "新增";
    }

    private void initView() {
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);


    }


    private void initAdapter() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setItemPrefetchEnabled(false);

        mAdapter = new AddressAdapter(R.layout.item_address);

        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.addItemDecoration();
        mRecyclerView.setAdapter(mAdapter);

        //这里可能要开启 下拉动画
        loadData();


    }

    private void initViewListener() {
//        mPullToRefreshView.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);//下拉刷新 禁止上拉加载
        pageIndex = 1;
        isRefreshing = true;//标明 此次是刷新的
        loadData();
        mAdapter.setEnableLoadMore(true);//下拉刷新完成 开启上拉加载更多 这个可以放在代码里面
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.Success(position + ":click");

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.Success(position + ":long");
        return true;
    }

    @Override
    public void onLoadMoreRequested() {
//        mPullToRefreshView.setEnabled(false); //开始加载更多 进制下拉刷新
        mSwipeRefreshLayout.setEnabled(false);
        //加载更多数据的代码*****************************
        pageIndex += 1;
        isRefreshing = false;//标明 此次是加载的
        loadData();
//        mPullToRefreshView.setEnabled(true);//加载更多完成 开启下来刷新
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void showContent(String msg) {

    }

    @Override
    public void loadSuccess(List<MeiZiBean> beanList) {
        if (isRefreshing) {
            mAdapter.setNewData(beanList);
            mSwipeRefreshLayout.setRefreshing(false);
//            mPullToRefreshView.setRefreshing(false);
        } else {
            mAdapter.addData(beanList);
            mAdapter.loadMoreComplete();//加载完成  还可继续加载
//            mAdapter.loadMoreEnd();//数据全部加载完成 没有更多数据
//            mAdapter.loadMoreFail();//加载失败 点击重新加载
//            mAdapter.loadMoreEnd(true);//true is gone,false is visible,加载完成 不显示底部提示语
        }
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
        mPresenter.loadData(pageSize, pageIndex);
    }

}
