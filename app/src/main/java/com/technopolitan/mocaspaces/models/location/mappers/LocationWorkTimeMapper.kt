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
    var startWorkString: String = ""
    var endWorkString: String = ""

    fun init(workTimeResponse: LocationWorkingHourResponse): LocationWorkTimeMapper {
        val currentDateTime = Calendar.getInstance().time
        dayFrom = workTimeResponse.dayFrom
        dayTo = workTimeResponse.dayTo
        startWorkString = workTimeResponse.startWorkingHour
        endWorkString = workTimeResponse.endWorkingHour
        if (workTimeResponse.endWorkingHour.isNotEmpty() && workTimeResponse.startWorkingHour.isNotEmpty()) {
            dateTimeModule.formatDate(
                workTimeResponse.startWorkingHour,
                DateTimeConstants.timeFormat
            )?.let { startWorkAt = startWorkAt }
            dateTimeModule.formatDate(workTimeResponse.endWorkingHour, DateTimeConstants.timeFormat)
                ?.let { endWorkAt = it }
        }
        return this
    }
}