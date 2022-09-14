package com.technopolitan.mocaspaces.models.shared


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PricesResponse(
    @SerializedName("Bundle")
    @Expose
    val bundle: Double,
    @SerializedName("Day")
    @Expose
    val day: Double,
    @SerializedName("Hourly")
    @Expose
    val hourly: Double,
    @SerializedName("Tailored")
    @Expose
    val tailored: Double
)