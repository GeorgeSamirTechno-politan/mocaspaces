package com.technopolitan.mocaspaces.customWheelPicker

import android.content.Context
import android.util.AttributeSet
import java.util.*


class WheelDayPicker(context: Context, attrs: AttributeSet?) :
    WheelPicker(context, attrs), IWheelDayPicker {
    private val mCalendar: Calendar
    private var mYear: Int
    private var mMonth: Int
    private var mSelectedDay: Int

    constructor(context: Context) : this(context, null)

    private fun updateDays() {
        mCalendar.set(Calendar.YEAR, mYear)
        mCalendar.set(Calendar.MONTH, mMonth)
        val days: Int = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        var data = DAYS[days]
        if (null == data) {
            data = ArrayList()
            for (i in 1..days) data.add(i)
            DAYS[days] = data
        }
        super.setData(data)
    }

    private fun updateSelectedDay() {
        setSelectedItemPosition(mSelectedDay - 1)
    }

    override fun setData(data: List<*>?) {
        throw UnsupportedOperationException("You can not invoke setData in WheelDayPicker")
    }

    override fun getSelectedDay(): Int {
        return mSelectedDay
    }

    override fun setSelectedDay(day: Int) {
        mSelectedDay = day
        updateSelectedDay()
    }

    override fun getCurrentDay(): Int {
        return Integer.valueOf(getData()!![getCurrentItemPosition()].toString())
    }

    override fun setYearAndMonth(year: Int, month: Int) {
        mYear = year
        mMonth = month - 1
        updateDays()
    }

    override fun getYear(): Int {
        return mYear
    }

    override fun setYear(year: Int) {
        mYear = year
        updateDays()
    }

    override fun getMonth(): Int {
        return mMonth
    }

    override fun setMonth(month: Int) {
        mMonth = month - 1
        updateDays()
    }

    companion object {
        private val DAYS: MutableMap<Int, MutableList<Int?>> = HashMap()
    }

    init {
        mCalendar = Calendar.getInstance()
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        updateDays()
        mSelectedDay = mCalendar.get(Calendar.DAY_OF_MONTH)
        updateSelectedDay()
    }
}