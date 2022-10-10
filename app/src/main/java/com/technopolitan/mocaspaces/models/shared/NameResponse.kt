package com.technopolitan.mocaspaces.models.shared


import com.google.gson.annotations.SerializedName

data class NameResponse(
    @SerializedName("Id")
    val id: Int,

    @SerializedName("Name")
    val name: String

)