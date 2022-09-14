package com.technopolitan.mocaspaces.models.location.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationAmenityResponse(
    @SerializedName("Icon")
    @Expose
    val icon: String
)