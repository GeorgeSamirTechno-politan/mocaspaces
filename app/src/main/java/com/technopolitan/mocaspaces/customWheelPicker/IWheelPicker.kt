package com.technopolitan.mocaspaces.customWheelPicker

import android.graphics.Typeface


interface IWheelPicker {

    fun getVisibleItemCount(): Int
    fun setVisibleItemCount(count: Int)
    fun isCyclic(): Boolean
    fun setCyclic(isCyclic: Boolean)
    fun setOnItemSelectedListener(listener: WheelPicker.OnItemSelectedListener?)
    fun getSelectedItemPosition(): Int
    fun setSelectedItemPosition(position: Int)
    fun getCurrentItemPosition(): Int
    fun getData(): List<*>?
    fun setData(data: List<*>?)
    fun setSameWidth(hasSameSize: Boolean)
    fun setOnWheelChangeListener(listener: WheelPicker.OnWheelChangeListener?)
    fun hasSameWidth(): Boolean
    fun getMaximumWidthText(): String?
    fun setMaximumWidthText(text: String?)
    fun getMaximumWidthTextPosition(): Int

    fun setMaximumWidthTextPosition(position: Int)
    fun getSelectedItemTextColor(): Int
    fun setSelectedItemTextColor(color: Int)

    fun getItemTextColor(): Int
    fun setItemTextColor(color: Int)
    fun getItemTextSize(): Int
    fun setItemTextSize(size: Int)

    fun getItemSpace(): Int
    fun setItemSpace(space: Int)
    fun setIndicator(hasIndicator: Boolean)
    fun hasIndicator(): Boolean
    fun getIndicatorSize(): Int

    fun setIndicatorSize(size: Int)
    fun getIndicatorColor(): Int
    fun setIndicatorColor(color: Int)
    fun setCurtain(hasCurtain: Boolean)
    fun hasCurtain(): Boolean
    fun getCurtainColor(): Int
    fun setCurtainColor(color: Int)
    fun setAtmospheric(hasAtmospheric: Boolean)

    fun hasAtmospheric(): Boolean
    fun isCurved(): Boolean

    fun setCurved(isCurved: Boolean)

    fun getItemAlign(): Int
    fun setItemAlign(align: Int)

    fun getTypeface(): Typeface?
    fun setTypeface(tf: Typeface?)
}