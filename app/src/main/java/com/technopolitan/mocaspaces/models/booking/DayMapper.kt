package com.technopolitan.mocaspaces.models.booking

import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.DateTimeUnits
import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import java.util.*


class DayMapper {
    var id: Int = 0
    var dayName: String = ""
    var dayNumber: String = ""
    lateinit var date: Date
    var enable: Boolean = false
    var selected: Boolean = false
    var currentSelected: Boolean = false
    lateinit var dateTimeModule: DateTimeModule
//     lateinit var bookingType: BookingType

    private fun initDayMapper(
        id: Int,
        dayName: String,
        dayNumber: String,
        date: Date,
        enable: Boolean
    ): DayMapper {
        this.id = id
        this.dayName = dayName
        this.dayNumber = dayNumber
        this.date = date
        this.enable = enable
        this.selected = false
        this.currentSelected = false
        return this
    }

    fun buildDayList(
        dayStringList: List<String>,
        dateTimeModule: DateTimeModule,

        ): MutableList<DayMapper> {
        this.dateTimeModule = dateTimeModule
        val list: MutableList<DayMapper> = mutableListOf()
        val apiDateList = getApiDates(dayStringList)
        list.addAll(buildDisabledDateList(apiDateList.first()))
        list.addAll(buildEnabledDayList(apiDateList, list.last().id))
        return list
    }

    private fun getApiDates(
        dayString: List<String>
    ): MutableList<Date> {
        val apiDateList = mutableListOf<Date>()
        dayString.forEach { item ->
            apiDateList.add(
                dateTimeModule.formatDate(
                    item,
                    DateTimeConstants.apiDefaultDateTimeFormat
                )!!
            )
        }
        return apiDateList
    }

    private fun buildDisabledDateList(endDate: Date): MutableList<DayMapper> {
        val list: MutableList<DayMapper> = mutableListOf()
        val endDayCalender = GregorianCalendar()
        endDayCalender.time = getLastDisabledDayInList(endDate)
        val calenderItem = GregorianCalendar()
        calenderItem.set(
            endDayCalender.get(Calendar.YEAR),
            endDayCalender.get(Calendar.MONTH),
            1
        )
        var id = 1
        while ((calenderItem.get(Calendar.MONTH) == endDayCalender.get(Calendar.MONTH)) &&
            calenderItem.get(Calendar.DAY_OF_MONTH) < endDayCalender.get(Calendar.DAY_OF_MONTH)
        ) {
            list.add(addItemToList(id, calenderItem, false))
            id += 1
            calenderItem.add(Calendar.DATE, 1)
        }
        return list
    }

    private fun buildEnabledDayList(
        apiDayList: List<Date>,
        lastItemId: Int
    ): MutableList<DayMapper> {
        val list: MutableList<DayMapper> = mutableListOf()
        var lastId = lastItemId
        apiDayList.forEach { item ->
            lastId += 1
            val calender = GregorianCalendar()
            calender.time = item
            if (list.isEmpty()) {
                list.add(addItemToList(lastId, calender, true))
            } else {
                val different =
                    dateTimeModule.diffInDates(list.last().date, item, DateTimeUnits.DAYS)
                recursiveAddDaysBetween(different, list, calender, lastId)
            }
        }
        return list
    }

    private fun recursiveAddDaysBetween(
        different: Int,
        list: MutableList<DayMapper>,
        calender: GregorianCalendar,
        lastItemId: Int
    ) {
        if (different > 1) {
            val calenderItem = GregorianCalendar()
            calenderItem.time = list.last().date
            calenderItem.add(Calendar.DATE, 1)
            list.add(addItemToList(lastItemId, calenderItem, false))
            recursiveAddDaysBetween(different - 1, list, calender, lastItemId + 1)
        } else {
            list.add(addItemToList(lastItemId, calender, true))
        }
    }

    private fun addItemToList(
        lastItemId: Int,
        calender: GregorianCalendar,
        enable: Boolean
    ): DayMapper = DayMapper().initDayMapper(
        id = lastItemId,
        dayName = dateTimeModule.formatDate(
            calender.time,
            DateTimeConstants.twoCharDayName
        ),
        dayNumber = dateTimeModule.formatDate(
            calender.time,
            DateTimeConstants.dayFormat
        ),
        date = calender.time,
        enable = enable
    )

    private fun getLastDisabledDayInList(endDate: Date): Date {
        val calender = GregorianCalendar()
        calender.time = endDate
//        when(bookingType){
//            BookingType.Hourly ->  calender.time = endDate
//            BookingType.DayPass ->  calender.time = endDate
//            BookingType.Tailored ->  calender.time = endDate
//            BookingType.Bundle ->  calender.time = endDate
//            BookingType.PrivateOffice ->  calender.time = endDate
//            BookingType.MeetingSpace ->  calender.time = endDate
//            BookingType.BizLounge ->  calender.time = endDate
//            BookingType.EventSpace -> {
//                calender.time
//                calender.time = endDate
//            }
//        }
        return calender.time
    }
}