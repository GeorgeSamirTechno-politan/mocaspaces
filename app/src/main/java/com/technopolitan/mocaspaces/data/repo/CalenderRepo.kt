package com.technopolitan.mocaspaces.data.repo

import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.remote.WorkSpaceCalenderRemote
import com.technopolitan.mocaspaces.enums.BookingType
import com.technopolitan.mocaspaces.models.booking.DayMapper
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import javax.inject.Inject

class CalenderRepo @Inject constructor(private var workSpaceCalenderRemote: WorkSpaceCalenderRemote) {

    private lateinit var bookingType: BookingType

    fun handleDayApi(
        bookingType: BookingType, locationId: Int,

        startDate: String,
        endDate: String
    ): SingleLiveEvent<ApiStatus<List<DayMapper>>> {
        return when (bookingType) {
            BookingType.Hourly -> workSpaceCalenderRemote.getWorkSpaceCalender(
                locationId,
                startDate,
                endDate
            )
            BookingType.DayPass -> workSpaceCalenderRemote.getWorkSpaceCalender(
                locationId,
                startDate,
                endDate
            )
            BookingType.Tailored -> workSpaceCalenderRemote.getWorkSpaceCalender(
                locationId,
                startDate,
                endDate
            )
            BookingType.Bundle -> workSpaceCalenderRemote.getWorkSpaceCalender(
                locationId,
                startDate,
                endDate
            )
            BookingType.PrivateOffice -> workSpaceCalenderRemote.getWorkSpaceCalender(
                locationId,
                startDate,
                endDate
            )
            BookingType.MeetingSpace -> workSpaceCalenderRemote.getWorkSpaceCalender(
                locationId,
                startDate,
                endDate
            )
            BookingType.BizLounge -> workSpaceCalenderRemote.getWorkSpaceCalender(
                locationId,
                startDate,
                endDate
            )
            BookingType.EventSpace -> workSpaceCalenderRemote.getWorkSpaceCalender(
                locationId,
                startDate,
                endDate
            )
        }
    }
}