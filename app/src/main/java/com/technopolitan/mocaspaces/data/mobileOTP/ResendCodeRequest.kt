package com.technopolitan.mocaspaces.data.mobileOTP


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResendCodeRequest(
    @SerializedName("mobile")
    @Expose
    val mobile: String? = ""
)