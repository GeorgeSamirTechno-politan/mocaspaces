package com.technopolitan.mocaspaces.models.shared

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PriceResponse(
    @SerializedName("Hourly")
    @Expose
    val hourly: Double? = 0.0,
    @SerializedName("Day")
    @Expose
    val day: Double? = 0.0,
    @SerializedName("Tailored")
    @Expose
    val tailored: Double? = 0.0,
    @SerializedName("Bundle")
    @Expose
    val bundle: Double? = 0.0,
    @SerializedName("PrivateOffice")
    @Expose
    val privateOffice: Double? = 0.0
)