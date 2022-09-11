package com.technopolitan.mocaspaces.customWheelPicker

interface IWheelYearPicker {

    fun setYearFrame(start: Int, end: Int)

    fun getYearStart(): Int

    fun setYearStart(start: Int)

    fun getYearEnd(): Int

    fun setYearEnd(end: Int)


    fun getSelectedYear(): Int

    fun setSelectedYear(year: Int)

    fun getCurrentYear(): Int
}