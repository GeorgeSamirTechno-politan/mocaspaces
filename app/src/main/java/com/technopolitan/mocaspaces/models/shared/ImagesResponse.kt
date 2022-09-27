package com.technopolitan.mocaspaces.models.shared


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImagesResponse(
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