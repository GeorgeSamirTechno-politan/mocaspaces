package com.technopolitan.mocaspaces.models.shared


import com.google.gson.annotations.SerializedName

data class WorkingHourResponse(
    @SerializedName("DayFrom")
    val dayFrom: String,

    @SerializedName("DayTo")
    val dayTo: String,

    @SerializedName("EndWorkingHour")
    val endWorkingHour: String,

    @SerializedName("Id")
    val id: Int,

    @SerializedName("LocationId")
    val locationId: Int,

    @SerializedName("StartWorkingHour")
    val startWorkingHour: String
)