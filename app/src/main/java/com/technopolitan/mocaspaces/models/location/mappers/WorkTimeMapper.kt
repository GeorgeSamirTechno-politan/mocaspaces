package com.technopolitan.mocaspaces.models.location.mappers
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import java.util.*
import javax.inject.Inject

class WorkTimeMapper @Inject constructor(private var dateTimeModule: DateTimeModule){

    var list: List<LocationWorkTimeMapper> = mutableListOf()
    private val dayList : MutableList<String> = mutableListOf()
    private val workTimeMap : MutableMap<String, TimeMapper> = mutableMapOf()

    fun init(list: List<LocationWorkTimeMapper>){
        this.list = list
        initDayList()
        list.forEach{ item ->
            dayList.sortBy { item.dayFrom.lowercase() }
            dayList.forEach{ dayItem->
                if(item.dayFrom.lowercase() == dayItem.lowercase() || inBetween(dayItem, item.dayTo)){
                    workTimeMap[dayItem] = TimeMapper(item.startWorkAt, item.endWorkAt)
                }
            }

        }
    }

    fun isOpen(): Boolean{
        val currentDateDayName = dateTimeModule.getTodayDateOrTime(DateTimeConstants.fullDayName)!!
        return Calendar.getInstance().time.before(workTimeMap.getValue(currentDateDayName).closeHour)
    }

    private fun inBetween(dayName: String, endWorkDayName: String): Boolean =
        dayList.indexOf(dayName) >= dayList.indexOf(endWorkDayName)

    private fun initDayList(){
        dayList.add("saturday")
        dayList.add("sunday")
        dayList.add("monday")
        dayList.add("tuesday")
        dayList.add("wednesday")
        dayList.add("thursday")
        dayList.add("friday")
    }
}