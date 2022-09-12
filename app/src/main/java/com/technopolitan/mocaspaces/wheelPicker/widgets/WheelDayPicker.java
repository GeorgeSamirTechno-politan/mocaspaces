package com.technopolitan.mocaspaces.wheelPicker.widgets;


import android.content.Context;
import android.util.AttributeSet;

import com.technopolitan.mocaspaces.wheelPicker.WheelPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日期选择器
 * <p>
 * Picker for Day
 *
 * @author AigeStudio 2016-07-12
 * @version 1
 */
public class WheelDayPicker extends WheelPicker implements IWheelDayPicker {
    private static final Map<Integer, List<Integer>> DAYS = new HashMap<>();
    private int startDay = 1;
    private int selectedMonth = 1;
    private int selectedYear = 1;
    private final Calendar mCalendar;

    private int mYear, mMonth;
    private int mSelectedDay;

    public WheelDayPicker(Context context) {
        this(context, null);
    }

    public WheelDayPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        mCalendar = Calendar.getInstance();

        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);

        updateDays();

        mSelectedDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        updateSelectedDay();
    }

    private void updateDays() {
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.MONTH, mMonth);

        int days = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(Calendar.getInstance().getTime());
        List<Integer> data = DAYS.get(days);
        if (null == data) {
            data = new ArrayList<>();
            if (calendar.get(Calendar.MONTH) + 1 == selectedMonth &&
                    calendar.get(Calendar.YEAR) == selectedYear)
                startDay = calendar.get(Calendar.DAY_OF_MONTH);
            for (int i = startDay; i <= days; i++)
                data.add(i);
            DAYS.put(days, data);
        }
        super.setData(data);
    }

    private void updateSelectedDay() {
        setSelectedItemPosition(mSelectedDay - 1);
    }

    @Override
    public void setData(List data) {
        throw new UnsupportedOperationException("You can not invoke setData in WheelDayPicker");
    }

    @Override
    public int getSelectedDay() {
        return mSelectedDay;
    }

    @Override
    public void setSelectedDay(int day) {
        mSelectedDay = day;
        updateSelectedDay();
    }

    @Override
    public int getCurrentDay() {
        return Integer.valueOf(String.valueOf(getData().get(getCurrentItemPosition())));
    }

    @Override
    public void setYearAndMonth(int year, int month) {
        mYear = year;
        mMonth = month - 1;
        updateDays();
    }

    @Override
    public int getYear() {
        return mYear;
    }

    @Override
    public void setYear(int year) {
        mYear = year;
        updateDays();
    }

    @Override
    public int getMonth() {
        return mMonth;
    }

    @Override
    public void setMonth(int month) {
        mMonth = month - 1;
        updateDays();
    }

    @Override
    public void setSelectedMonthForStartDay(int month, int year) {
        this.selectedMonth = month;
        this.selectedYear = year;
    }
}