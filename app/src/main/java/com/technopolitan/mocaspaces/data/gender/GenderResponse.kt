package com.technopolitan.mocaspaces.data.gender


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class GenderResponse(
    @SerializedName("Id")
    @Expose
    val id: Int,
    @SerializedName("Name")
    @Expose
    val name: String
)