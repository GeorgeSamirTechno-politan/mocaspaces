package com.technopolitan.mocaspaces.models.location.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationWorkingHourResponse(
    @SerializedName("DayFrom")
    @Expose
    val dayFrom: String,
    @SerializedName("DayTo")
    @Expose
    val dayTo: String,
    @SerializedName("EndWorkingHour")
    @Expose
    val endWorkingHour: String,
    @SerializedName("Id")
    @Expose
    val id: Int,
    @SerializedName("LocationId")
    @Expose
    val locationId: Int,
    @SerializedName("StartWorkingHour")
    @Expose
    val startWorkingHour: String
)