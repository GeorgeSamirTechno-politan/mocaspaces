package com.technopolitan.mocaspaces.customWheelPicker

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.customWheelPicker.WheelPicker.OnWheelChangeListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class WheelDatePicker(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs), WheelPicker.OnItemSelectedListener,
    IDebug, IWheelPicker, IWheelDatePicker, IWheelYearPicker, IWheelMonthPicker, IWheelDayPicker {
    private val mPickerYear: WheelYearPicker
    private val mPickerMonth: WheelMonthPicker
    private val mPickerDay: WheelDayPicker
    private var mListener: OnDateSelectedListener? = null
    private val mTVYear: TextView
    private val mTVMonth: TextView
    private val mTVDay: TextView
    private var mYear: Int
    private var mMonth: Int
    private var mDay: Int

    constructor(context: Context) : this(context, null)

    private fun setMaximumWidthTextYear() {
        val years: List<*> = mPickerYear.getData()!!
        val lastYear = years[years.size - 1].toString()
        val sb = StringBuilder()
        for (i in 0 until lastYear.length) sb.append("0")
        mPickerYear.setMaximumWidthText(sb.toString())
    }

    override fun onItemSelected(picker: WheelPicker?, data: Any?, position: Int) {
        if (picker!!.id == R.id.wheel_date_picker_year) {
            mYear = data as Int
            mPickerDay.setYear(mYear)
        } else if (picker.id == R.id.wheel_date_picker_month) {
            mMonth = data as Int
            mPickerDay.setMonth(mMonth)
        }
        mDay = mPickerDay.getCurrentDay()
        val date = "$mYear-$mMonth-$mDay"
        if (null != mListener) try {
            mListener!!.onDateSelected(this, SDF.parse(date))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    override fun setDebug(isDebug: Boolean) {
        mPickerYear.setDebug(isDebug)
        mPickerMonth.setDebug(isDebug)
        mPickerDay.setDebug(isDebug)
    }

    override fun getVisibleItemCount(): Int {
        if (mPickerYear.getVisibleItemCount() === mPickerMonth.getVisibleItemCount() &&
            mPickerMonth.getVisibleItemCount() === mPickerDay.getVisibleItemCount()
        ) return mPickerYear.getVisibleItemCount()
        throw ArithmeticException(
            "Can not get visible item count correctly from" +
                    "WheelDatePicker!"
        )
    }

    override fun setVisibleItemCount(count: Int) {
        mPickerYear.setVisibleItemCount(count)
        mPickerMonth.setVisibleItemCount(count)
        mPickerDay.setVisibleItemCount(count)
    }

    override fun isCyclic(): Boolean {
        return mPickerYear.isCyclic() && mPickerMonth.isCyclic() && mPickerDay.isCyclic()
    }

    override fun setCyclic(isCyclic: Boolean) {
        mPickerYear.setCyclic(isCyclic)
        mPickerMonth.setCyclic(isCyclic)
        mPickerDay.setCyclic(isCyclic)
    }

    @Deprecated("")
    override fun setOnItemSelectedListener(listener: WheelPicker.OnItemSelectedListener?) {
        throw UnsupportedOperationException(
            "You can not set OnItemSelectedListener for" +
                    "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun getSelectedItemPosition(): Int {
        throw UnsupportedOperationException(
            "You can not get position of selected item from" +
                    "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun setSelectedItemPosition(position: Int) {
        throw UnsupportedOperationException(
            "You can not set position of selected item for" +
                    "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun getCurrentItemPosition(): Int {
        throw UnsupportedOperationException(
            "You can not get position of current item from" +
                    "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun getData(): List<*> {
        throw UnsupportedOperationException("You can not get data source from WheelDatePicker")
    }

    @Deprecated("")
    override fun setData(data: List<*>?) {
        throw UnsupportedOperationException(
            "You don't need to set data source for" +
                    "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun setSameWidth(hasSameSize: Boolean) {
        throw UnsupportedOperationException(
            "You don't need to set same width for" +
                    "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun hasSameWidth(): Boolean {
        throw UnsupportedOperationException(
            "You don't need to set same width for" +
                    "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun setOnWheelChangeListener(listener: OnWheelChangeListener?) {
        throw UnsupportedOperationException(
            "WheelDatePicker unsupport set" +
                    "OnWheelChangeListener"
        )
    }

    @Deprecated("")
    override fun getMaximumWidthText(): String? {
        throw UnsupportedOperationException(
            "You can not get maximum width text from" +
                    "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun setMaximumWidthText(text: String?) {
        throw UnsupportedOperationException(
            "You don't need to set maximum width text for" +
                    "WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun getMaximumWidthTextPosition(): Int {
        throw UnsupportedOperationException(
            "You can not get maximum width text position" +
                    "from WheelDatePicker"
        )
    }

    @Deprecated("")
    override fun setMaximumWidthTextPosition(position: Int) {
        throw UnsupportedOperationException(
            "You don't need to set maximum width text" +
                    "position for WheelDatePicker"
        )
    }

    override fun getSelectedItemTextColor(): Int {
        if (mPickerYear.getSelectedItemTextColor() === mPickerMonth.getSelectedItemTextColor() &&
            mPickerMonth.getSelectedItemTextColor() === mPickerDay.getSelectedItemTextColor()
        ) return mPickerYear.getSelectedItemTextColor()
        throw RuntimeException(
            "Can not get color of selected item text correctly from" +
                    "WheelDatePicker!"
        )
    }

    override fun setSelectedItemTextColor(color: Int) {
        mPickerYear.setSelectedItemTextColor(color)
        mPickerMonth.setSelectedItemTextColor(color)
        mPickerDay.setSelectedItemTextColor(color)
    }

    override fun getItemTextColor(): Int {
        if (mPickerYear.getItemTextColor() === mPickerMonth.getItemTextColor() &&
            mPickerMonth.getItemTextColor() === mPickerDay.getItemTextColor()
        ) return mPickerYear.getItemTextColor()
        throw RuntimeException(
            "Can not get color of item text correctly from" +
                    "WheelDatePicker!"
        )
    }

    override fun setItemTextColor(color: Int) {
        mPickerYear.setItemTextColor(color)
        mPickerMonth.setItemTextColor(color)
        mPickerDay.setItemTextColor(color)
    }

    override fun getItemTextSize(): Int {
        if (mPickerYear.getItemTextSize() === mPickerMonth.getItemTextSize() &&
            mPickerMonth.getItemTextSize() === mPickerDay.getItemTextSize()
        ) return mPickerYear.getItemTextSize()
        throw RuntimeException(
            "Can not get size of item text correctly from" +
                    "WheelDatePicker!"
        )
    }

    override fun setItemTextSize(size: Int) {
        mPickerYear.setItemTextSize(size)
        mPickerMonth.setItemTextSize(size)
        mPickerDay.setItemTextSize(size)
    }

    override fun getItemSpace(): Int {
        if (mPickerYear.getItemSpace() === mPickerMonth.getItemSpace() &&
            mPickerMonth.getItemSpace() === mPickerDay.getItemSpace()
        ) return mPickerYear.getItemSpace()
        throw RuntimeException("Can not get item space correctly from WheelDatePicker!")
    }

    override fun setItemSpace(space: Int) {
        mPickerYear.setItemSpace(space)
        mPickerMonth.setItemSpace(space)
        mPickerDay.setItemSpace(space)
    }

    override fun setIndicator(hasIndicator: Boolean) {
        mPickerYear.setIndicator(hasIndicator)
        mPickerMonth.setIndicator(hasIndicator)
        mPickerDay.setIndicator(hasIndicator)
    }

    override fun hasIndicator(): Boolean {
        return mPickerYear.hasIndicator() && mPickerMonth.hasIndicator() &&
                mPickerDay.hasIndicator()
    }

    override fun getIndicatorSize(): Int {
        if (mPickerYear.getIndicatorSize() === mPickerMonth.getIndicatorSize() &&
            mPickerMonth.getIndicatorSize() === mPickerDay.getIndicatorSize()
        ) return mPickerYear.getIndicatorSize()
        throw RuntimeException("Can not get indicator size correctly from WheelDatePicker!")
    }

    override fun setIndicatorSize(size: Int) {
        mPickerYear.setIndicatorSize(size)
        mPickerMonth.setIndicatorSize(size)
        mPickerDay.setIndicatorSize(size)
    }

    override fun getIndicatorColor(): Int {
        if (mPickerYear.getCurtainColor() === mPickerMonth.getCurtainColor() &&
            mPickerMonth.getCurtainColor() === mPickerDay.getCurtainColor()
        ) return mPickerYear.getCurtainColor()
        throw RuntimeException("Can not get indicator color correctly from WheelDatePicker!")
    }

    override fun setIndicatorColor(color: Int) {
        mPickerYear.setIndicatorColor(color)
        mPickerMonth.setIndicatorColor(color)
        mPickerDay.setIndicatorColor(color)
    }

    override fun setCurtain(hasCurtain: Boolean) {
        mPickerYear.setCurtain(hasCurtain)
        mPickerMonth.setCurtain(hasCurtain)
        mPickerDay.setCurtain(hasCurtain)
    }

    override fun hasCurtain(): Boolean {
        return mPickerYear.hasCurtain() && mPickerMonth.hasCurtain() &&
                mPickerDay.hasCurtain()
    }

    override fun getCurtainColor(): Int {
        if (mPickerYear.getCurtainColor() === mPickerMonth.getCurtainColor() &&
            mPickerMonth.getCurtainColor() === mPickerDay.getCurtainColor()
        ) return mPickerYear.getCurtainColor()
        throw RuntimeException("Can not get curtain color correctly from WheelDatePicker!")
    }

    override fun setCurtainColor(color: Int) {
        mPickerYear.setCurtainColor(color)
        mPickerMonth.setCurtainColor(color)
        mPickerDay.setCurtainColor(color)
    }

    override fun setAtmospheric(hasAtmospheric: Boolean) {
        mPickerYear.setAtmospheric(hasAtmospheric)
        mPickerMonth.setAtmospheric(hasAtmospheric)
        mPickerDay.setAtmospheric(hasAtmospheric)
    }

    override fun hasAtmospheric(): Boolean {
        return mPickerYear.hasAtmospheric() && mPickerMonth.hasAtmospheric() &&
                mPickerDay.hasAtmospheric()
    }

    override fun isCurved(): Boolean {
        return mPickerYear.isCurved() && mPickerMonth.isCurved() && mPickerDay.isCurved()
    }

    override fun setCurved(isCurved: Boolean) {
        mPickerYear.setCurved(isCurved)
        mPickerMonth.setCurved(isCurved)
        mPickerDay.setCurved(isCurved)
    }

    @Deprecated("")
    override fun getItemAlign(): Int {
        throw UnsupportedOperationException("You can not get item align from WheelDatePicker")
    }

    @Deprecated("")
    override fun setItemAlign(align: Int) {
        throw UnsupportedOperationException(
            "You don't need to set item align for" +
                    "WheelDatePicker"
        )
    }

    override fun getTypeface(): Typeface? {
        if (mPickerYear.getTypeface()!! == mPickerMonth.getTypeface() &&
            mPickerMonth.getTypeface()!! == mPickerDay.getTypeface()
        ) return mPickerYear.getTypeface()
        throw RuntimeException("Can not get typeface correctly from WheelDatePicker!")
    }

    override fun setTypeface(tf: Typeface?) {
        mPickerYear.setTypeface(tf)
        mPickerMonth.setTypeface(tf)
        mPickerDay.setTypeface(tf)
    }

    override fun setOnDateSelectedListener(listener: OnDateSelectedListener?) {
        mListener = listener
    }

    override fun getCurrentDate(): Date? {
        val date = "$mYear-$mMonth-$mDay"
        try {
            return SDF.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    override fun getItemAlignYear(): Int {
        return mPickerYear.getItemAlign()
    }

    override fun setItemAlignYear(align: Int) {
        mPickerYear.setItemAlign(align)
    }

    override fun getItemAlignMonth(): Int {
        return mPickerMonth.getItemAlign()
    }

    override fun setItemAlignMonth(align: Int) {
        mPickerMonth.setItemAlign(align)
    }

    override fun getItemAlignDay(): Int {
        return mPickerDay.getItemAlign()
    }

    override fun setItemAlignDay(align: Int) {
        mPickerDay.setItemAlign(align)
    }

    override fun getWheelYearPicker(): WheelYearPicker? {
        return mPickerYear
    }

    override fun getWheelMonthPicker(): WheelMonthPicker? {
        return mPickerMonth
    }

    override fun getWheelDayPicker(): WheelDayPicker? {
        return mPickerDay
    }

    override fun getTextViewYear(): TextView? {
        return mTVYear
    }

    override fun getTextViewMonth(): TextView? {
        return mTVMonth
    }

    override fun getTextViewDay(): TextView? {
        return mTVDay
    }

    override fun setYearFrame(start: Int, end: Int) {
        mPickerYear.setYearFrame(start, end)
    }

    override fun getYearStart(): Int {
        return mPickerYear.getYearStart()
    }

    override fun setYearStart(start: Int) {
        mPickerYear.setYearStart(start)
    }

    override fun getYearEnd(): Int {
        return mPickerYear.getYearEnd()
    }

    override fun setYearEnd(end: Int) {
        mPickerYear.setYearEnd(end)
    }

    override fun getSelectedYear(): Int {
        return mPickerYear.getSelectedYear()
    }

    override fun setSelectedYear(year: Int) {
        mYear = year
        mPickerYear.setSelectedYear(year)
        mPickerDay.setYear(year)
    }

    override fun getCurrentYear(): Int {
        return mPickerYear.getCurrentYear()
    }

    override fun getSelectedMonth(): Int {
        return mPickerMonth.getSelectedMonth()
    }

    override fun setSelectedMonth(month: Int) {
        mMonth = month
        mPickerMonth.setSelectedMonth(month)
        mPickerDay.setMonth(month)
    }

    override fun getCurrentMonth(): Int {
        return mPickerMonth.getCurrentMonth()
    }

    override fun getSelectedDay(): Int {
        return mPickerDay.getSelectedDay()
    }

    override fun setSelectedDay(day: Int) {
        mDay = day
        mPickerDay.setSelectedDay(day)
    }

    override fun getCurrentDay(): Int {
        return mPickerDay.getCurrentDay()
    }

    override fun setYearAndMonth(year: Int, month: Int) {
        mYear = year
        mMonth = month
        mPickerYear.setSelectedYear(year)
        mPickerMonth.setSelectedMonth(month)
        mPickerDay.setYearAndMonth(year, month)
    }

    override fun getYear(): Int {
        return getSelectedYear()
    }

    override fun setYear(year: Int) {
        mYear = year
        mPickerYear.setSelectedYear(year)
        mPickerDay.setYear(year)
    }

    override fun getMonth(): Int {
        return getSelectedMonth()
    }

    override fun setMonth(month: Int) {
        mMonth = month
        mPickerMonth.setSelectedMonth(month)
        mPickerDay.setMonth(month)
    }

    interface OnDateSelectedListener {
        fun onDateSelected(picker: WheelDatePicker?, date: Date?)
    }

    companion object {
        private val SDF: SimpleDateFormat = SimpleDateFormat("yyyy-M-d", Locale.getDefault())
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_wheel_date_picker, this)
        mPickerYear = findViewById<View>(R.id.wheel_date_picker_year) as WheelYearPicker
        mPickerMonth = findViewById<View>(R.id.wheel_date_picker_month) as WheelMonthPicker
        mPickerDay = findViewById<View>(R.id.wheel_date_picker_day) as WheelDayPicker
        mPickerYear.setOnItemSelectedListener(this)
        mPickerMonth.setOnItemSelectedListener(this)
        mPickerDay.setOnItemSelectedListener(this)
        setMaximumWidthTextYear()
        mPickerMonth.setMaximumWidthText("00")
        mPickerDay.setMaximumWidthText("00")
        mTVYear = findViewById<View>(R.id.wheel_date_picker_year_tv) as TextView
        mTVMonth = findViewById<View>(R.id.wheel_date_picker_month_tv) as TextView
        mTVDay = findViewById<View>(R.id.wheel_date_picker_day_tv) as TextView
        mYear = mPickerYear.getCurrentYear()
        mMonth = mPickerMonth.getCurrentMonth()
        mDay = mPickerDay.getCurrentDay()
    }
}