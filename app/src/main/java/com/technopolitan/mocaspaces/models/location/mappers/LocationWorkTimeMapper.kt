package com.technopolitan.mocaspaces.models.location.mappers

import com.technopolitan.mocaspaces.models.location.response.LocationWorkingHourResponse
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import java.util.*

class LocationWorkTimeMapper constructor(private var dateTimeModule: DateTimeModule) {

    var dayFrom: String = ""
    var dayTo: String = ""
    var startWorkAt: Date = Calendar.getInstance().time
    var endWorkAt: Date = Calendar.getInstance().time

    fun init(workTimeResponse: LocationWorkingHourResponse): LocationWorkTimeMapper {
        dayFrom = workTimeResponse.dayFrom
        dayTo = workTimeResponse.dayTo
        if (workTimeResponse.endWorkingHour.isNotEmpty() && workTimeResponse.startWorkingHour.isNotEmpty()) {
            dateTimeModule.formatDate(
                workTimeResponse.startWorkingHour,
                DateTimeConstants.timeFormat
            )?.let { startWorkAt = it }
            dateTimeModule.formatDate(workTimeResponse.endWorkingHour, DateTimeConstants.timeFormat)
                ?.let { endWorkAt = it }
        }
        return this
    }
}