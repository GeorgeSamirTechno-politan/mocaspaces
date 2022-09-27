package com.technopolitan.mocaspaces.models.location.mappers

import android.content.Context
import android.text.Spannable
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.models.shared.WorkingHourResponse
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import java.util.*

class WorkTimeMapper constructor(
    private var dateTimeModule: DateTimeModule,
    private var spannableStringModule: SpannableStringModule,
    private var context: Context
) {

    var locationWorkTimeMapperList: List<LocationWorkTimeMapper> = mutableListOf()
    private val dayList: MutableList<String> = mutableListOf()
    private val workTimeMap: MutableMap<String, TimeMapper> = mutableMapOf()

    fun init(list: List<WorkingHourResponse>) {
        configList(list)
        initWorkTimeMapper()
    }

    private fun initWorkTimeMapper() {
        initDayList()
        this.locationWorkTimeMapperList.forEach { item ->
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


    private fun configList(list: List<WorkingHourResponse>) {
        val workTimeList = mutableListOf<LocationWorkTimeMapper>()
        list.forEach { item ->
            workTimeList.add(LocationWorkTimeMapper(dateTimeModule).init(item))
        }
        this.locationWorkTimeMapperList = workTimeList
    }


    private fun inBetween(dayName: String, endWorkDayName: String): Boolean =
        dayList.indexOf(dayName) >= dayList.indexOf(endWorkDayName)

    fun isOpen(): Boolean {
        val keyDayName = dateTimeModule.getTodayDateOrTime(DateTimeConstants.fullDayName)
        if (keyDayName != null) {
            val currentDateTime = Calendar.getInstance().time
            val closeHour =
                dateTimeModule.addCurrentDayMonthYearToDate(workTimeMap.getValue(keyDayName.lowercase()).closeHour)
            return currentDateTime.before(closeHour)
        }
        return false
    }

    fun getOpenHourText(): Spannable {
        val stringSpan = spannableStringModule.newString()
        locationWorkTimeMapperList.forEach {
            if (it.dayFrom == it.dayTo) {
                spannableStringModule.addString(
                    context.getString(R.string.all_text) +
                            " ${it.dayFrom} ${it.startWorkString} " +
                            "- ${it.endWorkString}\n"
                )
                    .init(R.color.black, com.intuit.sdp.R.dimen._10sdp, R.font.gt_meduim)
            } else {
                spannableStringModule.addString(
                    "${it.dayFrom} ${context.getString(R.string.to)} " +
                            "${it.dayTo} ${it.startWorkString} - ${it.endWorkString}"
                )
                    .init(R.color.black, com.intuit.sdp.R.dimen._10sdp, R.font.gt_meduim)
            }
        }
        return stringSpan.getSpannableString()
    }


}