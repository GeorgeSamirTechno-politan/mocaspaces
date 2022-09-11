package com.technopolitan.mocaspaces.customWheelPicker

interface IWheelDayPicker {
    fun getSelectedDay(): Int
    fun setSelectedDay(day: Int)
    fun getCurrentDay(): Int

    fun setYearAndMonth(year: Int, month: Int)
    fun getYear(): Int
    fun setYear(year: Int)
    fun getMonth(): Int
    fun setMonth(month: Int)
}