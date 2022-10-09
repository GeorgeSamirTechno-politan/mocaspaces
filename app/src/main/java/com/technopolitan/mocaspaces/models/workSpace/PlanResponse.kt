package com.technopolitan.mocaspaces.models.workSpace


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlanResponse(
    @SerializedName("Description")
    @Expose
    val description: String,
    @SerializedName("Id")
    @Expose
    val id: Int,
    @SerializedName("Points")
    @Expose
    val points: String,
    @SerializedName("TermsOfUse")
    @Expose
    val termsOfUse: String,
    @SerializedName("WhatYouGet")
    @Expose
    val whatYouGet: String
)