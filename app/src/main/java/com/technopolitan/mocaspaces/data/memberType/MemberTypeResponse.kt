package com.technopolitan.mocaspaces.data.memberType


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MemberTypeResponse(
    @SerializedName("Icon")
    @Expose
    val icon: String? = "",
    @SerializedName("Id")
    @Expose
    val id: Int? = 0,
    @SerializedName("Name")
    @Expose
    val name: String? = ""
)