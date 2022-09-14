package com.technopolitan.mocaspaces.models.location.mappers

import java.util.*

data class TimeMapper ( var openHour: Date = Calendar.getInstance().time,
                        var closeHour: Date = Calendar.getInstance().time)