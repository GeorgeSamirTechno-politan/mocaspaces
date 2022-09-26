package com.technopolitan.mocaspaces.models.location.request


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationRequest(
    @SerializedName("id")
    @Expose
    val id: Int? = null,
    @SerializedName("pageNumber")
    @Expose
    val pageNumber: Int,
    @SerializedName("pageSize")
    @Expose
    val pageSize: Int,
    @SerializedName("type")
    @Expose
    val type: Int? = null,
    @SerializedName("fromPax")
    @Expose
    val fromPax: Int? = null,
    @SerializedName("toPax")
    @Expose
    val toPax: Int? = null
)