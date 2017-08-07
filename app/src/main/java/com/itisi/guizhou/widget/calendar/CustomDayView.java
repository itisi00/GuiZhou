package com.itisi.guizhou.widget.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itisi.guizhou.R;
import com.ldf.calendar.Utils;
import com.ldf.calendar.component.State;
import com.ldf.calendar.interf.IDayRenderer;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.DayView;

/**
 ***********************
 * 功 能:自定义-日历视图中的 日视图
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/7 11:10
 * 修改人:itisi
 * 修改时间: 2017/8/7 11:10
 * 修改内容:itisi
 * *********************
 */
public class CustomDayView extends DayView {

    private TextView dateTv;// 今天
    private ImageView marker;//有标记的图标
    private View selectedBackground;//选中的背景
    private View todayBackground;// 今天的背景
    private final CalendarDate today = new CalendarDate();

    /**
     * 构造器
     *
     * @param context 上下文
     * @param layoutResource 自定义DayView的layout资源
     */
    public CustomDayView(Context context, int layoutResource) {
        super(context, layoutResource);
        dateTv =  findViewById(R.id.tv_date);
        marker =  findViewById(R.id.iv_maker);
        selectedBackground = findViewById(R.id.selected_background);
        todayBackground = findViewById(R.id.today_background);
    }

    @Override
    public void refreshContent() {
        renderToday(day.getDate());
        renderSelect(day.getState());
        renderMarker(day.getDate(), day.getState());
        super.refreshContent();
    }

    private void renderMarker(CalendarDate date, State state) {
        if (Utils.loadMarkData().containsKey(date.toString())) {
            if (state == State.SELECT || date.toString().equals(today.toString())) {
                marker.setVisibility(GONE);
            } else {
                marker.setVisibility(VISIBLE);
                if (Utils.loadMarkData().get(date.toString()).equals("0")) {
                    marker.setEnabled(true);
                } else {
                    marker.setEnabled(false);
                }
            }
        } else {
            marker.setVisibility(GONE);
        }
    }

    private void renderSelect(State state) {
        if (state == State.SELECT) {
            selectedBackground.setVisibility(VISIBLE);
            dateTv.setTextColor(Color.WHITE);
        } else if (state == State.NEXT_MONTH || state == State.PAST_MONTH) {
            selectedBackground.setVisibility(GONE);
            dateTv.setTextColor(Color.parseColor("#d5d5d5"));
        } else {
            selectedBackground.setVisibility(GONE);
            dateTv.setTextColor(Color.parseColor("#111111"));
        }
    }

    private void renderToday(CalendarDate date) {
        if (date != null) {
            if (date.equals(today)) {
                dateTv.setText("今");
                todayBackground.setVisibility(VISIBLE);
            } else {
                dateTv.setText(date.day + "");
                todayBackground.setVisibility(GONE);
            }
        }
    }

    @Override
    public IDayRenderer copy() {
        return new CustomDayView(context, layoutResource);
    }
}
