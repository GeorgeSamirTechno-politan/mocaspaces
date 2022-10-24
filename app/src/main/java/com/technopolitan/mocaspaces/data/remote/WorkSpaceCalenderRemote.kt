package com.technopolitan.mocaspaces.data.remote

import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.models.booking.DayMapper
import com.technopolitan.mocaspaces.models.booking.DaysResponse
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import io.reactivex.Flowable
import javax.inject.Inject

class WorkSpaceCalenderRemote @Inject constructor(
    private var networkModule: NetworkModule,
    private var dateTimeModule: DateTimeModule
) : BaseRemote<List<DayMapper>, DaysResponse>() {

    private var locationId: Int = 0
    private lateinit var startDate: String
    private lateinit var endDate: String

    fun getWorkSpaceCalender(
        locationId: Int,
        startDate: String,
        endDate: String
    ): SingleLiveEvent<ApiStatus<List<DayMapper>>> {
        this.endDate = endDate
        this.startDate = startDate
        this.locationId = locationId
        return handleApi()
    }

    override fun flowable(): Flowable<HeaderResponse<DaysResponse>> {
        return networkModule.provideService(BaseUrl.workSpaceReservation).getWorkSpaceWorkingDays(
            startDate = startDate,
            endDay = endDate,
            locationId = locationId
        )
    }

    override fun handleResponse(it: HeaderResponse<DaysResponse>): ApiStatus<List<DayMapper>> {
        return if (it.succeeded) {
            val list = mutableListOf<DayMapper>()
            it.data?.let {
                list.addAll(DayMapper().buildDayList(it.days, dateTimeModule))
            }
            SuccessStatus(it.message, list)
        } else return FailedStatus(it.message)
    }
}