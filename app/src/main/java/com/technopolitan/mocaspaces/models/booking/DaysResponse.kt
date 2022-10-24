package com.technopolitan.mocaspaces.models.booking

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DaysResponse(
    @SerializedName("Days")
    @Expose
    val days: List<String>
)