package com.technopolitan.mocaspaces.customWheelPicker

import android.content.Context
import android.util.AttributeSet
import java.util.*


class WheelMonthPicker(context: Context, attrs: AttributeSet?) :
    WheelPicker(context, attrs), IWheelMonthPicker {
    private var mSelectedMonth: Int

    constructor(context: Context) : this(context, null)

    private fun updateSelectedYear() {
        setSelectedItemPosition(mSelectedMonth - 1)
    }

    override fun setData(data: List<*>?) {
        throw UnsupportedOperationException("You can not invoke setData in WheelMonthPicker")
    }

    override fun getSelectedMonth(): Int {
        return mSelectedMonth
    }

    override fun setSelectedMonth(month: Int) {
        mSelectedMonth = month
        updateSelectedYear()
    }

    override fun getCurrentMonth(): Int {
        return Integer.valueOf(getData()!![getCurrentItemPosition()].toString())
    }

    init {
        val data: MutableList<Int?> = ArrayList()
        for (i in 1..12) data.add(i)
        super.setData(data)
        mSelectedMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        updateSelectedYear()
    }
}