package com.technopolitan.mocaspaces.data.bookingShared

import android.content.Context
import com.technopolitan.mocaspaces.databinding.MonthDayLayoutBinding
import com.technopolitan.mocaspaces.models.booking.DayMapper
import com.technopolitan.mocaspaces.models.booking.MonthMapper
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import java.util.*
import javax.inject.Inject

class MonthDayDataModule @Inject constructor(
    private var context: Context,
    spannableStringModule: SpannableStringModule,
    private var dateTimeModule: DateTimeModule
) {
    private lateinit var binding: MonthDayLayoutBinding
    private lateinit var monthCallBack: (monthMapper: MonthMapper) -> Unit
    private val monthList: MutableList<MonthMapper> = mutableListOf()
    private val monthAdapter: MonthAdapter = MonthAdapter({
        clickOnMonthItem(it)
    }, context, spannableStringModule)
    private lateinit var dayAdapter: DayAdapter

    private fun initMonthList() {
        val currentDate = Calendar.getInstance()
        monthList.add(
            getMonthMapper(currentDate)
        )
        currentDate.add(Calendar.MONTH, 1)
        monthList.add(
            getMonthMapper(currentDate)
        )
        currentDate.add(Calendar.MONTH, 1)
        monthList.add(
            getMonthMapper(currentDate)
        )
    }

    private fun getMonthMapper(currentDate: Calendar) = MonthMapper(
        currentDate.get(Calendar.MONTH),
        dateTimeModule.formatDate(currentDate.time, DateTimeConstants.monthNameFormat),
        currentDate.time, true
    )


    private fun clickOnMonthItem(monthMapper: MonthMapper) {
        monthCallBack(monthMapper)
    }

    fun init(binding: MonthDayLayoutBinding, monthCallBack: (monthMapper: MonthMapper) -> Unit) {
        this.binding = binding
        this.monthCallBack = monthCallBack
        initMonthList()
        monthAdapter.setData(monthList.toMutableList())
        binding.monthRecycler.adapter = monthAdapter
        monthCallBack(monthList.first())
    }

    fun setDayList(
        list: List<DayMapper>,
        dayCallBack: (dayMapper: DayMapper, position: Int) -> Unit
    ) {
        dayAdapter = DayAdapter(dayCallBack, context)
        dayAdapter.setHasStableIds(false)
        dayAdapter.setData(list.toMutableList())
        binding.dayRecycler.adapter = dayAdapter
    }


}