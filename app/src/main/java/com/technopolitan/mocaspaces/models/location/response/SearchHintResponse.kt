package com.technopolitan.mocaspaces.models.location.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchHintResponse(
    @SerializedName("Id")
    @Expose
    val id: Int,
    @SerializedName("Name")
    @Expose
    val name: String,
    @SerializedName("Type")
    @Expose
    val type: Int
)