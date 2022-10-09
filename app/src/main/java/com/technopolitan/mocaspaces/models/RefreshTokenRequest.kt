package com.technopolitan.mocaspaces.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class RefreshTokenRequest(
    @SerializedName("Token")
    @Expose
    val token: String = ""
)