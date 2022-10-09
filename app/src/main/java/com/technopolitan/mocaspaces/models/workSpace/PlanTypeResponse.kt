package com.technopolitan.mocaspaces.models.workSpace


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlanTypeResponse(
    @SerializedName("Id")
    @Expose
    val id: Int,
    @SerializedName("Name")
    @Expose
    val name: String,
    @SerializedName("Plan")
    @Expose
    val planResponse: PlanResponse,
    @SerializedName("URL")
    @Expose
    val uRL: String
)