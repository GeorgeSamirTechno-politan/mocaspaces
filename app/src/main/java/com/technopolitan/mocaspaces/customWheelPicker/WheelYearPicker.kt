package com.technopolitan.mocaspaces.customWheelPicker

import android.content.Context
import android.util.AttributeSet
import java.util.*


class WheelYearPicker(context: Context, attrs: AttributeSet?) :
    WheelPicker(context, attrs), IWheelYearPicker {
    private var mYearStart = 1000
    private var mYearEnd = 3000
    private var mSelectedYear: Int

    constructor(context: Context) : this(context, null)

    private fun updateYears() {
        val data: MutableList<Int?> = ArrayList()
        for (i in mYearStart..mYearEnd) data.add(i)
        super.setData(data)
    }

    private fun updateSelectedYear() {
        setSelectedItemPosition(mSelectedYear - mYearStart)
    }

    override fun setData(data: List<*>?) {
        throw UnsupportedOperationException("You can not invoke setData in WheelYearPicker")
    }

    override fun setYearFrame(start: Int, end: Int) {
        mYearStart = start
        mYearEnd = end
        mSelectedYear = getCurrentYear()
        updateYears()
        updateSelectedYear()
    }

    override fun getYearStart(): Int {
        return mYearStart
    }

    override fun setYearStart(start: Int) {
        mYearStart = start
        mSelectedYear = getCurrentYear()
        updateYears()
        updateSelectedYear()
    }

    override fun getYearEnd(): Int {
        return mYearEnd
    }

    override fun setYearEnd(end: Int) {
        mYearEnd = end
        updateYears()
    }

    override fun getSelectedYear(): Int {
        return mSelectedYear
    }

    override fun setSelectedYear(year: Int) {
        mSelectedYear = year
        updateSelectedYear()
    }

    override fun getCurrentYear(): Int {
        return Integer.valueOf(getData()!![getCurrentItemPosition()].toString())
    }

    init {
        updateYears()
        mSelectedYear = Calendar.getInstance().get(Calendar.YEAR)
        updateSelectedYear()
    }
}