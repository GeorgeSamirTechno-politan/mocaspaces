package com.technopolitan.mocaspaces.models.booking

import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import java.util.*
enum class BookingType{
    Hourly, DayPass, Tailored, Bundle, PrivateOffice, MeetingSpace, BizLounge, EventSpace
}
class DayMapper {
    private var id: Int = 0
    private var dayName: String = ""
    private var dayNumber: String = ""
    private lateinit var date: Date
    private var enable: Boolean = false
    private var selected: Boolean = false
    private var currentSelected: Boolean = false
    private lateinit var dateTimeModule: DateTimeModule
    private lateinit var bookingType: BookingType

    private fun buildDayList(dayString: List<String>, dateTimeModule: DateTimeModule, bookingType: BookingType): MutableList<DayMapper> {
        this.dateTimeModule = dateTimeModule
        this.bookingType = bookingType
        val list: MutableList<DayMapper> = mutableListOf()
        var calender = GregorianCalendar()
        val apiDateList = mutableListOf<Date>()
        dayString.forEach{ item->
            apiDateList.add(dateTimeModule.formatDate(item, DateTimeConstants.apiDefaultDateTimeFormat)!!)
        }
        return list
    }

    private fun buildDisabledDateList(endDate: Date):  MutableList<DayMapper>{
        val list: MutableList<DayMapper> = mutableListOf()

        return list
    }

    private fun getLastDisabledDayInList(endDate: Date): Date{
        var calender = GregorianCalendar()
        calender.time = endDate
        when(bookingType){
            BookingType.Hourly -> TODO()
            BookingType.DayPass -> TODO()
            BookingType.Tailored -> TODO()
            BookingType.Bundle -> TODO()
            BookingType.PrivateOffice -> TODO()
            BookingType.MeetingSpace -> TODO()
            BookingType.BizLounge -> TODO()
            BookingType.EventSpace -> TODO()
        }
        return calender.time
    }
}