package com.technopolitan.mocaspaces.ui.bookingSharedFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.bookingShared.MonthDayDataModule
import com.technopolitan.mocaspaces.data.bookingShared.SingleMultiplyDataModule
import com.technopolitan.mocaspaces.data.repo.CalenderRepo
import com.technopolitan.mocaspaces.databinding.MonthDayLayoutBinding
import com.technopolitan.mocaspaces.databinding.SingleMultiPickingLayoutBinding
import com.technopolitan.mocaspaces.enums.BookingType
import com.technopolitan.mocaspaces.models.booking.DayMapper
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import java.util.*
import javax.inject.Inject

class CalenderViewModel @Inject constructor(
    private val calenderRepo: CalenderRepo,
    private var monthDayDataModule: MonthDayDataModule,
    private var singleMultiplyDataModule: SingleMultiplyDataModule,
    private var dateTimeModule: DateTimeModule,

    ) : BaseViewModel<List<DayMapper>>() {

    private var singleBookingMutable: MutableLiveData<Boolean> = MutableLiveData()
    private var locationId: Int = 0
    private var selectedDayMapper: DayMapper? = null
    private var selectedDayMapperPosition: Int = -1


    fun initMonthDayDataModule(binding: MonthDayLayoutBinding) {
        monthDayDataModule.init(binding) {
            val startCalender = GregorianCalendar()
            val endCalender = GregorianCalendar()
            startCalender.time = it.date
            endCalender.time = it.date
            startCalender.set(
                startCalender.get(Calendar.YEAR),
                startCalender.get(Calendar.MONTH),
                1
            )
            endCalender.set(
                endCalender.get(Calendar.YEAR),
                endCalender.get(Calendar.MONTH),
                dateTimeModule.getLastDayOfMonth(endCalender.time)
            )
            val startDate = dateTimeModule.formatDate(
                startCalender.time,
                DateTimeConstants.apiDefaultDateTimeFormat
            )
            val endDate = dateTimeModule.formatDate(
                endCalender.time,
                DateTimeConstants.apiDefaultDateTimeFormat
            )
            apiMediatorLiveData =
                calenderRepo.handleDayApi(BookingType.Hourly, locationId, startDate, endDate)
        }
    }

    fun initSingleMultiDateModule(binding: SingleMultiPickingLayoutBinding) {
        singleMultiplyDataModule.init(binding) {
            singleBookingMutable.postValue(it)
        }
    }

    fun getSingleLiveData(): LiveData<Boolean> = singleBookingMutable

    fun listenForCalenderApi(): LiveData<ApiStatus<List<DayMapper>>> = apiMediatorLiveData

    fun setLocationId(locationId: Int) {
        this.locationId = locationId
    }

    fun reset() {
        singleBookingMutable = MutableLiveData()
        locationId = 0
        selectedDayMapper = null
        selectedDayMapperPosition = -1
    }

    fun setDayList(list: List<DayMapper>) {
        monthDayDataModule.setDayList(list) { item, position ->
            selectedDayMapper = item
            selectedDayMapperPosition = position
        }
    }
}