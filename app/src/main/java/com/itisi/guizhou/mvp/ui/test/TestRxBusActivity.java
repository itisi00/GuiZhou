package com.itisi.guizhou.mvp.ui.test;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.ui.main.MainActivity;
import com.itisi.guizhou.mvp.ui.main.MainContract;
import com.itisi.guizhou.mvp.ui.main.MainPresenter;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.RxBus;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;

import java.util.Date;

import butterknife.BindView;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

@UseRxBus
public class TestRxBusActivity extends RootActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.btn_test)
    Button btn_test;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_rx_bus;
    }

    @Override
    protected String setToolbarTvTitle() {
        return "测试页面测试标题测试页面测试标题测试页面测试标题";
    }

//    @Override
//    protected String setToolbarMoreTxt() {
//        return "更多";
//    }

    @Override
    protected int setMenuLayoutId() {
        return R.menu.test_menu;
    }

    @Override
    protected Toolbar.OnMenuItemClickListener setMenuItemClickListener() {
        return new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ToastUtil.Success(item.getTitle().toString());
                return false;
            }
        };
    }

    @Override
    public int setSwipeBackOriginal() {
        return SwipeBackLayout.EDGE_ALL;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                RxBus.getInstance().post(tag,"message");
                 RxBus.getInstance().post(RxBus.getInstance().getTag(MainActivity.class,RxBus.TAG_UPDATE),
                         new Date().toString());
//                Logger.i("rxbus发送数据了");
            }
        });
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

}
