package com.itisi.guizhou.mvp.ui.main.leisure;


import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootFragment;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.ui.adapter.MeiZiAdapter;
import com.itisi.guizhou.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeiZiFragment extends RootFragment<MeiZiPresenter>
            implements MeiZiContract.View
            , SwipeRefreshLayout.OnRefreshListener
            ,BaseQuickAdapter.RequestLoadMoreListener
            , BaseQuickAdapter.OnItemClickListener
           , BaseQuickAdapter.OnItemLongClickListener {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    MeiZiAdapter mAdapter;
    private int pageSize = 10;//页大小
    private int pageIndex = 1;//页数
    private int totalCount=0;//服务器返回的总的数量 有些接口可能没有
    private boolean isRefreshing=true;//刷新 还是 加载更多 加载成功以后 是追加还是替换

    public MeiZiFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mei_zi;
    }

    @Override
    protected void initEventAndData() {
        initView();
        initAdapter();
        initViewListener();
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
//        LinearLayoutManager layoutManager=
//                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        StaggeredGridLayoutManager layoutManager= new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        //fix issue #52 https://github.com/codeestX/GeekNews/issues/52
        layoutManager.setItemPrefetchEnabled(false);

        mAdapter=new MeiZiAdapter(R.layout.item_meizi);
//        mAdapter.setOnItemClickListener(this);
//        mAdapter.setOnItemLongClickListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //这里可能要开启 下拉动画
        loadData();

//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtil.Success("pos:"+position);
//            }
//        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.Success("333");
            }
        });

    }


    private void initViewListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
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

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showContent(String msg) {

    }

    @Override
    public void loadSuccess(List<MeiZiBean> beanList) {
        if (isRefreshing){
            mAdapter.setNewData(beanList);
            mSwipeRefreshLayout.setRefreshing(false);
        }else{
            mAdapter.addData(beanList);
            mAdapter.loadMoreComplete();//加载完成  还可继续加载
//            mAdapter.loadMoreEnd();//数据全部加载完成 没有更多数据
//            mAdapter.loadMoreFail();//加载失败 点击重新加载
//            mAdapter.loadMoreEnd(true);//true is gone,false is visible,加载完成 不显示底部提示语
        }


    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);//下拉刷新 禁止上拉加载
        pageIndex=1;
        isRefreshing=true;//标明 此次是刷新的
        loadData();
        mAdapter.setEnableLoadMore(true);//下拉刷新完成 开启上拉加载更多 这个可以放在代码里面
    }

    private void loadData(){
        mPresenter.loadData(pageSize,pageIndex);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false); //开始加载更多 进制下拉刷新
        //加载更多数据的代码*****************************
        pageIndex+=1;
        isRefreshing=false;//标明 此次是加载的
        loadData();
        mSwipeRefreshLayout.setEnabled(true);//加载更多完成 开启下来刷新

    }

    // TODO: 2017/7/13  点击事件  不行

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.Success(position+"");
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.Success(position+":long");
        return true;
    }
}
