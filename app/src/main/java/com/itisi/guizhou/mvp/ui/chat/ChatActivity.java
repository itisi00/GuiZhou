package com.itisi.guizhou.mvp.ui.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMMessage;
import com.itisi.guizhou.R;
import com.itisi.guizhou.app.Constants;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.adapter.ChatAdapter;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.ui.chat.chatfragment.EmotionMainFragment;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.ui.RxGalleryListener;
import cn.finalteam.rxgalleryfinal.ui.base.IMultiImageCheckedListener;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;
import cn.finalteam.rxgalleryfinal.utils.MediaScanner;

@UseRxBus
public class ChatActivity extends RootActivity<ChatPresenter>
        implements ChatContract.View
        , View.OnClickListener
        , BaseQuickAdapter.OnItemClickListener
        , BaseQuickAdapter.OnItemLongClickListener
        , BaseQuickAdapter.OnItemChildClickListener
        , BaseQuickAdapter.UpFetchListener {

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

    private InputMethodManager mInputMethodManager;//软键盘管理
    private MessageReceiveLisener mReceiveLisener;//环信消息监听器


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
        initGalleryListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initEmotionViewAndListener();

    }

    private void initView() {
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setPath();
    }

    private void initEmotionViewAndListener() {
        if (mInputView == null) {
            mInputView = getSupportFragmentManager().findFragmentById(R.id.fl_pannel).getView();
            mBtnSend = (Button) mInputView.findViewById(R.id.bar_btn_send);
            mIvEmotion = (ImageView) mInputView.findViewById(R.id.bar_iv_emotion);
            mIvExtend = (ImageView) mInputView.findViewById(R.id.bar_iv_extend);
            mEditText = (EditText) mInputView.findViewById(R.id.bar_edit_text);
            mEditText.addTextChangedListener(new EditTextChangeListener());//文本变化监听器

            //只为获取高度 软键盘
//            mEditText.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mEditText.setFocusable(true);
//                    mEditText.setFocusableInTouchMode(true);
//                    mEditText.requestFocus();
//                    mInputMethodManager.showSoftInput(mEditText,InputMethodManager.SHOW_FORCED);
//
//                    mInputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0); //强制隐藏键盘
//                }
//            },200);
            mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    //暂时不需要 因为QQ 也没有在输入框获取焦点的时候 进行滚动
//                    if (hasFocus) {
//                       recyclerSmoothScrollToBottom();
//                    }
                }
            });
            mBtnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    sendTxtMessage();
                    ToastUtil.Success(mEditText.getText().toString());
                }
            });

            TextView tv_extend_picture = (TextView) mInputView.findViewById(R.id.tv_extend_picture);//照片
            TextView tv_extend_camera = (TextView) mInputView.findViewById(R.id.tv_extend_camera);//拍照

            tv_extend_picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPhoto();
                }
            });

            tv_extend_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCamera();
                }
            });
        }
    }

    private void initAdapter() {
        //这里可能要开启 下拉动画
        loadData();

    }

    private void initViewListener() {
        initEmotionMainFragment();
        mReceiveLisener = new MessageReceiveLisener();
        EaseMobUtil.addReceiveMessageListener(mReceiveLisener);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                closeSoftInput();
                emotionMainFragment.hideEmotionLayoutoOrExtenLayout();
            }
        });
    }

    /**
     * 设置 照片路径 和 裁剪路径
     * //裁剪会自动生成路径；也可以手动设置裁剪的路径；
     */
    private void setPath() {
        RxGalleryFinalApi.setImgSaveRxSDCard(Constants.PATH_GALLERY);
        RxGalleryFinalApi.setImgSaveRxCropSDCard(Constants.PATH_GALLERY_CROP);
    }

    /**
     * 初始化 rxgallery 的相关事件
     */
    private void initGalleryListener() {
//        多选事件的回调
        RxGalleryListener
                .getInstance()
                .setMultiImageCheckedListener(
                        new IMultiImageCheckedListener() {
                            @Override
                            public void selectedImg(Object t, boolean isChecked) {
//                                Toast.makeText(getBaseContext(), isChecked ? "itisi选中" : "itisi取消选中", Toast.LENGTH_SHORT).show();
                                //这个主要点击或者按到就会触发，所以不建议在这里进行Toast
                            }

                            @Override
                            public void selectedImgMax(Object t, boolean isChecked, int maxSize) {
//                                Toast.makeText(getBaseContext(), "itisi你最多只能选择" + maxSize + "张图片", Toast.LENGTH_SHORT).show();
                                ToastUtil.Info("你最多只能选择" + maxSize + "张图片");
                            }
                        });
//        裁剪图片的回调
        RxGalleryListener
                .getInstance()
                .setRadioImageCheckedListener(
                        new IRadioImageCheckedListener() {
                            @Override
                            public void cropAfter(Object t) {
//                                Toast.makeText(getBaseContext(), "itisi"+t.toString(), Toast.LENGTH_SHORT).show();
                                Logger.i("裁剪后的图片:" + t.toString());
                            }

                            @Override
                            public boolean isActivityFinish() {
                                return false;
                            }
                        });

    }

    @Override
    public void onResume() {
        super.onResume();
        //选择调用：裁剪图片的回调
        RxGalleryFinalApi.cropActivityForResult(this, new MediaScanner.ScanCallback() {
            @Override
            public void onScanCompleted(String[] images) {
                Logger.i(String.format("裁剪图片成功,图片裁剪后存储路径xxyy:%s", images[0]));
            }
        });

    }

    /**
     * 打开相册 选择照片
     */
    private void openPhoto() {
        //打开相册

//        RxGalleryFinal
//                .with(ChatActivity.this)
//                .image()
//                .radio()
//                .cropAspectRatioOptions(0, new AspectRatio("3:3", 30, 10))
//                .crop()
//                .imageLoader(ImageLoaderType.FRESCO)
//                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
//                    @Override
//                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
//                        Toast.makeText(getBaseContext(), "选中了图片路径：" + imageRadioResultEvent.getResult().getOriginalPath(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .openGallery();

        //自定义方法的单选
//        RxGalleryFinal
//                .with(this)
//                .image()
//                .radio()
//                .crop()
//                .imageLoader(ImageLoaderType.GLIDE)
//                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
//                    @Override
//                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
//                        //图片选择结果
//                        ToastUtil.Success(imageRadioResultEvent.getResult().getOriginalPath());
//                    }
//                })
//                .openGallery();

//        //使用默认的参数
//        RxGalleryFinalApi
//                .getInstance(ChatActivity.this)
//                .setType(RxGalleryFinalApi.SelectRXType.TYPE_IMAGE,3) // 好像没用 这个设置多选 两个参数的
//                .setImageMultipleResultEvent(
//                        new RxBusResultSubscriber<ImageMultipleResultEvent>() {
//                            @Override
//                            protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
//                                Logger.i("多选图片的回调");
//                                Logger.i(imageMultipleResultEvent.getResult().size()+"");
//                            }
//                        }).open();


//        //使用自定义的参数
//        RxGalleryFinalApi
//                .getInstance(ChatActivity.this)
//                .setType(RxGalleryFinalApi.SelectRXType.TYPE_IMAGE, RxGalleryFinalApi.SelectRXType.TYPE_SELECT_MULTI)
//
//                .setImageMultipleResultEvent(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
//                    @Override
//                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
//                        Logger.i("多选图片的回调:"+imageMultipleResultEvent.getResult().size());
//
//                    }
//                }).open();

        //多选图片
        RxGalleryFinal
                .with(ChatActivity.this)
                .image()
                .multiple()
                .maxSize(5)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
//                        ToastUtil.Info(imageMultipleResultEvent.getResult().size() + "张图片");
                        for (int i = 0; i < imageMultipleResultEvent.getResult().size(); i++) {
                            Logger.i(imageMultipleResultEvent.getResult().get(i).getOriginalPath());

                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
//                        Toast.makeText(getBaseContext(), "OVER", Toast.LENGTH_SHORT).show();
                    }
                })
                .openGallery();

        //单选，使用RxGalleryFinal默认设置，并且带有裁剪
//        RxGalleryFinalApi.getInstance(ChatActivity.this)
//                .openGalleryRadioImgDefault(
//                        new RxBusResultSubscriber<ImageRadioResultEvent>() {
//                            @Override
//                            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
//                                Logger.i("只要选择图片就会触发");
//                            }
//                        })
//                .onCropImageResult(
//                        new IRadioImageCheckedListener() {
//                            @Override
//                            public void cropAfter(Object t) {
//                                Logger.i("裁剪完成;"+t);
//                            }
//
//                            @Override
//                            public boolean isActivityFinish() {
//                                Logger.i("返回false不关闭，返回true则为关闭");
//                                return true;
//                            }
//                        });

        //自定义单选
//        RxGalleryFinal
//                .with(ChatActivity.this)
//                .image()
//                .radio()
////                .cropAspectRatioOptions(0, new AspectRatio("3:3", 30, 10))
//                .crop()
//                .imageLoader(ImageLoaderType.GLIDE)
//                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
//                    @Override
//                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
//                        Toast.makeText(getBaseContext(), "选中了图片路径：" + imageRadioResultEvent.getResult().getOriginalPath(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .openGallery();

    }

    /**
     * 打开相机-拍照
     */
    private void openCamera() {
        RxGalleryFinalApi.openZKCamera(ChatActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RxGalleryFinalApi.TAKE_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //刷新相册数据库
            RxGalleryFinalApi.openZKCameraForResult(ChatActivity.this, new MediaScanner.ScanCallback() {
                @Override
                public void onScanCompleted(String[] strings) {
                    Logger.i(String.format("拍照成功,图片存储路径:%s", strings[0]));
                    RxGalleryFinalApi.cropScannerForResult(ChatActivity.this, RxGalleryFinalApi.getModelPath(), strings[0]);
                    //调用裁剪.RxGalleryFinalApi.getModelPath()为默认的输出路径
                    //裁剪后的图片  在onresume 里面获取
                }

            });
        } else {
            Logger.i("拍照已取消");
            ToastUtil.Info("已取消");
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
            mAdapter.setOnItemChildClickListener(this);
            mAdapter.setUpFetchListener(this);
            mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
            recyclerSmoothScrollToBottom();
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
//        ToastUtil.Success(emotionMainFragment.isAtLeastShow()+"");
//        ToastUtil.Info(getSoftButtonsBarHeight()+"");
        closeSoftInput();
        emotionMainFragment.hideEmotionLayoutoOrExtenLayout();
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        closeSoftInput();
        emotionMainFragment.hideEmotionLayoutoOrExtenLayout();

        return true;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        closeSoftInput();
        emotionMainFragment.hideEmotionLayoutoOrExtenLayout();
        // TODO: 2017/7/27  还要处理 消息的点击事件
        ToastUtil.Success("child:" + position);
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

    /**
     * 关`闭软键盘
     */
    private void closeSoftInput() {
        mEditText.clearFocus();
        mInputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
          /* 判断是否拦截返回键操作
                */
        if (!emotionMainFragment.isInterceptBackPress()) {
            super.onBackPressed();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EaseMobUtil.removeReceiveMessageListener(mReceiveLisener);
    }

    /**
     * 聊天布局 滚动方法
     */
    public void recyclerSmoothScrollToBottom() {
        Logger.i("滚动到底部");
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }
        }, 100);
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
     * 环信--收到的监听器
     */
    class MessageReceiveLisener implements EMMessageListener {
        //收到消息
        @Override
        public void onMessageReceived(List<EMMessage> list) {
//            Message obtainMessage = mHandler.obtainMessage();
//            obtainMessage.what = MESSAGE_RECEVIE;
//            obtainMessage.obj = list;
//            mHandler.sendMessage(obtainMessage);
            Logger.i("收到消息:" + list.size() + "===" + list.get(0));

        }

        //收到透传消息
        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {
            Logger.i("收到透传消息:" + list.get(0).toString());
        }

        //收到已读回执
        @Override
        public void onMessageRead(List<EMMessage> list) {
            Logger.i("收到已读回执:" + list.get(0).toString());
        }

        //收到已送达回执---在这里判断 发送成功与失败?
        @Override
        public void onMessageDelivered(List<EMMessage> list) {
            Logger.i("收到已送达回执:" + list.get(0).toString());
        }

        //消息变动---这是什么意思?
        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {
            Logger.i("消息变动");
        }
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
