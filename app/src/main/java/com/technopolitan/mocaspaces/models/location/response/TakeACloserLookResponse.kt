package com.technopolitan.mocaspaces.models.location.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TakeACloserLookResponse(
    @SerializedName("Name")
    @Expose
    val name: String,
    @SerializedName("Path")
    @Expose
    val path: String
)