package com.itisi.guizhou.mvp.ui.main.chat;


import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootFragment;

/**
 * 聊天--会话界面
 */
public class ChatSessionFragment   extends RootFragment<ChatSessionPresenter>
        implements ChatSessionContract.View {

    public ChatSessionFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_session;
    }

    @Override
    protected void initEventAndData() {

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
    protected void initInject() {
        getFragmentComponent().inject(this);
    }
}
