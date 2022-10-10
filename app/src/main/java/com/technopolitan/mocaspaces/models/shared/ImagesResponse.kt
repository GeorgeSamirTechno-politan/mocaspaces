package com.technopolitan.mocaspaces.models.shared

import com.google.gson.annotations.SerializedName

data class ImagesResponse(
    @SerializedName("Id")
    val id: Int,

    @SerializedName("LocationId")
    val locationId: Int,

    @SerializedName("LocationImageFilePath")
    val locationImageFilePath: String
)