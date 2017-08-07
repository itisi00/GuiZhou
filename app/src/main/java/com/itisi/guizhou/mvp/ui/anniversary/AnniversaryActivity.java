package com.itisi.guizhou.mvp.ui.anniversary;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itisi.guizhou.R;
import com.itisi.guizhou.adapter.AnniversaryAdapter;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;
import com.itisi.guizhou.widget.calendar.CustomDayView;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * **********************
 * 功 能:纪念日提醒
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/1 15:19
 * 修改人:itisi
 * 修改时间: 2017/8/1 15:19
 * 修改内容:itisi
 * *********************
 */
@UseRxBus
public class AnniversaryActivity extends RootActivity<AnniversaryPresenter>
        implements AnniversaryContract.View
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnItemClickListener
        , BaseQuickAdapter.OnItemLongClickListener {


    @BindView(R.id.calendar_view)
    MonthPager mMonthPager;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    //数据列表
    AnniversaryAdapter mAdapter;
    private int pageSize = 10;//页大小
    private int pageIndex = 1;//页数
    private int totalCount = 0;//服务器返回的总的数量 有些接口可能没有
    private boolean isRefreshing = true;//刷新 还是 加载更多 加载成功以后 是追加还是替换

    //日历相关
    private CalendarViewAdapter calendarAdapter;//日历适配器
    private OnSelectDateListener onSelectDateListener;//日历选择事件
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;//暂时用不到
    private CalendarDate currentDate;//当前时间,或者说上一次选择的时间,估计会用到
    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private boolean initiated = false;//是否第一次初始化


    @Override
    protected int getLayoutId() {
        return R.layout.activity_anniversary;
    }

//    上一个月 下一个月
//     nextMonthBtn.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);
//           }
//       });
//        lastMonthBtn.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);
//           }
//       });

    @Override
    protected void initEventAndData() {
        initView();
        initViewListener();
        setToolbarMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.Success("新增");
            }
        });
        setToolbarTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDate today = new CalendarDate();
                calendarAdapter.notifyDataChanged(today);
                setToolbarTvTitle(today.getYear() + " / " + today.getMonth());
            }
        });


        setToolbarTvLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ToastUtil.Info("其他功能");
                return true;
            }
        });
    }

    private void initView() {
        initCalendarView();
    }

    private void initViewListener() {
        initAdapter();
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setItemPrefetchEnabled(false);
        mAdapter = new AnniversaryAdapter(R.layout.item_anniversary);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.addItemDecoration();
        mRecyclerView.setAdapter(mAdapter);

        //这里可能要开启 下拉动画
        loadData();

    }

    /**
     * 点击刷新日历--处理日历的点击事件
     *
     * @param date
     */
    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        Logger.i("onSelectDate:" + date.getYear() + "年" + date.getMonth() + "月" + date.getDay() + "日");
        ToastUtil.Info("onSelectDate:" + date.getYear() + "年" + date.getMonth() + "月" + date.getDay() + "日");

    }

    private void initCalendarView() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                mMonthPager.selectOtherMonth(offset);
            }
        };
        /**
         * 初始化日历适配器
         */
        calendarAdapter = new CalendarViewAdapter(
                mActivity,
                onSelectDateListener,
                CalendarAttr.CalendayType.MONTH,
                new CustomDayView(mActivity, R.layout.partial_custom_day));
        initSystemMarkData();
        initMonthPager();
    }

    private void initSystemMarkData() {
        HashMap markData = new HashMap<>();
        //1表示红点，0表示灰点
        markData.put("1970-1-1", "0");
        calendarAdapter.setMarkData(markData);

    }

    /**
     * 初始化标记--测试数据
     */
    private void initMarkData() {
        HashMap markData = new HashMap<>();
        //1表示红点，0表示灰点
        markData.put("2017-8-9", "1");
        markData.put("2017-7-9", "0");
        markData.put("2017-6-9", "1");
        markData.put("2017-8-10", "0");
        calendarAdapter.setMarkData(markData);

    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     * 日历横向切换
     *
     * @return void
     */
    private void initMonthPager() {
        mMonthPager.setAdapter(calendarAdapter);
        mMonthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        mMonthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        //月份切换事件
        mMonthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) instanceof Calendar) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    setToolbarTvTitle(date.getYear() + " / " + date.getMonth());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !initiated) {
            CalendarDate today = new CalendarDate();
            calendarAdapter.notifyDataChanged(today);
            initiated = true;
        }
    }

    @Override
    protected String setToolbarTvTitle() {
//        java.util.Calendar c = java.util.Calendar.getInstance();
//        int year = c.get(java.util.Calendar.YEAR);
//        int month = c.get(java.util.Calendar.MONTH)+1;//月份是 0-11 所以要加1
        CalendarDate today = new CalendarDate();
        return today.getYear() + " / " + today.getMonth();
    }

    @Override
    protected String setToolbarMoreTxt() {
        return "加一个";
    }

    @Override
    public void showContent(String msg) {

    }

    @Override
    public void loadSuccess(List<MeiZiBean> beanList) {
//        if (isRefreshing) {//根本就不会有这个选项....第一次 这个if 还是成立的 这里得改 坑了一把
//            mAdapter.setNewData(beanList);
//            mSwipeRefreshLayout.setRefreshing(false);
//            mPullToRefreshView.setRefreshing(false);
//        } else {
//            mAdapter.addData(beanList);
//            mAdapter.loadMoreComplete();//加载完成  还可继续加载
//            mAdapter.loadMoreEnd();//数据全部加载完成 没有更多数据
//            mAdapter.loadMoreFail();//加载失败 点击重新加载
//            mAdapter.loadMoreEnd(true);//true is gone,false is visible,加载完成 不显示底部提示语
//        }
        mAdapter.addData(beanList);
        mAdapter.loadMoreComplete();//加载完成  还可继续加载
        initMarkData();
        calendarAdapter.notifyDataSetChanged();
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

    @Override
    public void onLoadMoreRequested() {
        //加载更多数据的代码*****************************
        pageIndex += 1;
        isRefreshing = false;//标明 此次是加载的
        loadData();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
