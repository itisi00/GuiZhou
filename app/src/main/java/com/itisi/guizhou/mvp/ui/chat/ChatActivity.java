package com.itisi.guizhou.mvp.ui.chat;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.ui.adapter.ChatAdapter;
import com.itisi.guizhou.mvp.ui.chat.chatfragment.EmotionMainFragment;
import com.itisi.guizhou.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class ChatActivity extends RootActivity<ChatPresenter>
        implements ChatContract.View
        , View.OnClickListener
        , BaseQuickAdapter.OnItemClickListener
        , BaseQuickAdapter.OnItemLongClickListener,
        BaseQuickAdapter.UpFetchListener {

    public String toid;//对方的id
    public String tonick;//对方的昵称
    public String toheader; //对方的头像地址

    private String minid;//自己的id
    private String minnick;//自己的昵称
    private String minheader;//自己的头像

    private EmotionMainFragment emotionMainFragment;//表情面板

    private Button mBtnSend;//发送按钮
    private ImageView mIvEmotion;//表情按钮
    private ImageView mIvExtend;//扩展菜单按钮
    private EditText mEditText;//内容输入框
    private View mInputView;

    @BindView(R.id.ll_parent)
    LinearLayout mLinearLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    ChatAdapter mAdapter; // TODO: 2017/7/19  聊天的适配器
    private int pageSize = 10;//页大小
    private int pageIndex = 1;//页数
    private int totalCount = 0;//服务器返回的总的数量 有些接口可能没有
    private boolean isRefreshing = true;//刷新 还是 加载更多 加载成功以后 是追加还是替换


    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
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
        //这里可能要开启 下拉动画
        loadData();

    }

    private void initViewListener() {
        initEmotionMainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mInputView == null) {
            mInputView = getSupportFragmentManager().findFragmentById(R.id.fl_pannel).getView();
            mBtnSend = (Button) mInputView.findViewById(R.id.bar_btn_send);
            mIvEmotion = (ImageView) mInputView.findViewById(R.id.bar_iv_emotion);
            mIvExtend = (ImageView) mInputView.findViewById(R.id.bar_iv_extend);
            mEditText = (EditText) mInputView.findViewById(R.id.bar_edit_text);
            mEditText.addTextChangedListener(new EditTextChangeListener());//文本变化监听器
            mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
//                        recyclerSmoothScrollToBottom();
                    }
                }
            });
            mBtnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    sendTxtMessage();

                }
            });

            TextView tv_extend_picture = (TextView) mInputView.findViewById(R.id.tv_extend_picture);//照片
            TextView tv_extend_camera = (TextView) mInputView.findViewById(R.id.tv_extend_camera);//拍照

//            tv_extend_picture.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    openPhoto();
//                }
//            });
//
//            tv_extend_camera.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    openCamera();
//                }
//            });
        }
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void showContent(String msg) {

    }

    @Override
    public void loadSuccess(List<MeiZiBean> beanList) {
        if (mAdapter == null) {
            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            layoutManager.setItemPrefetchEnabled(false);
            mAdapter = new ChatAdapter(beanList);
            mAdapter.setOnItemClickListener(this);
            mAdapter.setOnItemLongClickListener(this);
            mAdapter.setUpFetchListener(this);
            mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
        if (isRefreshing) {
//            mAdapter.setNewData(beanList);
//            mSwipeRefreshLayout.setRefreshing(false);
        } else {
//            mAdapter.addData(beanList);
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


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.Success(position + ":click");

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.Success(position + ":long");
        return true;
    }


    private void loadData() {
        mPresenter.loadData(pageSize, pageIndex);
    }

    @Override
    public void onUpFetch() {
        loadData();
        // TODO: 2017/7/19 不知道干啥的
//        mAdapter.setStartUpFetchPosition(1);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//          /* 判断是否拦截返回键操作
//                */
//        if (!emotionMainFragment.isInterceptBackPress()) {
//            super.onBackPressed();
//        }
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        EaseMobUtil.removeReceiveMessageListener(mReceiveLisener);
    }


    public void recyclerSmoothScrollToBottom(){

    }
    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment() {
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, false);
        //替换fragment   //创建修改实例
        emotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class, bundle);
        emotionMainFragment.bindToContentView(mLinearLayout);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_pannel, emotionMainFragment);
        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();

    }


    /**
     * 输入框内容变化监听器
     */
    class EditTextChangeListener implements TextWatcher {
        /**
         * 编辑框内容发生改变之前的会回调
         *
         * @param s
         * @param start
         * @param count
         * @param after
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        /**
         * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
         * 我们可以在这里实时地 通过搜索匹配用户的输入
         *
         * @param charSequence
         * @param start
         * @param before
         * @param count
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if (TextUtils.isEmpty(charSequence.toString())) {
                mBtnSend.setVisibility(View.GONE);
                mIvExtend.setVisibility(View.VISIBLE);

            } else {
                mBtnSend.setVisibility(View.VISIBLE);
                mIvExtend.setVisibility(View.GONE);

            }
        }

        /**
         * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
         *
         * @param s
         */
        @Override
        public void afterTextChanged(Editable s) {
        }


    }


}
