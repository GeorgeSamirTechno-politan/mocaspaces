package com.technopolitan.mocaspaces.models.location.mappers

import com.technopolitan.mocaspaces.models.location.response.LocationWorkingHourResponse
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import java.util.*

class WorkTimeMapper constructor(private var dateTimeModule: DateTimeModule) {

    var list: List<LocationWorkTimeMapper> = mutableListOf()
    private val dayList: MutableList<String> = mutableListOf()
    private val workTimeMap: MutableMap<String, TimeMapper> = mutableMapOf()

    fun init(list: List<LocationWorkingHourResponse>) {
        configList(list)
        initWorkTimeMapper()
    }

    private fun initWorkTimeMapper() {
        initDayList()
        this.list.forEach { item ->
            dayList.sortBy { item.dayFrom.lowercase() }
            dayList.forEach { dayItem ->
                if (item.dayFrom.lowercase() == dayItem.lowercase() || inBetween(
                        dayItem,
                        item.dayTo
                    )
                ) {
                    workTimeMap[dayItem] = TimeMapper(item.startWorkAt, item.endWorkAt)
                }
            }

        }
    }

    private fun initDayList() {
        dayList.add("saturday")
        dayList.add("sunday")
        dayList.add("monday")
        dayList.add("tuesday")
        dayList.add("wednesday")
        dayList.add("thursday")
        dayList.add("friday")
    }


    private fun configList(list: List<LocationWorkingHourResponse>) {
        val workTimeList = mutableListOf<LocationWorkTimeMapper>()
        list.forEach { item ->
            workTimeList.add(LocationWorkTimeMapper(dateTimeModule).init(item))
        }
        this.list = workTimeList
    }


    private fun inBetween(dayName: String, endWorkDayName: String): Boolean =
        dayList.indexOf(dayName) >= dayList.indexOf(endWorkDayName)

    fun isOpen(): Boolean {
        val currentDateDayName = dateTimeModule.getTodayDateOrTime(DateTimeConstants.fullDayName)!!
        return Calendar.getInstance().time.before(workTimeMap.getValue(currentDateDayName).closeHour)
    }


}