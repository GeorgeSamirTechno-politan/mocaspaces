package com.technopolitan.mocaspaces.models.location.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationImagesResponse(
    @SerializedName("Id")
    @Expose
    val id: Int,
    @SerializedName("LocationId")
    @Expose
    val locationId: Int,
    @SerializedName("LocationImageFilePath")
    @Expose
    val locationImageFilePath: String
)