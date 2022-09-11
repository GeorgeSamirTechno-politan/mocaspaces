package com.technopolitan.mocaspaces.wheelPicker.widgets;


import android.content.Context;
import android.util.AttributeSet;


import com.technopolitan.mocaspaces.R;
import com.technopolitan.mocaspaces.wheelPicker.WheelPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * 月份选择器
 * <p>
 * Picker for Months
 *
 * @author AigeStudio 2016-07-12
 * @version 1
 */
public class WheelMonthPicker extends WheelPicker implements IWheelMonthPicker {
    private int mSelectedMonth;
    private List<String> monthNames = Arrays.asList(getResources()
            .getStringArray(R.array.WheelMonthName));
    public WheelMonthPicker(Context context) {
        this(context, null);
    }

    public WheelMonthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        //        for (int i = 0; i <= monthNames.size(); i++)
        super.setData(monthNames);
        mSelectedMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        updateSelectedYear();
    }

    private void updateSelectedYear() {
        setSelectedItemPosition(mSelectedMonth - 1);
    }

    @Override
    public void setData(List data) {
        throw new UnsupportedOperationException("You can not invoke setData in WheelMonthPicker");
    }

    @Override
    public int getSelectedMonth() {
        return mSelectedMonth;
    }

    @Override
    public void setSelectedMonth(int month) {
        mSelectedMonth = month;
        updateSelectedYear();
    }

    @Override
    public int getCurrentMonth() {
        return getCurrentItemPosition();
//        return Integer.valueOf(String.valueOf(getData().get(getCurrentItemPosition())));
    }
}
