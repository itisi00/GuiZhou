package com.itisi.guizhou.mvp.ui.main.chatsession;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootFragment;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.adapter.ChatSessionAdapter;
import com.itisi.guizhou.mvp.ui.chat.ChatActivity;
import com.itisi.guizhou.utils.ActivityUtil;
import com.itisi.guizhou.utils.SceneAnim;
import com.itisi.guizhou.utils.ToastUtil;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.List;

import butterknife.BindView;

/**
 * 聊天--会话界面
 */
public class ChatSessionFragment extends RootFragment<ChatSessionPresenter>
        implements ChatSessionContract.View
//        , SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnItemClickListener
        , BaseQuickAdapter.OnItemLongClickListener
        ,PullToRefreshView.OnRefreshListener
//        BaseQuickAdapter.UpFetchListener
        {


    @BindView(R.id.pullrefresh)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    ChatSessionAdapter mAdapter;
    private int pageSize = 10;//页大小
    private int pageIndex = 1;//页数
    private int totalCount = 0;//服务器返回的总的数量 有些接口可能没有
    private boolean isRefreshing = true;//刷新 还是 加载更多 加载成功以后 是追加还是替换


    public ChatSessionFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_session;
    }

    @Override
    protected void initEventAndData() {
        initView();
        initAdapter();
        initViewListener();
    }

    private void initView() {

    }

    private void initAdapter() {
        LinearLayoutManager layoutManager=
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        layoutManager.setItemPrefetchEnabled(false);

        mAdapter=new ChatSessionAdapter(R.layout.item_session);
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
        mPullToRefreshView.setOnRefreshListener(this);

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
    public void showContent(String msg) {

    }

    @Override
    public void loadSuccess(List<MeiZiBean> beanList) {
        if (isRefreshing){
            mAdapter.setNewData(beanList);
//            mSwipeRefreshLayout.setRefreshing(false);
            mPullToRefreshView.setRefreshing(false);
        }else{
            mAdapter.addData(beanList);
            mAdapter.loadMoreComplete();//加载完成  还可继续加载
//            mAdapter.loadMoreEnd();//数据全部加载完成 没有更多数据
//            mAdapter.loadMoreFail();//加载失败 点击重新加载
//            mAdapter.loadMoreEnd(true);//true is gone,false is visible,加载完成 不显示底部提示语
        }

    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ActivityUtil.getInstance().openActivity(getActivity(), ChatActivity.class, SceneAnim.AnimType.BOTTOM_IN);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.Success(position+":long");
        return true;
    }

    @Override
    public void onLoadMoreRequested() {
        mPullToRefreshView.setEnabled(false); //开始加载更多 进制下拉刷新
        //加载更多数据的代码*****************************
        pageIndex+=1;
        isRefreshing=false;//标明 此次是加载的
        loadData();
        mPullToRefreshView.setEnabled(true);//加载更多完成 开启下来刷新
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

}
