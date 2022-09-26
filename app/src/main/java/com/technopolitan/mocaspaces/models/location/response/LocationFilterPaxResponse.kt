package com.technopolitan.mocaspaces.models.location.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationFilterPaxResponse(
    @SerializedName("FeatureId")
    @Expose
    val featureId: Int,
    @SerializedName("FromPax")
    @Expose
    val fromPax: Int,
    @SerializedName("Id")
    @Expose
    val id: Int,
    @SerializedName("ToPax")
    @Expose
    val toPax: Int
)