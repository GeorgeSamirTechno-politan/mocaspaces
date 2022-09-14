package com.technopolitan.mocaspaces.models.shared


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("Id")
    @Expose
    val id: Int,
    @SerializedName("Name")
    @Expose
    val name: String
)