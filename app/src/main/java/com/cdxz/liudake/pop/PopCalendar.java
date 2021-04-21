package com.cdxz.liudake.pop;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cdxz.liudake.R;
import com.lxj.xpopup.core.BottomPopupView;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnMultiChooseListener;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;

import java.util.HashMap;

public class PopCalendar extends BottomPopupView {

    private OnSelectListener onSelectListener;
    private CalendarView calendarView;
    private int[] cDate = CalendarUtil.getCurrentDate();

    public PopCalendar(@NonNull Context context, OnSelectListener onSelectListener) {
        super(context);
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_calendar;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        final TextView title = (TextView) findViewById(R.id.title);

        calendarView = (CalendarView) findViewById(R.id.calendar);
        HashMap<String, String> map = new HashMap<>();
        map.put("2017.10.30", "qaz");
        map.put("2017.10.1", "wsx");
        map.put("2017.11.12", "yhn");
        map.put("2017.9.15", "edc");
        map.put("2017.11.6", "rfv");
        map.put("2017.11.11", "tgb");
        calendarView
//                .setSpecifyMap(map)
                .setStartEndDate("2016.1", "2028.12")
                .setDisableStartEndDate("2016.10.10", "2028.10.10")
                .setInitDate(cDate[0] + "." + cDate[1])
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                .init();
        title.setText(cDate[0] + "年" + cDate[1] + "月");
        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
            }
        });
        calendarView.setOnMultiChooseListener(new OnMultiChooseListener() {
            @Override
            public void onMultiChoose(View view, DateBean date, boolean flag) {
                date.getSolar();
            }
        });

        findViewById(R.id.img_last).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.lastMonth();
            }
        });
        findViewById(R.id.img_next).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.nextMonth();
            }
        });
    }

    public interface OnSelectListener {
        void onSelect(int position);
    }
}
